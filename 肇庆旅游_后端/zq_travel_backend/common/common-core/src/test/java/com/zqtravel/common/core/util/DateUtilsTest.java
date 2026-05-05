package com.zqtravel.common.core.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** DateUtils单元测试 */
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
		LocalDateTime newDateTime = DateUtils.addHours(dateTime, 3);

		assertEquals(LocalDateTime.of(2026, 5, 5, 13, 0, 0), newDateTime);
	}

	@Test
	@DisplayName("测试添加分钟")
	void testAddMinutes() {
		LocalDateTime dateTime = LocalDateTime.of(2026, 5, 5, 10, 0, 0);
		LocalDateTime newDateTime = DateUtils.addMinutes(dateTime, 45);

		assertEquals(LocalDateTime.of(2026, 5, 5, 10, 45, 0), newDateTime);
	}

	@Test
	@DisplayName("测试日期是否在范围内")
	void testIsBetweenDate() {
		LocalDate date = LocalDate.of(2026, 5, 5);
		LocalDate start = LocalDate.of(2026, 5, 1);
		LocalDate end = LocalDate.of(2026, 5, 10);

		assertTrue(DateUtils.isBetween(date, start, end));

		// 测试边界情况
		assertTrue(DateUtils.isBetween(start, start, end)); // 开始日期
		assertTrue(DateUtils.isBetween(end, start, end)); // 结束日期

		// 测试不在范围内
		LocalDate before = LocalDate.of(2026, 4, 30);
		LocalDate after = LocalDate.of(2026, 5, 11);
		assertFalse(DateUtils.isBetween(before, start, end));
		assertFalse(DateUtils.isBetween(after, start, end));
	}

	@Test
	@DisplayName("测试日期时间是否在范围内")
	void testIsBetweenDateTime() {
		LocalDateTime dateTime = LocalDateTime.of(2026, 5, 5, 12, 0, 0);
		LocalDateTime start = LocalDateTime.of(2026, 5, 5, 10, 0, 0);
		LocalDateTime end = LocalDateTime.of(2026, 5, 5, 14, 0, 0);

		assertTrue(DateUtils.isBetween(dateTime, start, end));

		// 测试边界情况
		assertTrue(DateUtils.isBetween(start, start, end)); // 开始时间
		assertTrue(DateUtils.isBetween(end, start, end)); // 结束时间

		// 测试不在范围内
		LocalDateTime before = LocalDateTime.of(2026, 5, 5, 9, 0, 0);
		LocalDateTime after = LocalDateTime.of(2026, 5, 5, 15, 0, 0);
		assertFalse(DateUtils.isBetween(before, start, end));
		assertFalse(DateUtils.isBetween(after, start, end));
	}

	@Test
	@DisplayName("测试获取当天开始时间")
	void testStartOfDay() {
		LocalDateTime startOfDay = DateUtils.startOfDay();
		assertNotNull(startOfDay);

		// 验证时间是00:00:00
		assertEquals(0, startOfDay.getHour());
		assertEquals(0, startOfDay.getMinute());
		assertEquals(0, startOfDay.getSecond());
		assertEquals(0, startOfDay.getNano());
	}

	@Test
	@DisplayName("测试获取指定日期开始时间")
	void testStartOfDayWithDate() {
		LocalDate date = LocalDate.of(2026, 5, 5);
		LocalDateTime startOfDay = DateUtils.startOfDay(date);

		assertEquals(LocalDateTime.of(2026, 5, 5, 0, 0, 0), startOfDay);
	}

	@Test
	@DisplayName("测试获取当天结束时间")
	void testEndOfDay() {
		LocalDateTime endOfDay = DateUtils.endOfDay();
		assertNotNull(endOfDay);

		// 验证时间是23:59:59.999999999
		assertEquals(23, endOfDay.getHour());
		assertEquals(59, endOfDay.getMinute());
		assertEquals(59, endOfDay.getSecond());
		assertEquals(999999999, endOfDay.getNano());
	}

	@Test
	@DisplayName("测试获取指定日期结束时间")
	void testEndOfDayWithDate() {
		LocalDate date = LocalDate.of(2026, 5, 5);
		LocalDateTime endOfDay = DateUtils.endOfDay(date);

		assertEquals(LocalDateTime.of(2026, 5, 5, 23, 59, 59, 999999999), endOfDay);
	}

	@Test
	@DisplayName("测试格式化null日期时间")
	void testFormatNullDateTime() {
		assertNull(DateUtils.format((LocalDateTime) null));
		assertNull(DateUtils.format((LocalDateTime) null, "yyyy-MM-dd"));
	}

	@Test
	@DisplayName("测试格式化null日期")
	void testFormatNullDate() {
		assertNull(DateUtils.format((LocalDate) null));
		assertNull(DateUtils.format((LocalDate) null, "yyyy-MM-dd"));
	}

	@Test
	@DisplayName("测试格式化null Date对象")
	void testFormatNullDateObject() {
		assertNull(DateUtils.format((Date) null));
		assertNull(DateUtils.format((Date) null, "yyyy-MM-dd"));
	}

	@Test
	@DisplayName("测试解析空字符串")
	void testParseEmptyString() {
		assertNull(DateUtils.parseDateTime(""));
		assertNull(DateUtils.parseDateTime("  "));
		assertNull(DateUtils.parseDateTime(null));

		assertNull(DateUtils.parseDate(""));
		assertNull(DateUtils.parseDate("  "));
		assertNull(DateUtils.parseDate(null));

		assertNull(DateUtils.parse(""));
		assertNull(DateUtils.parse("  "));
		assertNull(DateUtils.parse(null));
	}

	@Test
	@DisplayName("测试常量定义")
	void testConstants() {
		assertEquals("yyyy-MM-dd", DateUtils.DEFAULT_DATE_PATTERN);
		assertEquals("yyyy-MM-dd HH:mm:ss", DateUtils.DEFAULT_DATETIME_PATTERN);
		assertEquals("HH:mm:ss", DateUtils.DEFAULT_TIME_PATTERN);
		assertEquals(ZoneId.of("Asia/Shanghai"), DateUtils.DEFAULT_ZONE_ID);
	}

	@Test
	@DisplayName("测试工具类不可实例化")
	void testUtilityClass() {
		// 验证工具类有@UtilityClass注解，不能实例化
		// 这里我们验证类有私有构造函数（通过反射）
		try {
			java.lang.reflect.Constructor<?>[] constructors =
					DateUtils.class.getDeclaredConstructors();
			for (java.lang.reflect.Constructor<?> constructor : constructors) {
				assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
			}
		} catch (Exception e) {
			fail("工具类构造函数检查失败: " + e.getMessage());
		}
	}
}
