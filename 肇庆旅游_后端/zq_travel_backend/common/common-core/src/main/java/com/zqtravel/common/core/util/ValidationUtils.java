package com.zqtravel.common.core.util;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

import com.zqtravel.common.core.exception.BusinessException;

import lombok.experimental.UtilityClass;

/**
 * 参数验证工具类
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-05
 */
@UtilityClass
public class ValidationUtils {

	/** 手机号正则表达式 */
	public static final String PHONE_REGEX = "^1[3-9]\\d{9}$";

	/** 邮箱正则表达式 */
	public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

	/** 身份证号正则表达式（简单验证） */
	public static final String ID_CARD_REGEX =
			"^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";

	/** URL正则表达式 */
	public static final String URL_REGEX = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$";

	/** IP地址正则表达式 */
	public static final String IP_REGEX =
			"^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$";

	/** 中文正则表达式 */
	public static final String CHINESE_REGEX = "^[\\u4e00-\\u9fa5]+$";

	/** 数字正则表达式 */
	public static final String NUMBER_REGEX = "^\\d+$";

	/** 字母正则表达式 */
	public static final String LETTER_REGEX = "^[a-zA-Z]+$";

	/** 字母数字正则表达式 */
	public static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9]+$";

	/**
	 * 验证对象不为null
	 *
	 * @param object 对象
	 * @param message 错误消息
	 */
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证对象为null
	 *
	 * @param object 对象
	 * @param message 错误消息
	 */
	public static void isNull(Object object, String message) {
		if (object != null) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证字符串不为空
	 *
	 * @param str 字符串
	 * @param message 错误消息
	 */
	public static void notBlank(String str, String message) {
		if (StringUtils.isBlank(str)) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证字符串为空
	 *
	 * @param str 字符串
	 * @param message 错误消息
	 */
	public static void isBlank(String str, String message) {
		if (StringUtils.isNotBlank(str)) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证集合不为空
	 *
	 * @param collection 集合
	 * @param message 错误消息
	 */
	public static void notEmpty(Collection<?> collection, String message) {
		if (collection == null || collection.isEmpty()) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证集合为空
	 *
	 * @param collection 集合
	 * @param message 错误消息
	 */
	public static void isEmpty(Collection<?> collection, String message) {
		if (collection != null && !collection.isEmpty()) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证Map不为空
	 *
	 * @param map Map
	 * @param message 错误消息
	 */
	public static void notEmpty(Map<?, ?> map, String message) {
		if (map == null || map.isEmpty()) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证Map为空
	 *
	 * @param map Map
	 * @param message 错误消息
	 */
	public static void isEmpty(Map<?, ?> map, String message) {
		if (map != null && !map.isEmpty()) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证数组不为空
	 *
	 * @param array 数组
	 * @param message 错误消息
	 */
	public static void notEmpty(Object[] array, String message) {
		if (array == null || array.length == 0) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证数组为空
	 *
	 * @param array 数组
	 * @param message 错误消息
	 */
	public static void isEmpty(Object[] array, String message) {
		if (array != null && array.length > 0) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证条件为true
	 *
	 * @param condition 条件
	 * @param message 错误消息
	 */
	public static void isTrue(boolean condition, String message) {
		if (!condition) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证条件为false
	 *
	 * @param condition 条件
	 * @param message 错误消息
	 */
	public static void isFalse(boolean condition, String message) {
		if (condition) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证对象相等
	 *
	 * @param obj1 对象1
	 * @param obj2 对象2
	 * @param message 错误消息
	 */
	public static void equals(Object obj1, Object obj2, String message) {
		if (obj1 == null && obj2 == null) {
			return;
		}
		if (obj1 == null || !obj1.equals(obj2)) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证对象不相等
	 *
	 * @param obj1 对象1
	 * @param obj2 对象2
	 * @param message 错误消息
	 */
	public static void notEquals(Object obj1, Object obj2, String message) {
		if (obj1 == null && obj2 == null) {
			throw BusinessException.validationError(message);
		}
		if (obj1 != null && obj1.equals(obj2)) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证字符串匹配正则表达式
	 *
	 * @param str 字符串
	 * @param regex 正则表达式
	 * @param message 错误消息
	 */
	public static void matches(String str, String regex, String message) {
		notBlank(str, message);
		if (!Pattern.matches(regex, str)) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证手机号格式
	 *
	 * @param phone 手机号
	 * @param message 错误消息
	 */
	public static void validatePhone(String phone, String message) {
		matches(phone, PHONE_REGEX, message);
	}

	/**
	 * 验证邮箱格式
	 *
	 * @param email 邮箱
	 * @param message 错误消息
	 */
	public static void validateEmail(String email, String message) {
		matches(email, EMAIL_REGEX, message);
	}

	/**
	 * 验证身份证号格式
	 *
	 * @param idCard 身份证号
	 * @param message 错误消息
	 */
	public static void validateIdCard(String idCard, String message) {
		matches(idCard, ID_CARD_REGEX, message);
	}

	/**
	 * 验证URL格式
	 *
	 * @param url URL
	 * @param message 错误消息
	 */
	public static void validateUrl(String url, String message) {
		matches(url, URL_REGEX, message);
	}

	/**
	 * 验证IP地址格式
	 *
	 * @param ip IP地址
	 * @param message 错误消息
	 */
	public static void validateIp(String ip, String message) {
		matches(ip, IP_REGEX, message);
	}

	/**
	 * 验证字符串长度
	 *
	 * @param str 字符串
	 * @param min 最小长度
	 * @param max 最大长度
	 * @param message 错误消息
	 */
	public static void validateLength(String str, int min, int max, String message) {
		notBlank(str, message);
		int length = str.length();
		if (length < min || length > max) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证字符串最小长度
	 *
	 * @param str 字符串
	 * @param min 最小长度
	 * @param message 错误消息
	 */
	public static void validateMinLength(String str, int min, String message) {
		notBlank(str, message);
		if (str.length() < min) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证字符串最大长度
	 *
	 * @param str 字符串
	 * @param max 最大长度
	 * @param message 错误消息
	 */
	public static void validateMaxLength(String str, int max, String message) {
		notBlank(str, message);
		if (str.length() > max) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证数字范围
	 *
	 * @param number 数字
	 * @param min 最小值
	 * @param max 最大值
	 * @param message 错误消息
	 */
	public static void validateRange(Number number, Number min, Number max, String message) {
		notNull(number, message);
		double value = number.doubleValue();
		double minValue = min.doubleValue();
		double maxValue = max.doubleValue();
		if (value < minValue || value > maxValue) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证数字最小值
	 *
	 * @param number 数字
	 * @param min 最小值
	 * @param message 错误消息
	 */
	public static void validateMin(Number number, Number min, String message) {
		notNull(number, message);
		if (number.doubleValue() < min.doubleValue()) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证数字最大值
	 *
	 * @param number 数字
	 * @param max 最大值
	 * @param message 错误消息
	 */
	public static void validateMax(Number number, Number max, String message) {
		notNull(number, message);
		if (number.doubleValue() > max.doubleValue()) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证集合大小
	 *
	 * @param collection 集合
	 * @param min 最小大小
	 * @param max 最大大小
	 * @param message 错误消息
	 */
	public static void validateSize(Collection<?> collection, int min, int max, String message) {
		notNull(collection, message);
		int size = collection.size();
		if (size < min || size > max) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证集合最小大小
	 *
	 * @param collection 集合
	 * @param min 最小大小
	 * @param message 错误消息
	 */
	public static void validateMinSize(Collection<?> collection, int min, String message) {
		notNull(collection, message);
		if (collection.size() < min) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证集合最大大小
	 *
	 * @param collection 集合
	 * @param max 最大大小
	 * @param message 错误消息
	 */
	public static void validateMaxSize(Collection<?> collection, int max, String message) {
		notNull(collection, message);
		if (collection.size() > max) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证日期范围
	 *
	 * @param date 日期
	 * @param start 开始日期
	 * @param end 结束日期
	 * @param message 错误消息
	 */
	public static void validateDateRange(
			java.time.LocalDate date,
			java.time.LocalDate start,
			java.time.LocalDate end,
			String message) {
		notNull(date, message);
		if (date.isBefore(start) || date.isAfter(end)) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证日期时间范围
	 *
	 * @param dateTime 日期时间
	 * @param start 开始日期时间
	 * @param end 结束日期时间
	 * @param message 错误消息
	 */
	public static void validateDateTimeRange(
			java.time.LocalDateTime dateTime,
			java.time.LocalDateTime start,
			java.time.LocalDateTime end,
			String message) {
		notNull(dateTime, message);
		if (dateTime.isBefore(start) || dateTime.isAfter(end)) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证字符串是否为数字
	 *
	 * @param str 字符串
	 * @param message 错误消息
	 */
	public static void validateNumeric(String str, String message) {
		matches(str, NUMBER_REGEX, message);
	}

	/**
	 * 验证字符串是否为字母
	 *
	 * @param str 字符串
	 * @param message 错误消息
	 */
	public static void validateLetter(String str, String message) {
		matches(str, LETTER_REGEX, message);
	}

	/**
	 * 验证字符串是否为字母数字
	 *
	 * @param str 字符串
	 * @param message 错误消息
	 */
	public static void validateAlphanumeric(String str, String message) {
		matches(str, ALPHANUMERIC_REGEX, message);
	}

	/**
	 * 验证字符串是否为中文
	 *
	 * @param str 字符串
	 * @param message 错误消息
	 */
	public static void validateChinese(String str, String message) {
		matches(str, CHINESE_REGEX, message);
	}

	/**
	 * 验证文件大小
	 *
	 * @param fileSize 文件大小（字节）
	 * @param maxSize 最大文件大小（字节）
	 * @param message 错误消息
	 */
	public static void validateFileSize(long fileSize, long maxSize, String message) {
		if (fileSize > maxSize) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 验证文件扩展名
	 *
	 * @param fileName 文件名
	 * @param allowedExtensions 允许的扩展名
	 * @param message 错误消息
	 */
	public static void validateFileExtension(
			String fileName, String[] allowedExtensions, String message) {
		notBlank(fileName, message);
		String extension = getFileExtension(fileName).toLowerCase();
		boolean allowed = false;
		for (String allowedExtension : allowedExtensions) {
			if (allowedExtension.toLowerCase().equals(extension)) {
				allowed = true;
				break;
			}
		}
		if (!allowed) {
			throw BusinessException.validationError(message);
		}
	}

	/**
	 * 获取文件扩展名
	 *
	 * @param fileName 文件名
	 * @return 文件扩展名
	 */
	private static String getFileExtension(String fileName) {
		int lastDotIndex = fileName.lastIndexOf('.');
		if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
			return "";
		}
		return fileName.substring(lastDotIndex + 1);
	}
}
