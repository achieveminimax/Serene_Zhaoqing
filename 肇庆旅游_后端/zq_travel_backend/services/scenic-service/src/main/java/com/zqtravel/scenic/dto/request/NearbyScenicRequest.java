package com.zqtravel.scenic.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 附近景点查询请求DTO
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@Schema(description = "附近景点查询请求")
public class NearbyScenicRequest {

    @Schema(description = "纬度", required = true, example = "23.0516")
    @NotNull(message = "纬度不能为空")
    @DecimalMin(value = "-90.0", message = "纬度范围无效")
    @DecimalMax(value = "90.0", message = "纬度范围无效")
    private BigDecimal lat;

    @Schema(description = "经度", required = true, example = "112.4587")
    @NotNull(message = "经度不能为空")
    @DecimalMin(value = "-180.0", message = "经度范围无效")
    @DecimalMax(value = "180.0", message = "经度范围无效")
    private BigDecimal lng;

    @Schema(description = "搜索半径（公里）", example = "10.0")
    private Double radius = 10.0;

    @Schema(description = "返回数量限制", example = "20")
    private Integer limit = 20;
}