package com.zqtravel.shop.builder;

import com.zqtravel.shop.entity.ProductCategory;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 分类测试数据构建器
 */
public class CategoryTestDataBuilder {

    /**
     * 创建默认分类
     */
    public static ProductCategory defaultCategory() {
        return ProductCategory.builder()
                .id(1L)
                .name("肇庆特产")
                .parentId(0L)
                .icon("https://example.com/icons/specialty.png")
                .sortOrder(1)
                .createdAt(LocalDateTime.now())
                .build();
    }

    /**
     * 创建子分类
     */
    public static ProductCategory childCategory() {
        return ProductCategory.builder()
                .id(2L)
                .name("糕点")
                .parentId(1L)
                .icon("https://example.com/icons/cake.png")
                .sortOrder(1)
                .createdAt(LocalDateTime.now())
                .build();
    }

    /**
     * 创建指定父分类的子分类
     */
    public static ProductCategory categoryWithParent(Long parentId) {
        return ProductCategory.builder()
                .id(3L)
                .name("茶叶")
                .parentId(parentId)
                .icon("https://example.com/icons/tea.png")
                .sortOrder(1)
                .createdAt(LocalDateTime.now())
                .build();
    }

    /**
     * 创建分类树数据
     */
    public static List<ProductCategory> categoryTree() {
        return Arrays.asList(
                ProductCategory.builder()
                        .id(1L)
                        .name("肇庆特产")
                        .parentId(0L)
                        .sortOrder(1)
                        .createdAt(LocalDateTime.now())
                        .build(),
                ProductCategory.builder()
                        .id(2L)
                        .name("旅游纪念品")
                        .parentId(0L)
                        .sortOrder(2)
                        .createdAt(LocalDateTime.now())
                        .build(),
                ProductCategory.builder()
                        .id(3L)
                        .name("糕点")
                        .parentId(1L)
                        .sortOrder(1)
                        .createdAt(LocalDateTime.now())
                        .build(),
                ProductCategory.builder()
                        .id(4L)
                        .name("茶叶")
                        .parentId(1L)
                        .sortOrder(2)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    /**
     * 创建指定ID的分类
     */
    public static ProductCategory categoryWithId(Long id) {
        return ProductCategory.builder()
                .id(id)
                .name("测试分类" + id)
                .parentId(0L)
                .sortOrder(1)
                .createdAt(LocalDateTime.now())
                .build();
    }
}