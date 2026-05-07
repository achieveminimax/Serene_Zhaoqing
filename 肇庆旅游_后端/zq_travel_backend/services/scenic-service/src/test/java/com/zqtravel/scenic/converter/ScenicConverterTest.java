package com.zqtravel.scenic.converter;

import com.zqtravel.scenic.dto.response.ScenicSpotDTO;
import com.zqtravel.scenic.dto.vo.ScenicDetailVO;
import com.zqtravel.scenic.entity.ScenicSpot;
import com.zqtravel.scenic.util.TestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ScenicConverter 单元测试
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@DisplayName("景点转换器测试")
class ScenicConverterTest {

    @Test
    @DisplayName("转换为DTO - 正常实体")
    void toDTO_WithValidEntity_ShouldReturnDTO() {
        // Given
        ScenicSpot entity = TestDataFactory.createScenicSpot(1L, "七星岩");

        // When
        ScenicSpotDTO dto = ScenicConverter.toDTO(entity);

        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("七星岩");
        assertThat(dto.getCategoryId()).isEqualTo(TestDataFactory.DEFAULT_CATEGORY_ID);
        assertThat(dto.getAqi()).isEqualTo(45);
        assertThat(dto.getViewCount()).isEqualTo(1000);
        assertThat(dto.getFavoriteCount()).isEqualTo(200);
    }

