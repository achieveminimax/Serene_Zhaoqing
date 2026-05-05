package com.zqtravel.common.core.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import cn.hutool.core.date.DateUtil;
import lombok.experimental.UtilityClass;

/**
 * 日期时间工具类
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-05
 */
@UtilityClass
public class DateUtils {

	/** 默认日期格式：yyyy-MM-dd */
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

	/** 默认日期时间格式：yyyy-MM-dd HH:mm:ss */
	public static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/** 默认时间格式：HH:mm:ss */
	public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";

	/** 默认时区：Asia/Shanghai */
	public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Asia/Shanghai");

	/**
	 * 获取当前日期时间
	 *
	 * @return 当前日期时间
	 */
	public static LocalDateTime now() {
		return LocalDateTime.now(DEFAULT_ZONE_ID);
	}

	/**
	 * 获取当前日期
	 *
	 * @return 当前日期
	 */
	public static LocalDate today() {
		return LocalDate.now(DEFAULT_ZONE_ID);
	}

	/**
	 * 获取当前时间戳（毫秒）
	 *
	 * @return 当前时间戳
	 */
	public static long currentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * 格式化日期时间为字符串（默认格式）
	 *
	 * @param dateTime 日期时间
	 * @return 格式化后的字符串
	 */
	public static String format(LocalDateTime dateTime) {
		return format(dateTime, DEFAULT_DATETIME_PATTERN);
	}

	/**
	 * 格式化日期时间为字符串
	 *
	 * @param dateTime 日期时间
	 * @param pattern 格式模式
	 * @return 格式化后的字符串
	 */
	public static String format(LocalDateTime dateTime, String pattern) {
		if (dateTime == null) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return dateTime.format(formatter);
	}

	/**
	 * 格式化日期为字符串（默认格式）
	 *
	 * @param date 日期
	 * @return 格式化后的字符串
	 */
	public static String format(LocalDate date) {
		return format(date, DEFAULT_DATE_PATTERN);
	}

	/**
	 * 格式化日期为字符串
	 *
	 * @param date 日期
	 * @param pattern 格式模式
	 * @return 格式化后的字符串
	 */
	public static String format(LocalDate date, String pattern) {
		if (date == null) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return date.format(formatter);
	}

	/**
	 * 格式化Date为字符串（默认格式）
	 *
	 * @param date Date对象
	 * @return 格式化后的字符串
	 */
	public static String format(Date date) {
		return format(date, DEFAULT_DATETIME_PATTERN);
	}

	/**
	 * 格式化Date为字符串
	 *
	 * @param date Date对象
	 * @param pattern 格式模式
	 * @return 格式化后的字符串
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		return DateUtil.format(date, pattern);
	}

	/**
	 * 解析字符串为日期时间（默认格式）
	 *
	 * @param dateTimeStr 日期时间字符串
	 * @return 日期时间对象
	 */
	public static LocalDateTime parseDateTime(String dateTimeStr) {
		return parseDateTime(dateTimeStr, DEFAULT_DATETIME_PATTERN);
	}

	/**
	 * 解析字符串为日期时间
	 *
	 * @param dateTimeStr 日期时间字符串
	 * @param pattern 格式模式
	 * @return 日期时间对象
	 */
	public static LocalDateTime parseDateTime(String dateTimeStr, String pattern) {
		if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.parse(dateTimeStr, formatter);
	}

	/**
	 * 解析字符串为日期（默认格式）
	 *
	 * @param dateStr 日期字符串
	 * @return 日期对象
	 */
	public static LocalDate parseDate(String dateStr) {
		return parseDate(dateStr, DEFAULT_DATE_PATTERN);
	}

	/**
	 * 解析字符串为日期
	 *
	 * @param dateStr 日期字符串
	 * @param pattern 格式模式
	 * @return 日期对象
	 */
	public static LocalDate parseDate(String dateStr, String pattern) {
		if (dateStr == null || dateStr.trim().isEmpty()) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDate.parse(dateStr, formatter);
	}

