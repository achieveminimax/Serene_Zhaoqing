package com.zqtravel.common.redis.cache;

import com.zqtravel.common.redis.config.CommonRedisProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

@RequiredArgsConstructor
public class CacheKeyGenerator {

    private final CommonRedisProperties properties;

    private static final ExpressionParser PARSER = new SpelExpressionParser();

    public String generate(String key, String keyGenerator, Method method, Object[] args) {
        if (StringUtils.isNotBlank(key)) {
            if (isSpelExpression(key)) {
                return buildPrefix() + parseSpel(key, method, args);
            }
            return buildPrefix() + key;
        }
        if (StringUtils.isNotBlank(keyGenerator)) {
            return buildPrefix() + keyGenerator;
        }
        return buildPrefix() + method.getDeclaringClass().getSimpleName() + ":" + method.getName();
    }

    private String buildPrefix() {
        return properties.getKeyPrefix() + ":";
    }

    private boolean isSpelExpression(String key) {
        return key.contains("#") || key.contains("'");
    }

    private String parseSpel(String spel, Method method, Object[] args) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        String[] parameterNames = getParameterNames(method);
        for (int i = 0; i < args.length; i++) {
            String paramName = parameterNames != null && i < parameterNames.length
                    ? parameterNames[i]
                    : "p" + i;
            context.setVariable(paramName, args[i]);
        }
        Expression expression = PARSER.parseExpression(spel);
        Object value = expression.getValue(context);
        return value != null ? value.toString() : "";
    }

    private String[] getParameterNames(Method method) {
        java.lang.reflect.Parameter[] parameters = method.getParameters();
        String[] names = new String[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            names[i] = parameters[i].getName();
        }
        return names;
    }
}
