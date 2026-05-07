package com.zqtravel.shop.service;

import com.zqtravel.shop.dto.PageResult;
import com.zqtravel.shop.dto.request.ProductQueryRequest;
import com.zqtravel.shop.dto.response.ProductDTO;

import java.util.List;

public interface ProductService {

    /**
     * 分页查询商品列表
     */
    PageResult<ProductDTO> listProducts(ProductQueryRequest request);

    /**
     * 根据ID获取商品详情
     */
    ProductDTO getProductById(Long id);

    /**
     * 搜索商品
     */
    List<ProductDTO> searchProducts(String keyword, Long categoryId);

    /**
     * 获取热门商品
     */
    List<ProductDTO> getHotProducts(Integer limit);

    /**
     * 更新商品库存（乐观锁）
     */
    boolean updateProductStock(Long productId, Integer quantity);

    /**
     * 增加商品销量
     */
    void increaseSalesCount(Long productId, Integer quantity);
}