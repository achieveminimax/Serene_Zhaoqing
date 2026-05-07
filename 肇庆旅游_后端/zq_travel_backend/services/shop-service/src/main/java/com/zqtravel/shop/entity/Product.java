package com.zqtravel.shop.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("products")
public class Product {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;

    @TableField("category_id")
    private Long categoryId;

    @TableField("description")
    private String description;

    @TableField("images")
    private String images; // JSON字符串存储图片列表

    @TableField("price")
    private BigDecimal price;

    @TableField("original_price")
    private BigDecimal originalPrice;

    @TableField("stock")
    private Integer stock;

    @TableField("sales_count")
    private Integer salesCount;

    @TableField("status")
    private Integer status; // 0-下架 1-上架

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @TableField("is_deleted")
    @TableLogic
    private Integer isDeleted;

    @Version
    @TableField("version")
    private Integer version; // 乐观锁版本号
}