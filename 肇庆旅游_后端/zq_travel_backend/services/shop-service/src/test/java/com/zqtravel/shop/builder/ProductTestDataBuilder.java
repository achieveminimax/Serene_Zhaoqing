package com.zqtravel.shop.builder;

import com.zqtravel.shop.entity.Product;

import java.math.BigDecimal;

/**
 * 商品测试数据构建器
 * 使用Builder模式简化测试数据创建
 */
public class ProductTestDataBuilder {

    /**
     * 创建默认商品构建器
     */
    public static Product.ProductBuilder defaultProduct() {
        return Product.builder()
                .id(1L)
                .name("肇庆裹蒸粽")
                .categoryId(6L)
                .description("肇庆传统特产，糯米包裹绿豆、猪肉等馅料")
                .images("[\"https://example.com/images/zongzi1.jpg\", \"https://example.com/images/zongzi2.jpg\"]")
                .price(new BigDecimal("25.00"))
                .originalPrice(new BigDecimal("30.00"))
                .stock(100)
                .salesCount(50)
                .status(1)
                .isDeleted(0)
                .version(0);
    }

    /**
     * 创建已下架商品
     */
    public static Product offlineProduct() {
        return defaultProduct()
                .status(0)
                .build();
    }

    /**
     * 创建已删除商品
     */
    public static Product deletedProduct() {
        return defaultProduct()
                .isDeleted(1)
                .build();
    }

    /**
     * 创建低库存商品
     */
    public static Product lowStockProduct() {
        return defaultProduct()
                .stock(5)
                .build();
    }

    /**
     * 创建无库存商品
     */
    public static Product outOfStockProduct() {
        return defaultProduct()
                .stock(0)
                .build();
    }

    /**
     * 创建指定ID的商品
     */
    public static Product productWithId(Long id) {
        return defaultProduct()
                .id(id)
                .build();
    }

    /**
     * 创建指定价格的商品
     */
    public static Product productWithPrice(BigDecimal price) {
        return defaultProduct()
                .price(price)
                .build();
    }

    /**
     * 创建指定分类的商品
     */
    public static Product productWithCategory(Long categoryId) {
        return defaultProduct()
                .categoryId(categoryId)
                .build();
    }
}