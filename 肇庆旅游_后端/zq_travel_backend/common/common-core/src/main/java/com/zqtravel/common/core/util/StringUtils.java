package com.zqtravel.common.core.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils as ApacheStringUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字符串工具类
 * 基于Apache Commons Lang3的StringUtils，提供项目特定的字符串处理方法
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-05
 */
@UtilityClass
public class StringUtils {

    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return 是否为空
     */
    public static boolean isEmpty(CharSequence str) {
        return ApacheStringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str 字符串
     * @return 是否不为空
     */
    public static boolean isNotEmpty(CharSequence str) {
        return ApacheStringUtils.isNotEmpty(str);
    }

    /**
     * 判断字符串是否为空白（空或仅包含空白字符）
     *
     * @param str 字符串
     * @return 是否为空白
     */
    public static boolean isBlank(CharSequence str) {
        return ApacheStringUtils.isBlank(str);
    }

    /**
     * 判断字符串是否不为空白
     *
     * @param str 字符串
     * @return 是否不为空白
     */
    public static boolean isNotBlank(CharSequence str) {
        return ApacheStringUtils.isNotBlank(str);
    }

    /**
     * 去除字符串两端的空白字符
     *
     * @param str 字符串
     * @return 去除空白后的字符串
     */
    public static String trim(String str) {
        return ApacheStringUtils.trim(str);
    }

    /**
     * 去除字符串两端的空白字符，如果为null则返回null
     *
     * @param str 字符串
     * @return 去除空白后的字符串
     */
    public static String trimToNull(String str) {
        return ApacheStringUtils.trimToNull(str);
    }

    /**
     * 去除字符串两端的空白字符，如果为null则返回空字符串
     *
     * @param str 字符串
     * @return 去除空白后的字符串
     */
    public static String trimToEmpty(String str) {
        return ApacheStringUtils.trimToEmpty(str);
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始位置
     * @return 截取后的字符串
     */
    public static String substring(String str, int start) {
        return ApacheStringUtils.substring(str, start);
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始位置
     * @param end   结束位置
     * @return 截取后的字符串
     */
    public static String substring(String str, int start, int end) {
        return ApacheStringUtils.substring(str, start, end);
    }

    /**
     * 判断字符串是否以指定前缀开头
     *
     * @param str    字符串
     * @param prefix 前缀
     * @return 是否以指定前缀开头
     */
    public static boolean startsWith(String str, String prefix) {
        return ApacheStringUtils.startsWith(str, prefix);
    }

    /**
     * 判断字符串是否以指定后缀结尾
     *
     * @param str    字符串
     * @param suffix 后缀
     * @return 是否以指定后缀结尾
     */
    public static boolean endsWith(String str, String suffix) {
        return ApacheStringUtils.endsWith(str, suffix);
    }

    /**
     * 连接字符串数组
     *
     * @param elements 字符串数组
     * @return 连接后的字符串
     */
    public static String join(Object[] elements) {
        return ApacheStringUtils.join(elements);
    }

    /**
     * 使用分隔符连接字符串数组
     *
     * @param separator 分隔符
     * @param elements  字符串数组
     * @return 连接后的字符串
     */
    public static String join(CharSequence separator, Object[] elements) {
        return ApacheStringUtils.join(separator, elements);
    }

    /**
     * 使用分隔符连接集合
     *
     * @param separator 分隔符
     * @param iterable  集合
     * @return 连接后的字符串
     */
    public static String join(CharSequence separator, Iterable<?> iterable) {
        return ApacheStringUtils.join(separator, iterable);
    }

    /**
     * 分割字符串
     *
     * @param str       字符串
     * @param separator 分隔符
     * @return 分割后的字符串数组
     */
    public static String[] split(String str, String separator) {
        return ApacheStringUtils.split(str, separator);
    }

    /**
     * 分割字符串并去除空白
     *
     * @param str       字符串
     * @param separator 分隔符
     * @return 分割后的字符串数组
     */
    public static String[] splitAndTrim(String str, String separator) {
        if (isBlank(str)) {
            return new String[0];
        }
        String[] parts = split(str, separator);
        for (int i = 0; i < parts.length; i++) {
            parts[i] = trim(parts[i]);
        }
        return parts;
    }

    /**
     * 重复字符串
     *
     * @param str    字符串
     * @param repeat 重复次数
     * @return 重复后的字符串
     */
    public static String repeat(String str, int repeat) {
        return ApacheStringUtils.repeat(str, repeat);
    }

    /**
     * 字符串替换
     *
     * @param text         文本
     * @param searchString 搜索字符串
     * @param replacement  替换字符串
     * @return 替换后的字符串
     */
    public static String replace(String text, String searchString, String replacement) {
        return ApacheStringUtils.replace(text, searchString, replacement);
    }

    /**
     * 字符串替换（全部）
     *
     * @param text         文本
     * @param searchString 搜索字符串
     * @param replacement  替换字符串
     * @return 替换后的字符串
     */
    public static String replaceAll(String text, String searchString, String replacement) {
        return ApacheStringUtils.replace(text, searchString, replacement);
    }

    /**
     * 转换为小写
     *
     * @param str 字符串
     * @return 小写字符串
     */
    public static String lowerCase(String str) {
        return ApacheStringUtils.lowerCase(str);
    }

    /**
     * 转换为大写
     *
     * @param str 字符串
     * @return 大写字符串
     */
    public static String upperCase(String str) {
        return ApacheStringUtils.upperCase(str);
    }

    /**
     * 首字母大写
     *
     * @param str 字符串
     * @return 首字母大写的字符串
     */
    public static String capitalize(String str) {
        return ApacheStringUtils.capitalize(str);
    }

    /**
     * 首字母小写
     *
     * @param str 字符串
     * @return 首字母小写的字符串
     */
    public static String uncapitalize(String str) {
        return ApacheStringUtils.uncapitalize(str);
    }

    /**
     * 驼峰命名法转下划线命名法
     *
     * @param camelCase 驼峰命名字符串
     * @return 下划线命名字符串
     */
    public static String camelToUnderline(String camelCase) {
        if (isBlank(camelCase)) {
            return camelCase;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < camelCase.length(); i++) {
            char c = camelCase.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                result.append('_');
            }
            result.append(Character.toLowerCase(c));
        }
        return result.toString();
    }

    /**
     * 下划线命名法转驼峰命名法
     *
     * @param underline 下划线命名字符串
     * @return 驼峰命名字符串
     */
    public static String underlineToCamel(String underline) {
        if (isBlank(underline)) {
            return underline;
        }
        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;
        for (int i = 0; i < underline.length(); i++) {
            char c = underline.charAt(i);
            if (c == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    result.append(Character.toUpperCase(c));
                    nextUpper = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }
        return result.toString();
    }

    /**
     * 截断字符串，添加省略号
     *
     * @param str    字符串
     * @param length 最大长度
     * @return 截断后的字符串
     */
    public static String truncateWithEllipsis(String str, int length) {
        if (isBlank(str) || str.length() <= length) {
            return str;
        }
        if (length <= 3) {
            return substring(str, 0, length);
        }
        return substring(str, 0, length - 3) + "...";
    }

    /**
     * 隐藏手机号中间四位
     *
     * @param phone 手机号
     * @return 隐藏后的手机号
     */
    public static String maskPhone(String phone) {
        if (isBlank(phone) || phone.length() < 7) {
            return phone;
        }
        return phone.substring(0, 3) + "****" + phone.substring(7);
    }

    /**
     * 隐藏邮箱
     *
     * @param email 邮箱
     * @return 隐藏后的邮箱
     */
    public static String maskEmail(String email) {
        if (isBlank(email)) {
            return email;
        }
        int atIndex = email.indexOf('@');
        if (atIndex <= 1) {
            return email;
        }
        String prefix = email.substring(0, atIndex);
        String domain = email.substring(atIndex);
        
        if (prefix.length() <= 2) {
            return prefix.charAt(0) + "***" + domain;
        } else {
            return prefix.charAt(0) + "***" + prefix.charAt(prefix.length() - 1) + domain;
        }
    }

    /**
     * 过滤集合中的空字符串
     *
     * @param strings 字符串集合
     * @return 过滤后的集合
     */
    public static List<String> filterBlank(Collection<String> strings) {
        if (strings == null) {
            return List.of();
        }
        return strings.stream()
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str 字符串
     * @return 是否为数字
     */
    public static boolean isNumeric(String str) {
        return ApacheStringUtils.isNumeric(str);
    }

    /**
     * 判断字符串是否为字母
     *
     * @param str 字符串
     * @return 是否为字母
     */
    public static boolean isAlpha(String str) {
        return ApacheStringUtils.isAlpha(str);
    }

    /**
     * 判断字符串是否为字母或数字
     *
     * @param str 字符串
     * @return 是否为字母或数字
     */
    public static boolean isAlphanumeric(String str) {
        return ApacheStringUtils.isAlphanumeric(str);
    }

    /**
     * 去除HTML标签
     *
     * @param html HTML字符串
     * @return 去除HTML标签后的字符串
     */
    public static String stripHtml(String html) {
        if (isBlank(html)) {
            return html;
        }
        return html.replaceAll("<[^>]*>", "");
    }

    /**
     * 生成指定长度的随机字符串
     *
     * @param length 长度
     * @return 随机字符串
     */
    public static String randomString(int length) {
        if (length <= 0) {
            return "";
        }
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            result.append(chars.charAt(index));
        }
        return result.toString();
    }
}