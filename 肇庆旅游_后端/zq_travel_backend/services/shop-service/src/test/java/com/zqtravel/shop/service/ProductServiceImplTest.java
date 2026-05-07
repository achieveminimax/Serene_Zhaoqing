package com.zqtravel.shop.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zqtravel.shop.builder.ProductTestDataBuilder;
import com.zqtravel.shop.dto.PageResult;
import com.zqtravel.shop.dto.request.ProductQueryRequest;
import com.zqtravel.shop.dto.response.ProductDTO;
import com.zqtravel.shop.entity.Product;
import com.zqtravel.shop.mapper.ProductMapper;
import com.zqtravel.shop.service.impl.ProductServiceImpl;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * ProductServiceImpl 单元测试
 * 使用Mockito模拟依赖，不启动Spring上下文
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("商品服务测试")
class ProductServiceImplTest {

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product defaultProduct;

    @BeforeEach
    void setUp() {
        defaultProduct = ProductTestDataBuilder.defaultProduct().build();
    }

    @Test
    @DisplayName("分页查询商品列表 - 成功")
    void listProducts_Success() {
        // Given
        ProductQueryRequest request = ProductQueryRequest.builder()
                .page(1)
                .size(20)
                .categoryId(1L)
                .build();

        Page<Product> page = new Page<>(1, 20);
        List<Product> products = Arrays.asList(defaultProduct);
        page.setRecords(products);
        page.setTotal(1);

        when(productMapper.selectPage(any(Page.class), any(QueryWrapper.class)))
                .thenReturn(page);

        // When
        PageResult<ProductDTO> result = productService.listProducts(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getPage()).isEqualTo(1);
        assertThat(result.getSize()).isEqualTo(20);
        assertThat(result.getTotal()).isEqualTo(1);
        assertThat(result.getItems()).hasSize(1);
        assertThat(result.getItems().get(0).getName()).isEqualTo("肇庆裹蒸粽");

        verify(productMapper).selectPage(any(Page.class), any(QueryWrapper.class));
    }

    @Test
    @DisplayName("分页查询商品列表 - 带关键词搜索")
    void listProducts_WithKeyword() {
        // Given
        ProductQueryRequest request = ProductQueryRequest.builder()
                .page(1)
                .size(20)
                .keyword("裹蒸粽")
                .build();

        Page<Product> page = new Page<>(1, 20);
        page.setRecords(Arrays.asList(defaultProduct));
        page.setTotal(1);

        when(productMapper.selectPage(any(Page.class), any(QueryWrapper.class)))
                .thenReturn(page);

        // When
        PageResult<ProductDTO> result = productService.listProducts(request);

        // Then
        assertThat(result.getItems()).hasSize(1);
        verify(productMapper).selectPage(any(Page.class), any(QueryWrapper.class));
    }

    @Test
    @DisplayName("分页查询商品列表 - 按价格区间筛选")
    void listProducts_WithPriceRange() {
        // Given
        ProductQueryRequest request = ProductQueryRequest.builder()
                .page(1)
                .size(20)
                .minPrice(new BigDecimal("10"))
                .maxPrice(new BigDecimal("100"))
                .build();

        Page<Product> page = new Page<>(1, 20);
        page.setRecords(Arrays.asList(defaultProduct));
        page.setTotal(1);

        when(productMapper.selectPage(any(Page.class), any(QueryWrapper.class)))
                .thenReturn(page);

        // When
        PageResult<ProductDTO> result = productService.listProducts(request);

        // Then
        assertThat(result.getItems()).hasSize(1);
        verify(productMapper).selectPage(any(Page.class), any(QueryWrapper.class));
    }

    @Test
    @DisplayName("分页查询商品列表 - 空结果")
    void listProducts_EmptyResult() {
        // Given
        ProductQueryRequest request = ProductQueryRequest.builder()
                .page(1)
                .size(20)
                .build();

        Page<Product> page = new Page<>(1, 20);
        page.setRecords(Collections.emptyList());
        page.setTotal(0);

        when(productMapper.selectPage(any(Page.class), any(QueryWrapper.class)))
                .thenReturn(page);

        // When
        PageResult<ProductDTO> result = productService.listProducts(request);

        // Then
        assertThat(result.getItems()).isEmpty();
        assertThat(result.getTotal()).isEqualTo(0);
    }

