package com.zqtravel.shop.service;

import com.zqtravel.shop.builder.CartTestDataBuilder;
import com.zqtravel.shop.builder.ProductTestDataBuilder;
import com.zqtravel.shop.dto.request.CartAddRequest;
import com.zqtravel.shop.dto.request.CartUpdateRequest;
import com.zqtravel.shop.dto.response.CartDTO;
import com.zqtravel.shop.entity.Cart;
import com.zqtravel.shop.entity.Product;
import com.zqtravel.shop.mapper.CartMapper;
import com.zqtravel.shop.mapper.ProductMapper;
import com.zqtravel.shop.service.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * CartServiceImpl 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("购物车服务测试")
class CartServiceImplTest {

    @Mock
    private CartMapper cartMapper;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private CartServiceImpl cartService;

    private Product defaultProduct;
    private Cart defaultCart;

    @BeforeEach
    void setUp() {
        defaultProduct = ProductTestDataBuilder.defaultProduct().build();
        defaultCart = CartTestDataBuilder.defaultCart().build();
    }

    @Test
    @DisplayName("获取用户购物车 - 成功")
    void getUserCart_Success() {
        // Given
        Long userId = 1L;
        List<Cart> carts = Arrays.asList(
                CartTestDataBuilder.selectedCart(),
                CartTestDataBuilder.unselectedCart()
        );
        when(cartMapper.selectByUserId(userId)).thenReturn(carts);
        when(productMapper.selectById(1L)).thenReturn(defaultProduct);

        // When
        CartDTO.CartSummary result = cartService.getUserCart(userId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTotalItems()).isEqualTo(2);
        assertThat(result.getSelectedItems()).isEqualTo(1);
        assertThat(result.getTotalAmount()).isGreaterThan(BigDecimal.ZERO);
        verify(cartMapper).selectByUserId(userId);
    }

    @Test
    @DisplayName("获取用户购物车 - 空购物车")
    void getUserCart_Empty() {
        // Given
        Long userId = 1L;
        when(cartMapper.selectByUserId(userId)).thenReturn(Collections.emptyList());

        // When
        CartDTO.CartSummary result = cartService.getUserCart(userId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTotalItems()).isEqualTo(0);
        assertThat(result.getTotalAmount()).isEqualTo(BigDecimal.ZERO);
        assertThat(result.getItems()).isEmpty();
    }

    @Test
    @DisplayName("添加商品到购物车 - 新商品")
    void addToCart_NewProduct() {
        // Given
        Long userId = 1L;
        CartAddRequest request = CartAddRequest.builder()
                .productId(1L)
                .quantity(2)
                .selected(true)
                .build();

        when(productMapper.selectById(1L)).thenReturn(defaultProduct);
        when(cartMapper.selectByUserIdAndProductId(userId, 1L)).thenReturn(null);

        // When
        cartService.addToCart(userId, request);

        // Then
        verify(cartMapper).insert(any(Cart.class));
    }

    @Test
    @DisplayName("添加商品到购物车 - 已存在商品增加数量")
    void addToCart_ExistingProduct() {
        // Given
        Long userId = 1L;
        CartAddRequest request = CartAddRequest.builder()
                .productId(1L)
                .quantity(2)
                .selected(true)
                .build();

        Cart existingCart = CartTestDataBuilder.cartWithQuantity(1);

        when(productMapper.selectById(1L)).thenReturn(defaultProduct);
        when(cartMapper.selectByUserIdAndProductId(userId, 1L)).thenReturn(existingCart);

        // When
        cartService.addToCart(userId, request);

        // Then
        assertThat(existingCart.getQuantity()).isEqualTo(3);
        verify(cartMapper).updateById(existingCart);
        verify(cartMapper, never()).insert(any());
    }

