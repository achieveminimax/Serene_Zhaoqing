package com.zqtravel.shop.service.impl;

import com.zqtravel.shop.dto.request.CartAddRequest;
import com.zqtravel.shop.dto.request.CartUpdateRequest;
import com.zqtravel.shop.dto.response.CartDTO;
import com.zqtravel.shop.entity.Cart;
import com.zqtravel.shop.entity.Product;
import com.zqtravel.shop.mapper.CartMapper;
import com.zqtravel.shop.mapper.ProductMapper;
import com.zqtravel.shop.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartMapper cartMapper;
    private final ProductMapper productMapper;

    @Override
    @Cacheable(value = "cart", key = "#userId")
    public CartDTO.CartSummary getUserCart(Long userId) {
        List<Cart> cartItems = cartMapper.selectByUserId(userId);
        
        List<CartDTO> cartDTOs = cartItems.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        int totalItems = cartItems.size();
        int selectedItems = (int) cartItems.stream().filter(item -> item.getSelected() == 1).count();
        
        BigDecimal totalAmount = cartDTOs.stream()
                .map(CartDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal selectedAmount = cartDTOs.stream()
                .filter(CartDTO::getSelected)
                .map(CartDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return CartDTO.CartSummary.builder()
                .totalItems(totalItems)
                .selectedItems(selectedItems)
                .totalAmount(totalAmount)
                .selectedAmount(selectedAmount)
                .items(cartDTOs)
                .build();
    }

    @Override
    @Transactional
    @CacheEvict(value = "cart", key = "#userId")
    public void addToCart(Long userId, CartAddRequest request) {
        // 检查商品是否存在且库存充足
        Product product = productMapper.selectById(request.getProductId());
        if (product == null || product.getStatus() != 1 || product.getIsDeleted() == 1) {
            throw new RuntimeException("商品不存在或已下架");
        }
        
        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("商品库存不足");
        }
        
        // 检查购物车是否已存在该商品
        Cart existingCart = cartMapper.selectByUserIdAndProductId(userId, request.getProductId());
        if (existingCart != null) {
            // 更新数量
            existingCart.setQuantity(existingCart.getQuantity() + request.getQuantity());
            cartMapper.updateById(existingCart);
        } else {
            // 新增购物车项
            Cart cart = Cart.builder()
                    .userId(userId)
                    .productId(request.getProductId())
                    .quantity(request.getQuantity())
                    .selected(request.getSelected() ? 1 : 0)
                    .build();
            cartMapper.insert(cart);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "cart", key = "#userId")
    public void updateCartItem(Long userId, Long itemId, CartUpdateRequest request) {
        Cart cart = cartMapper.selectById(itemId);
        if (cart == null || !cart.getUserId().equals(userId)) {
            throw new RuntimeException("购物车项不存在");
        }
        
        if (request.getQuantity() != null) {
            // 检查库存
            Product product = productMapper.selectById(cart.getProductId());
            if (product.getStock() < request.getQuantity()) {
                throw new RuntimeException("商品库存不足");
            }
            cart.setQuantity(request.getQuantity());
        }
        
        if (request.getSelected() != null) {
            cart.setSelected(request.getSelected() ? 1 : 0);
        }
        
        cartMapper.updateById(cart);
    }

    @Override
    @Transactional
    @CacheEvict(value = "cart", key = "#userId")
    public void removeCartItem(Long userId, Long itemId) {
        Cart cart = cartMapper.selectById(itemId);
        if (cart != null && cart.getUserId().equals(userId)) {
            cartMapper.deleteById(itemId);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "cart", key = "#userId")
    public void clearUserCart(Long userId) {
        cartMapper.clearByUserId(userId);
    }

    @Override
    @Transactional
    @CacheEvict(value = "cart", key = "#userId")
    public void mergeCart(Long userId, List<CartAddRequest> localCartItems) {
        // 实现购物车合并逻辑
        for (CartAddRequest item : localCartItems) {
            try {
                addToCart(userId, item);
            } catch (Exception e) {
                log.warn("合并购物车项失败: {}", e.getMessage());
            }
        }
    }

    private CartDTO convertToDTO(Cart cart) {
        Product product = productMapper.selectById(cart.getProductId());
        if (product == null) {
            return null;
        }
        
        BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity()));
        
        return CartDTO.builder()
                .id(cart.getId())
                .userId(cart.getUserId())
                .productId(cart.getProductId())
                .productName(product.getName())
                .productImage(extractFirstImage(product.getImages()))
                .productPrice(product.getPrice())
                .quantity(cart.getQuantity())
                .selected(cart.getSelected() == 1)
                .subtotal(subtotal)
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .build();
    }
    
    private String extractFirstImage(String imagesJson) {
        if (imagesJson == null || imagesJson.isEmpty()) {
            return "";
        }
        // 简单提取第一张图片
        String cleaned = imagesJson.replaceAll("[\\[\\]\"]", "");
        String[] images = cleaned.split(",");
        return images.length > 0 ? images[0].trim() : "";
    }
}