    @Test
    @DisplayName("根据ID获取商品详情 - 成功")
    void getProductById_Success() {
        // Given
        Long productId = 1L;
        when(productMapper.selectById(productId)).thenReturn(defaultProduct);

        // When
        ProductDTO result = productService.getProductById(productId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(productId);
        assertThat(result.getName()).isEqualTo("肇庆裹蒸粽");
        assertThat(result.getPrice()).isEqualByComparingTo(new BigDecimal("25.00"));
        assertThat(result.getStatusDesc()).isEqualTo("上架");

        verify(productMapper).selectById(productId);
    }

    @Test
    @DisplayName("根据ID获取商品详情 - 商品不存在")
    void getProductById_NotFound() {
        // Given
        Long productId = 999L;
        when(productMapper.selectById(productId)).thenReturn(null);

        // When
        ProductDTO result = productService.getProductById(productId);

        // Then
        assertThat(result).isNull();
        verify(productMapper).selectById(productId);
    }

    @Test
    @DisplayName("根据ID获取商品详情 - 商品已删除")
    void getProductById_Deleted() {
        // Given
        Long productId = 1L;
        Product deletedProduct = ProductTestDataBuilder.deletedProduct();
        when(productMapper.selectById(productId)).thenReturn(deletedProduct);

        // When
        ProductDTO result = productService.getProductById(productId);

        // Then
        assertThat(result).isNull();
        verify(productMapper).selectById(productId);
    }

    @Test
    @DisplayName("搜索商品 - 成功")
    void searchProducts_Success() {
        // Given
        String keyword = "肇庆";
        Long categoryId = 1L;

        when(productMapper.selectList(any(QueryWrapper.class)))
                .thenReturn(Arrays.asList(defaultProduct));

        // When
        List<ProductDTO> result = productService.searchProducts(keyword, categoryId);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).contains("裹蒸粽");

        verify(productMapper).selectList(any(QueryWrapper.class));
    }

    @Test
    @DisplayName("搜索商品 - 空关键词")
    void searchProducts_EmptyKeyword() {
        // Given
        String keyword = "";
        Long categoryId = null;

        when(productMapper.selectList(any(QueryWrapper.class)))
                .thenReturn(Arrays.asList(defaultProduct));

        // When
        List<ProductDTO> result = productService.searchProducts(keyword, categoryId);

        // Then
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("获取热门商品 - 成功")
    void getHotProducts_Success() {
        // Given
        Integer limit = 10;

        when(productMapper.selectList(any(QueryWrapper.class)))
                .thenReturn(Arrays.asList(defaultProduct));

        // When
        List<ProductDTO> result = productService.getHotProducts(limit);

        // Then
        assertThat(result).hasSize(1);
        verify(productMapper).selectList(any(QueryWrapper.class));
    }

    @Test
    @DisplayName("更新商品库存 - 成功")
    void updateProductStock_Success() {
        // Given
        Long productId = 1L;
        Integer quantity = 5;

        when(productMapper.selectById(productId)).thenReturn(defaultProduct);
        when(productMapper.updateStockWithOptimisticLock(productId, quantity, 0))
                .thenReturn(1);

        // When
        boolean result = productService.updateProductStock(productId, quantity);

        // Then
        assertThat(result).isTrue();
        verify(productMapper).selectById(productId);
        verify(productMapper).updateStockWithOptimisticLock(productId, quantity, 0);
    }

    @Test
    @DisplayName("更新商品库存 - 库存不足")
    void updateProductStock_InsufficientStock() {
        // Given
        Long productId = 1L;
        Integer quantity = 1000;
        Product lowStockProduct = ProductTestDataBuilder.lowStockProduct();

        when(productMapper.selectById(productId)).thenReturn(lowStockProduct);

        // When
        boolean result = productService.updateProductStock(productId, quantity);

        // Then
        assertThat(result).isFalse();
        verify(productMapper).selectById(productId);
        verify(productMapper, never()).updateStockWithOptimisticLock(any(), any(), any());
    }

    @Test
    @DisplayName("更新商品库存 - 商品不存在")
    void updateProductStock_ProductNotFound() {
        // Given
        Long productId = 999L;
        Integer quantity = 1;

        when(productMapper.selectById(productId)).thenReturn(null);

        // When
        boolean result = productService.updateProductStock(productId, quantity);

        // Then
        assertThat(result).isFalse();
        verify(productMapper).selectById(productId);
    }

    @Test
    @DisplayName("更新商品库存 - 并发冲突（乐观锁失败）")
    void updateProductStock_ConcurrentConflict() {
        // Given
        Long productId = 1L;
        Integer quantity = 1;

        when(productMapper.selectById(productId)).thenReturn(defaultProduct);
        when(productMapper.updateStockWithOptimisticLock(productId, quantity, 0))
                .thenReturn(0);

        // When
        boolean result = productService.updateProductStock(productId, quantity);

        // Then
        assertThat(result).isFalse();
        verify(productMapper).updateStockWithOptimisticLock(productId, quantity, 0);
    }

    @Test
    @DisplayName("增加商品销量 - 成功")
    void increaseSalesCount_Success() {
        // Given
        Long productId = 1L;
        Integer quantity = 2;

        // When
        productService.increaseSalesCount(productId, quantity);

        // Then
        verify(productMapper).increaseSalesCount(productId, quantity);
    }
}