    @Test
    @DisplayName("转换为DTO - null实体")
    void toDTO_WithNullEntity_ShouldReturnNull() {
        // When
        ScenicSpotDTO dto = ScenicConverter.toDTO(null);

        // Then
        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("转换为DTO - 解析图片JSON")
    void toDTO_WithImagesJson_ShouldParseImages() {
        // Given
        ScenicSpot entity = TestDataFactory.createScenicSpot(1L, "七星岩");
        entity.setImages("[\"https://example.com/image1.jpg\", \"https://example.com/image2.jpg\"]");

        // When
        ScenicSpotDTO dto = ScenicConverter.toDTO(entity);

        // Then
        assertThat(dto.getImages()).hasSize(2);
        assertThat(dto.getImages().get(0)).isEqualTo("https://example.com/image1.jpg");
    }

    @Test
    @DisplayName("转换为DTO - 空图片JSON")
    void toDTO_WithEmptyImages_ShouldReturnEmptyList() {
        // Given
        ScenicSpot entity = TestDataFactory.createScenicSpot(1L, "七星岩");
        entity.setImages("");

        // When
        ScenicSpotDTO dto = ScenicConverter.toDTO(entity);

        // Then
        assertThat(dto.getImages()).isEmpty();
    }

    @Test
    @DisplayName("转换为DTO - 无效图片JSON")
    void toDTO_WithInvalidImagesJson_ShouldReturnEmptyList() {
        // Given
        ScenicSpot entity = TestDataFactory.createScenicSpot(1L, "七星岩");
        entity.setImages("invalid json");

        // When
        ScenicSpotDTO dto = ScenicConverter.toDTO(entity);

        // Then
        assertThat(dto.getImages()).isEmpty();
    }

    @Test
    @DisplayName("转换为DTO列表 - 正常列表")
    void toDTOList_WithValidList_ShouldReturnDTOList() {
        // Given
        List<ScenicSpot> entities = TestDataFactory.createScenicSpotList(3);

        // When
        List<ScenicSpotDTO> dtos = ScenicConverter.toDTOList(entities);

        // Then
        assertThat(dtos).hasSize(3);
        assertThat(dtos.get(0).getName()).isEqualTo("景点1");
    }

    @Test
    @DisplayName("转换为DTO列表 - 包含null元素")
    void toDTOList_WithNullElement_ShouldSkipNull() {
        // Given
        List<ScenicSpot> entities = new ArrayList<>();
        entities.add(TestDataFactory.createScenicSpot(1L, "景点1"));
        entities.add(null);
        entities.add(TestDataFactory.createScenicSpot(2L, "景点2"));

        // When
        List<ScenicSpotDTO> dtos = ScenicConverter.toDTOList(entities);

        // Then
        assertThat(dtos).hasSize(2);
    }

    @Test
    @DisplayName("转换为DTO列表 - 空列表")
    void toDTOList_WithEmptyList_ShouldReturnEmptyList() {
        // When
        List<ScenicSpotDTO> dtos = ScenicConverter.toDTOList(Collections.emptyList());

        // Then
        assertThat(dtos).isEmpty();
    }

    @Test
    @DisplayName("转换为详情VO - 正常实体")
    void toDetailVO_WithValidEntity_ShouldReturnDetailVO() {
        // Given
        ScenicSpot entity = TestDataFactory.createScenicSpot(1L, "七星岩");
        entity.setAqi(45);

        // When
        ScenicDetailVO vo = ScenicConverter.toDetailVO(entity);

        // Then
        assertThat(vo).isNotNull();
        assertThat(vo.getId()).isEqualTo(1L);
        assertThat(vo.getName()).isEqualTo("七星岩");
        assertThat(vo.getAqiLevel()).isEqualTo("优");
    }

    @Test
    @DisplayName("转换为详情VO - null实体")
    void toDetailVO_WithNullEntity_ShouldReturnNull() {
        // When
        ScenicDetailVO vo = ScenicConverter.toDetailVO(null);

        // Then
        assertThat(vo).isNull();
    }

    @Test
    @DisplayName("转换为详情VO - AQI等级优")
    void toDetailVO_WithAqiExcellent_ShouldReturnExcellent() {
        // Given
        ScenicSpot entity = TestDataFactory.createScenicSpot(1L, "七星岩");
        entity.setAqi(30);

        // When
        ScenicDetailVO vo = ScenicConverter.toDetailVO(entity);

        // Then
        assertThat(vo.getAqiLevel()).isEqualTo("优");
    }

    @Test
    @DisplayName("转换为详情VO - AQI等级良")
    void toDetailVO_WithAqiGood_ShouldReturnGood() {
        // Given
        ScenicSpot entity = TestDataFactory.createScenicSpot(1L, "七星岩");
        entity.setAqi(80);

        // When
        ScenicDetailVO vo = ScenicConverter.toDetailVO(entity);

        // Then
        assertThat(vo.getAqiLevel()).isEqualTo("良");
    }

    @Test
    @DisplayName("转换为详情VO - AQI等级轻度污染")
    void toDetailVO_WithAqiLightPollution_ShouldReturnLightPollution() {
        // Given
        ScenicSpot entity = TestDataFactory.createScenicSpot(1L, "七星岩");
        entity.setAqi(120);

        // When
        ScenicDetailVO vo = ScenicConverter.toDetailVO(entity);

        // Then
        assertThat(vo.getAqiLevel()).isEqualTo("轻度污染");
    }

    @Test
    @DisplayName("转换为详情VO - AQI等级中度污染")
    void toDetailVO_WithAqiModeratePollution_ShouldReturnModeratePollution() {
        // Given
        ScenicSpot entity = TestDataFactory.createScenicSpot(1L, "七星岩");
        entity.setAqi(170);

        // When
        ScenicDetailVO vo = ScenicConverter.toDetailVO(entity);

        // Then
        assertThat(vo.getAqiLevel()).isEqualTo("中度污染");
    }

    @Test
    @DisplayName("转换为详情VO - AQI等级重度污染")
    void toDetailVO_WithAqiHeavyPollution_ShouldReturnHeavyPollution() {
        // Given
        ScenicSpot entity = TestDataFactory.createScenicSpot(1L, "七星岩");
        entity.setAqi(250);

        // When
        ScenicDetailVO vo = ScenicConverter.toDetailVO(entity);

        // Then
        assertThat(vo.getAqiLevel()).isEqualTo("重度污染");
    }

    @Test
    @DisplayName("转换为详情VO - AQI等级严重污染")
    void toDetailVO_WithAqiSeverePollution_ShouldReturnSeverePollution() {
        // Given
        ScenicSpot entity = TestDataFactory.createScenicSpot(1L, "七星岩");
        entity.setAqi(350);

        // When
        ScenicDetailVO vo = ScenicConverter.toDetailVO(entity);

        // Then
        assertThat(vo.getAqiLevel()).isEqualTo("严重污染");
    }

    @Test
    @DisplayName("转换为详情VO - AQI为null")
    void toDetailVO_WithNullAqi_ShouldReturnUnknown() {
        // Given
        ScenicSpot entity = TestDataFactory.createScenicSpot(1L, "七星岩");
        entity.setAqi(null);

        // When
        ScenicDetailVO vo = ScenicConverter.toDetailVO(entity);

        // Then
        assertThat(vo.getAqiLevel()).isNull();
    }

    @Test
    @DisplayName("转换为详情VO - 边界值AQI=50")
    void toDetailVO_WithAqi50_ShouldReturnExcellent() {
        // Given
        ScenicSpot entity = TestDataFactory.createScenicSpot(1L, "七星岩");
        entity.setAqi(50);

        // When
        ScenicDetailVO vo = ScenicConverter.toDetailVO(entity);

        // Then
        assertThat(vo.getAqiLevel()).isEqualTo("优");
    }

    @Test
    @DisplayName("转换为详情VO - 边界值AQI=51")
    void toDetailVO_WithAqi51_ShouldReturnGood() {
        // Given
        ScenicSpot entity = TestDataFactory.createScenicSpot(1L, "七星岩");
        entity.setAqi(51);

        // When
        ScenicDetailVO vo = ScenicConverter.toDetailVO(entity);

        // Then
        assertThat(vo.getAqiLevel()).isEqualTo("良");
    }
}
