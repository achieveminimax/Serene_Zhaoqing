package com.zqtravel.common.core.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DateUtils单元测试
 */
@DisplayName("DateUtils单元测试")
class DateUtilsTest {

    @Test
    @DisplayName("测试当前日期时间")
    void testNow() {
        LocalDateTime now = DateUtils.now();
        assertNotNull(now);
        
        // 验证时区
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        LocalDateTime expectedNow = LocalDateTime.now(zoneId);
        
        // 由于时间差，只验证年份、月份、日期相同
        assertEquals(expectedNow.getYear(), now.getYear());
        assertEquals(expectedNow.getMonth(), now.getMonth());
        assertEquals(expectedNow.getDayOfMonth(), now.getDayOfMonth());
    }

    @Test
    @DisplayName("测试当前日期")
    void testToday() {
        LocalDate today = DateUtils.today();
        assertNotNull(today);
        
        LocalDate expectedToday = LocalDate.now(DateUtils.DEFAULT_ZONE_ID);
        assertEquals(expectedToday, today);
    }

    @Test
    @DisplayName("测试当前时间戳")
    void testCurrentTimeMillis() {
        long timestamp = DateUtils.currentTimeMillis();
        assertTrue(timestamp > 0);
        
        // 验证时间戳在合理范围内（2026年之后）
        assertTrue(timestamp > 1700000000000L); // 2023年左右
    }

    @Test
    @DisplayName("测试格式化日期时间（默认格式）")
    void testFormatDateTimeDefault() {
        LocalDateTime dateTime = LocalDateTime.of(2026, 5, 5, 10, 30, 45);
        String formatted = DateUtils.format(dateTime);
        
        assertEquals("2026-05-05 10:30:45", formatted);
    }

    @Test
    @DisplayName("测试格式化日期时间（自定义格式）")
    void testFormatDateTimeCustom() {
        LocalDateTime dateTime = LocalDateTime.of(2026, 5, 5, 10, 30, 45);
        String formatted = DateUtils.format(dateTime, "yyyy/MM/dd HH:mm");
        
        assertEquals("2026/05/05 10:30", formatted);
    }

    @Test
    @DisplayName("测试格式化日期（默认格式）")
    void testFormatDateDefault() {
        LocalDate date = LocalDate.of(2026, 5, 5);
        String formatted = DateUtils.format(date);
        
        assertEquals("2026-05-05", formatted);
    }

    @Test
    @DisplayName("测试格式化日期（自定义格式）")
    void testFormatDateCustom() {
        LocalDate date = LocalDate.of(2026, 5, 5);
        String formatted = DateUtils.format(date, "yyyy年MM月dd日");
        
        assertEquals("2026年05月05日", formatted);
    }

    @Test
    @DisplayName("测试格式化Date对象（默认格式）")
    void testFormatDateObjectDefault() {
        Date date = java.sql.Date.valueOf("2026-05-05");
        String formatted = DateUtils.format(date);
        
        // 注意：Date对象不包含时间部分，格式化后时间会是00:00:00
        assertTrue(formatted.startsWith("2026-05-05"));
    }

    @Test
    @DisplayName("测试解析日期时间字符串（默认格式）")
    void testParseDateTimeDefault() {
        String dateTimeStr = "2026-05-05 10:30:45";
        LocalDateTime dateTime = DateUtils.parseDateTime(dateTimeStr);
        
        assertNotNull(dateTime);
        assertEquals(2026, dateTime.getYear());
        assertEquals(5, dateTime.getMonthValue());
        assertEquals(5, dateTime.getDayOfMonth());
        assertEquals(10, dateTime.getHour());
        assertEquals(30, dateTime.getMinute());
        assertEquals(45, dateTime.getSecond());
    }

    @Test
    @DisplayName("测试解析日期字符串（默认格式）")
    void testParseDateDefault() {
        String dateStr = "2026-05-05";
        LocalDate date = DateUtils.parseDate(dateStr);
        
        assertNotNull(date);
        assertEquals(2026, date.getYear());
        assertEquals(5, date.getMonthValue());
        assertEquals(5, date.getDayOfMonth());
    }

    @Test
    @DisplayName("测试解析Date对象字符串（默认格式）")
    void testParseDefault() {
        String dateStr = "2026-05-05 10:30:45";
        Date date = DateUtils.parse(dateStr);
        
        assertNotNull(date);
        // 验证日期大致正确
        assertTrue(date.getTime() > 0);
    }

    @Test
    @DisplayName("测试计算天数差")
    void testDaysBetween() {
        LocalDate startDate = LocalDate.of(2026, 5, 1);
        LocalDate endDate = LocalDate.of(2026, 5, 10);
        
        long days = DateUtils.daysBetween(startDate, endDate);
        assertEquals(9, days);
    }

    @Test
    @DisplayName("测试计算小时差")
    void testHoursBetween() {
        LocalDateTime startDateTime = LocalDateTime.of(2026, 5, 5, 10, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2026, 5, 5, 15, 30, 0);
        
        long hours = DateUtils.hoursBetween(startDateTime, endDateTime);
        assertEquals(5, hours); // 5.5小时向下取整为5小时
    }

    @Test
    @DisplayName("测试计算分钟差")
    void testMinutesBetween() {
        LocalDateTime startDateTime = LocalDateTime.of(2026, 5, 5, 10, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2026, 5, 5, 10, 45, 0);
        
        long minutes = DateUtils.minutesBetween(startDateTime, endDateTime);
        assertEquals(45, minutes);
    }

    @Test
    @DisplayName("测试计算秒数差")
    void testSecondsBetween() {
        LocalDateTime startDateTime = LocalDateTime.of(2026, 5, 5, 10, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2026, 5, 5, 10, 0, 30);
        
        long seconds = DateUtils.secondsBetween(startDateTime, endDateTime);
        assertEquals(30, seconds);
    }

    @Test
    @DisplayName("测试添加天数")
    void testAddDays() {
        LocalDate date = LocalDate.of(2026, 5, 5);
        LocalDate newDate = DateUtils.addDays(date, 7);
        
        assertEquals(LocalDate.of(2026, 5, 12), newDate);
    }

    @Test
    @DisplayName("测试添加小时")
    void testAddHours() {
        LocalDateTime dateTime = LocalDateTime.of(2026, 5, 5, 10, 0, 0);
        LocalDateTime newDateTime =