    @Test
    @DisplayName("添加商品到购物车 - 商品不存在")
    void addToCart_ProductNotFound() {
        // Given
        Long userId = 1L;
        CartAddRequest request = CartAddRequest.builder()
                .productId(999L)
                .quantity(1)
                .build();

        when(productMapper.selectById(999L)).thenReturn(null);

        // When & Then
        assertThatThrownBy(() -> cartService.addToCart(userId, request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("商品不存在");
    }

    @Test
    @DisplayName("添加商品到购物车 - 商品已下架")
    void addToCart_ProductOffline() {
        // Given
        Long userId = 1L;
        CartAddRequest request = CartAddRequest.builder()
                .productId(1L)
                .quantity(1)
                .build();

        Product offlineProduct = ProductTestDataBuilder.offlineProduct();
        when(productMapper.selectById(1L)).thenReturn(offlineProduct);

        // When & Then
        assertThatThrownBy(() -> cartService.addToCart(userId, request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("商品不存在或已下架");
    }

    @Test
    @DisplayName("添加商品到购物车 - 库存不足")
    void addToCart_InsufficientStock() {
        // Given
        Long userId = 1L;
        CartAddRequest request = CartAddRequest.builder()
                .productId(1L)
                .quantity(100)
                .build();

        Product lowStockProduct = ProductTestDataBuilder.lowStockProduct();
        when(productMapper.selectById(1L)).thenReturn(lowStockProduct);

        // When & Then
        assertThatThrownBy(() -> cartService.addToCart(userId, request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("库存不足");
    }

    @Test
    @DisplayName("更新购物车项 - 成功")
    void updateCartItem_Success() {
        // Given
        Long userId = 1L;
        Long itemId = 1L;
        CartUpdateRequest request = CartUpdateRequest.builder()
                .quantity(5)
                .selected(true)
                .build();

        Cart cart = CartTestDataBuilder.cartWithUserId(userId);
        when(cartMapper.selectById(itemId)).thenReturn(cart);
        when(productMapper.selectById(1L)).thenReturn(defaultProduct);

        // When
        cartService.updateCartItem(userId, itemId, request);

        // Then
        assertThat(cart.getQuantity()).isEqualTo(5);
        assertThat(cart.getSelected()).isEqualTo(1);
        verify(cartMapper).updateById(cart);
    }

    @Test
    @DisplayName("更新购物车项 - 购物车项不存在")
    void updateCartItem_NotFound() {
        // Given
        Long userId = 1L;
        Long itemId = 999L;
        CartUpdateRequest request = CartUpdateRequest.builder()
                .quantity(5)
                .build();

        when(cartMapper.selectById(itemId)).thenReturn(null);

        // When & Then
        assertThatThrownBy(() -> cartService.updateCartItem(userId, itemId, request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("购物车项不存在");
    }

    @Test
    @DisplayName("更新购物车项 - 不属于当前用户")
    void updateCartItem_NotOwner() {
        // Given
        Long userId = 1L;
        Long itemId = 1L;
        CartUpdateRequest request = CartUpdateRequest.builder()
                .quantity(5)
                .build();

        Cart otherUserCart = CartTestDataBuilder.cartWithUserId(2L);
        when(cartMapper.selectById(itemId)).thenReturn(otherUserCart);

        // When & Then
        assertThatThrownBy(() -> cartService.updateCartItem(userId, itemId, request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("购物车项不存在");
    }

    @Test
    @DisplayName("更新购物车项 - 库存不足")
    void updateCartItem_InsufficientStock() {
        // Given
        Long userId = 1L;
        Long itemId = 1L;
        CartUpdateRequest request = CartUpdateRequest.builder()
                .quantity(100)
                .build();

        Cart cart = CartTestDataBuilder.cartWithUserId(userId);
        Product lowStockProduct = ProductTestDataBuilder.lowStockProduct();

        when(cartMapper.selectById(itemId)).thenReturn(cart);
        when(productMapper.selectById(1L)).thenReturn(lowStockProduct);

        // When & Then
        assertThatThrownBy(() -> cartService.updateCartItem(userId, itemId, request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("库存不足");
    }

    @Test
    @DisplayName("删除购物车项 - 成功")
    void removeCartItem_Success() {
        // Given
        Long userId = 1L;
        Long itemId = 1L;
        Cart cart = CartTestDataBuilder.cartWithUserId(userId);
        when(cartMapper.selectById(itemId)).thenReturn(cart);

        // When
        cartService.removeCartItem(userId, itemId);

        // Then
        verify(cartMapper).deleteById(itemId);
    }

    @Test
    @DisplayName("删除购物车项 - 不存在不抛出异常")
    void removeCartItem_NotFound() {
        // Given
        Long userId = 1L;
        Long itemId = 999L;
        when(cartMapper.selectById(itemId)).thenReturn(null);

        // When
        assertThatNoException().isThrownBy(() -> cartService.removeCartItem(userId, itemId));

        // Then
        verify(cartMapper, never()).deleteById(any(Long.class));
    }

    @Test
    @DisplayName("清空用户购物车 - 成功")
    void clearUserCart_Success() {
        // Given
        Long userId = 1L;

        // When
        cartService.clearUserCart(userId);

        // Then
        verify(cartMapper).clearByUserId(userId);
    }

    @Test
    @DisplayName("合并购物车 - 成功")
    void mergeCart_Success() {
        // Given
        Long userId = 1L;
        List<CartAddRequest> localCartItems = Arrays.asList(
                CartAddRequest.builder().productId(1L).quantity(2).build(),
                CartAddRequest.builder().productId(2L).quantity(1).build()
        );

        when(productMapper.selectById(any())).thenReturn(defaultProduct);
        when(cartMapper.selectByUserIdAndProductId(any(), any())).thenReturn(null);

        // When
        cartService.mergeCart(userId, localCartItems);

        // Then
        verify(cartMapper, times(2)).insert(any(Cart.class));
    }

    @Test
    @DisplayName("合并购物车 - 部分失败")
    void mergeCart_PartialFailure() {
        // Given
        Long userId = 1L;
        List<CartAddRequest> localCartItems = Arrays.asList(
                CartAddRequest.builder().productId(1L).quantity(2).build(),
                CartAddRequest.builder().productId(999L).quantity(1).build() // 不存在的商品
        );

        when(productMapper.selectById(1L)).thenReturn(defaultProduct);
        when(productMapper.selectById(999L)).thenReturn(null);
        when(cartMapper.selectByUserIdAndProductId(any(), any())).thenReturn(null);

        // When
        assertThatNoException().isThrownBy(() -> cartService.mergeCart(userId, localCartItems));

        // Then - 只有有效商品被添加
        verify(cartMapper, times(1)).insert(any(Cart.class));
    }
}
