package com.zqtravel.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zqtravel.shop.dto.PageResult;
import com.zqtravel.shop.dto.request.ProductQueryRequest;
import com.zqtravel.shop.dto.response.ProductDTO;
import com.zqtravel.shop.entity.Product;
import com.zqtravel.shop.mapper.ProductMapper;
import com.zqtravel.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    @Override
    public PageResult<ProductDTO> listProducts(ProductQueryRequest request) {
        Page<Product> page = new Page<>(request.getPage(), request.getSize());
        
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", 0);
        
        if (request.getCategoryId() != null) {
            queryWrapper.eq("category_id", request.getCategoryId());
        }
        
        if (StringUtils.hasText(request.getKeyword())) {
            queryWrapper.and(wrapper -> wrapper
                .like("name", request.getKeyword())
                .or()
                .like("description", request.getKeyword())
            );
        }
        
        if (request.getMinPrice() != null) {
            queryWrapper.ge("price", request.getMinPrice());
        }
        
        if (request.getMaxPrice() != null) {
            queryWrapper.le("price", request.getMaxPrice());
        }
        
        if (request.getStatus() != null) {
            queryWrapper.eq("status", request.getStatus());
        } else {
            queryWrapper.eq("status", 1); // 默认只查询上架商品
        }
        
        // 排序
        if (StringUtils.hasText(request.getSortBy())) {
            String order = "asc".equalsIgnoreCase(request.getOrder()) ? "ASC" : "DESC";
            queryWrapper.orderBy(true, "asc".equalsIgnoreCase(request.getOrder()), request.getSortBy());
        } else {
            queryWrapper.orderByDesc("created_at");
        }
        
        IPage<Product> productPage = productMapper.selectPage(page, queryWrapper);
        
        List<ProductDTO> productDTOs = productPage.getRecords().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        return PageResult.of(
                (int) productPage.getCurrent(),
                (int) productPage.getSize(),
                productPage.getTotal(),
                productDTOs
        );
    }

    @Override
    @Cacheable(value = "product", key = "#id", unless = "#result == null")
    public ProductDTO getProductById(Long id) {
        Product product = productMapper.selectById(id);
        if (product == null || product.getIsDeleted() == 1) {
            return null;
        }
        return convertToDTO(product);
    }

    @Override
    public List<ProductDTO> searchProducts(String keyword, Long categoryId) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", 0).eq("status", 1);
        
        if (StringUtils.hasText(keyword)) {
            queryWrapper.and(wrapper -> wrapper
                .like("name", keyword)
                .or()
                .like("description", keyword)
            );
        }
        
        if (categoryId != null) {
            queryWrapper.eq("category_id", categoryId);
        }
        
        queryWrapper.orderByDesc("sales_count");
        
        List<Product> products = productMapper.selectList(queryWrapper);
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "hotProducts", key = "#limit")
    public List<ProductDTO> getHotProducts(Integer limit) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", 0)
                   .eq("status", 1)
                   .orderByDesc("sales_count", "created_at")
                   .last("LIMIT " + (limit != null ? limit : 10));
        
        List<Product> products = productMapper.selectList(queryWrapper);
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateProductStock(Long productId, Integer quantity) {
        // 乐观锁更新库存
        Product product = productMapper.selectById(productId);
        if (product == null || product.getStock() < quantity) {
            return false;
        }
        
        int rows = productMapper.updateStockWithOptimisticLock(productId, quantity, product.getVersion());
        return rows > 0;
    }

    @Override
    public void increaseSalesCount(Long productId, Integer quantity) {
        productMapper.increaseSalesCount(productId, quantity);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        BeanUtils.copyProperties(product, dto);
        
        // 转换images JSON字符串为List
        // 这里简化处理，实际应该使用JSON解析
        if (StringUtils.hasText(product.getImages())) {
            // 简单处理，实际应该使用Jackson解析JSON数组
            dto.setImages(List.of(product.getImages().replaceAll("[\\[\\]\"]", "").split(",")));
        }
        
        // 设置状态描述
        if (product.getStatus() != null) {
            dto.setStatusDesc(product.getStatus() == 1 ? "上架" : "下架");
        }
        
        return dto;
    }
}