	/**
	 * 解析字符串为Date对象（默认格式）
	 *
	 * @param dateStr 日期字符串
	 * @return Date对象
	 */
	public static Date parse(String dateStr) {
		return parse(dateStr, DEFAULT_DATETIME_PATTERN);
	}

	/**
	 * 解析字符串为Date对象
	 *
	 * @param dateStr 日期字符串
	 * @param pattern 格式模式
	 * @return Date对象
	 */
	public static Date parse(String dateStr, String pattern) {
		if (dateStr == null || dateStr.trim().isEmpty()) {
			return null;
		}
		return DateUtil.parse(dateStr, pattern);
	}

	/**
	 * 计算两个日期之间的天数差
	 *
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return 天数差
	 */
	public static long daysBetween(LocalDate startDate, LocalDate endDate) {
		return endDate.toEpochDay() - startDate.toEpochDay();
	}

	/**
	 * 计算两个日期时间之间的小时差
	 *
	 * @param startDateTime 开始日期时间
	 * @param endDateTime 结束日期时间
	 * @return 小时差
	 */
	public static long hoursBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		return java.time.Duration.between(startDateTime, endDateTime).toHours();
	}

	/**
	 * 计算两个日期时间之间的分钟差
	 *
	 * @param startDateTime 开始日期时间
	 * @param endDateTime 结束日期时间
	 * @return 分钟差
	 */
	public static long minutesBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		return java.time.Duration.between(startDateTime, endDateTime).toMinutes();
	}

	/**
	 * 计算两个日期时间之间的秒数差
	 *
	 * @param startDateTime 开始日期时间
	 * @param endDateTime 结束日期时间
	 * @return 秒数差
	 */
	public static long secondsBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		return java.time.Duration.between(startDateTime, endDateTime).getSeconds();
	}

	/**
	 * 添加天数
	 *
	 * @param date 日期
	 * @param days 天数
	 * @return 添加后的日期
	 */
	public static LocalDate addDays(LocalDate date, long days) {
		return date.plusDays(days);
	}

	/**
	 * 添加小时
	 *
	 * @param dateTime 日期时间
	 * @param hours 小时数
	 * @return 添加后的日期时间
	 */
	public static LocalDateTime addHours(LocalDateTime dateTime, long hours) {
		return dateTime.plusHours(hours);
	}

	/**
	 * 添加分钟
	 *
	 * @param dateTime 日期时间
	 * @param minutes 分钟数
	 * @return 添加后的日期时间
	 */
	public static LocalDateTime addMinutes(LocalDateTime dateTime, long minutes) {
		return dateTime.plusMinutes(minutes);
	}

	/**
	 * 判断日期是否在指定范围内
	 *
	 * @param date 日期
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return 是否在范围内
	 */
	public static boolean isBetween(LocalDate date, LocalDate start, LocalDate end) {
		return !date.isBefore(start) && !date.isAfter(end);
	}

	/**
	 * 判断日期时间是否在指定范围内
	 *
	 * @param dateTime 日期时间
	 * @param start 开始日期时间
	 * @param end 结束日期时间
	 * @return 是否在范围内
	 */
	public static boolean isBetween(
			LocalDateTime dateTime, LocalDateTime start, LocalDateTime end) {
		return !dateTime.isBefore(start) && !dateTime.isAfter(end);
	}

	/**
	 * 获取当天的开始时间（00:00:00）
	 *
	 * @return 当天的开始时间
	 */
	public static LocalDateTime startOfDay() {
		return startOfDay(today());
	}

	/**
	 * 获取指定日期的开始时间（00:00:00）
	 *
	 * @param date 日期
	 * @return 开始时间
	 */
	public static LocalDateTime startOfDay(LocalDate date) {
		return date.atStartOfDay();
	}

	/**
	 * 获取当天的结束时间（23:59:59.999999999）
	 *
	 * @return 当天的结束时间
	 */
	public static LocalDateTime endOfDay() {
		return endOfDay(today());
	}

	/**
	 * 获取指定日期的结束时间（23:59:59.999999999）
	 *
	 * @param date 日期
	 * @return 结束时间
	 */
	public static LocalDateTime endOfDay(LocalDate date) {
		return date.atTime(23, 59, 59, 999999999);
	}
}
