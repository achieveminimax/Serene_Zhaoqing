package com.zqtravel.common.core.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** BusinessException单元测试 */
@DisplayName("BusinessException单元测试")
class BusinessExceptionTest {

	@Test
	@DisplayName("测试基本构造函数")
	void testBasicConstructor() {
		String errorCode = "40001";
		String errorMessage = "参数验证失败";

		BusinessException exception = new BusinessException(errorCode, errorMessage);

		assertNotNull(exception);
		assertEquals(errorCode, exception.getCode());
		assertEquals(errorMessage, exception.getMessage());
		assertNull(exception.getData());
		assertEquals(errorMessage, exception.getLocalizedMessage());
	}

	@Test
	@DisplayName("测试带原因的构造函数")
	void testConstructorWithCause() {
		String errorCode = "500";
		String errorMessage = "服务器内部错误";
		Throwable cause = new RuntimeException("底层异常");

		BusinessException exception = new BusinessException(errorCode, errorMessage, cause);

		assertNotNull(exception);
		assertEquals(errorCode, exception.getCode());
		assertEquals(errorMessage, exception.getMessage());
		assertNull(exception.getData());
		assertEquals(cause, exception.getCause());
	}

	@Test
	@DisplayName("测试带数据的构造函数")
	void testConstructorWithData() {
		String errorCode = "40001";
		String errorMessage = "参数验证失败";
		Object errorData = new Object[] {"field", "message"};

		BusinessException exception = new BusinessException(errorCode, errorMessage, errorData);

		assertNotNull(exception);
		assertEquals(errorCode, exception.getCode());
		assertEquals(errorMessage, exception.getMessage());
		assertEquals(errorData, exception.getData());
	}

	@Test
	@DisplayName("测试带数据和原因的构造函数")
	void testConstructorWithDataAndCause() {
		String errorCode = "500";
		String errorMessage = "服务器内部错误";
		Object errorData = new Object[] {"detail", "error"};
		Throwable cause = new RuntimeException("底层异常");

		BusinessException exception =
				new BusinessException(errorCode, errorMessage, errorData, cause);

		assertNotNull(exception);
		assertEquals(errorCode, exception.getCode());
		assertEquals(errorMessage, exception.getMessage());
		assertEquals(errorData, exception.getData());
		assertEquals(cause, exception.getCause());
	}

	@Test
	@DisplayName("测试参数验证异常工厂方法")
	void testValidationError() {
		String errorMessage = "参数验证失败";

		BusinessException exception = BusinessException.validationError(errorMessage);

		assertNotNull(exception);
		assertEquals("40001", exception.getCode());
		assertEquals(errorMessage, exception.getMessage());
		assertNull(exception.getData());
	}

	@Test
	@DisplayName("测试带数据的参数验证异常工厂方法")
	void testValidationErrorWithData() {
		String errorMessage = "参数验证失败";
		Object errorData = new Object[] {"field", "required"};

		BusinessException exception = BusinessException.validationError(errorMessage, errorData);

		assertNotNull(exception);
		assertEquals("40001", exception.getCode());
		assertEquals(errorMessage, exception.getMessage());
		assertEquals(errorData, exception.getData());
	}

	@Test
	@DisplayName("测试业务逻辑异常工厂方法")
	void testBusinessError() {
		String errorMessage = "业务逻辑错误";

		BusinessException exception = BusinessException.businessError(errorMessage);

		assertNotNull(exception);
		assertEquals("40002", exception.getCode());
		assertEquals(errorMessage, exception.getMessage());
		assertNull(exception.getData());
	}

	@Test
	@DisplayName("测试带数据的业务逻辑异常工厂方法")
	void testBusinessErrorWithData() {
		String errorMessage = "业务逻辑错误";
		Object errorData = new Object[] {"rule", "violated"};

		BusinessException exception = BusinessException.businessError(errorMessage, errorData);

		assertNotNull(exception);
		assertEquals("40002", exception.getCode());
		assertEquals(errorMessage, exception.getMessage());
		assertEquals(errorData, exception.getData());
	}

	@Test
	@DisplayName("测试未授权异常工厂方法")
	void testUnauthorizedError() {
		String errorMessage = "未授权访问";

		BusinessException exception = BusinessException.unauthorizedError(errorMessage);

		assertNotNull(exception);
		assertEquals("401", exception.getCode());
		assertEquals(errorMessage, exception.getMessage());
		assertNull(exception.getData());
	}

	@Test
	@DisplayName("测试禁止访问异常工厂方法")
	void testForbiddenError() {
		String errorMessage = "禁止访问";

		BusinessException exception = BusinessException.forbiddenError(errorMessage);

		assertNotNull(exception);
		assertEquals("403", exception.getCode());
		assertEquals(errorMessage, exception.getMessage());
		assertNull(exception.getData());
	}

	@Test
	@DisplayName("测试资源不存在异常工厂方法")
	void testNotFoundError() {
		String errorMessage = "资源不存在";

		BusinessException exception = BusinessException.notFoundError(errorMessage);

		assertNotNull(exception);
		assertEquals("404", exception.getCode());
		assertEquals(errorMessage, exception.getMessage());
		assertNull(exception.getData());
	}

	@Test
	@DisplayName("测试带数据的资源不存在异常工厂方法")
	void testNotFoundErrorWithData() {
		String errorMessage = "资源不存在";
		Object errorData = new Object[] {"resourceId", "123"};

		BusinessException exception = BusinessException.notFoundError(errorMessage, errorData);

		assertNotNull(exception);
		assertEquals("404", exception.getCode());
		assertEquals(errorMessage, exception.getMessage());
		assertEquals(errorData, exception.getData());
	}

	@Test
	@DisplayName("测试服务器内部错误异常工厂方法")
	void testInternalError() {
		String errorMessage = "服务器内部错误";

		BusinessException exception = BusinessException.internalError(errorMessage);

		assertNotNull(exception);
		assertEquals("500", exception.getCode());
		assertEquals(errorMessage, exception.getMessage());
		assertNull(exception.getData());
	}

	@Test
	@DisplayName("测试带原因的服务器内部错误异常工厂方法")
	void testInternalErrorWithCause() {
		String errorMessage = "服务器内部错误";
		Throwable cause = new RuntimeException("底层异常");

		BusinessException exception = BusinessException.internalError(errorMessage, cause);

		assertNotNull(exception);
		assertEquals("500", exception.getCode());
		assertEquals(errorMessage, exception.getMessage());
		assertNull(exception.getData());
		assertEquals(cause, exception.getCause());
	}

	@Test
	@DisplayName("测试数据库操作异常工厂方法")
	void testDatabaseError() {
		String errorMessage = "数据库操作失败";

		BusinessException exception = BusinessException.databaseError(errorMessage);

		assertNotNull(exception);
		assertEquals("50001", exception.getCode());
		assertEquals(errorMessage, exception.getMessage());
		assertNull(exception.getData());
	}

	@Test
	@DisplayName("测试带原因的数据库操作异常工厂方法")
	void testDatabaseErrorWithCause() {
		String errorMessage = "数据库操作失败";
		Throwable cause = new RuntimeException("SQL异常");

		BusinessException exception = BusinessException.databaseError(errorMessage, cause);

		assertNotNull(exception);
		assertEquals("50001", exception.getCode());
		assertEquals(errorMessage, exception.getMessage());
		assertNull(exception.getData());
		assertEquals(cause, exception.getCause());
	}

	@Test
	@DisplayName("测试外部服务调用异常工厂方法")
	void testExternalServiceError() {
		String errorMessage = "外部服务调用失败";

		BusinessException exception = BusinessException.externalServiceError(errorMessage);

		assertNotNull(exception);
		assertEquals("50002", exception.getCode());
		assertEquals(errorMessage, exception.getMessage());
		assertNull(exception.getData());
	}

	@Test
	@DisplayName("测试带原因的外部服务调用异常工厂方法")
	void testExternalServiceErrorWithCause() {
		String errorMessage = "外部服务调用失败";
		Throwable cause = new RuntimeException("网络异常");

		BusinessException exception = BusinessException.externalServiceError(errorMessage, cause);

		assertNotNull(exception);
		assertEquals("50002", exception.getCode());
		assertEquals(errorMessage, exception.getMessage());
		assertNull(exception.getData());
		assertEquals(cause, exception.getCause());
	}

	@Test
	@DisplayName("测试toString方法")
	void testToString() {
		String errorCode = "40001";
		String errorMessage = "参数验证失败";
		Object errorData = new Object[] {"field", "message"};

		BusinessException exception = new BusinessException(errorCode, errorMessage, errorData);
		String toString = exception.toString();

		assertNotNull(toString);
		assertTrue(toString.contains(errorCode));
		assertTrue(toString.contains(errorMessage));
		assertTrue(toString.contains("data="));
	}

	@Test
	@DisplayName("测试异常链")
	void testExceptionChain() {
		Throwable rootCause = new IllegalArgumentException("根原因");
		Throwable middleCause = new RuntimeException("中间原因", rootCause);
		BusinessException exception = new BusinessException("500", "业务异常", middleCause);

		assertNotNull(exception.getCause());
		assertEquals(middleCause, exception.getCause());
		assertEquals(rootCause, exception.getCause().getCause());
	}

	@Test
	@DisplayName("测试序列化ID")
	void testSerialVersionUID() {
		// 验证serialVersionUID存在
		try {
			java.lang.reflect.Field field =
					BusinessException.class.getDeclaredField("serialVersionUID");
			field.setAccessible(true);
			long serialVersionUID = field.getLong(null);
			assertEquals(1L, serialVersionUID);
		} catch (Exception e) {
			fail("serialVersionUID字段不存在或访问失败: " + e.getMessage());
		}
	}

	@Test
	@DisplayName("测试异常继承关系")
	void testInheritance() {
		BusinessException exception = new BusinessException("40001", "测试异常");

		// 应该是RuntimeException的子类
		assertTrue(exception instanceof RuntimeException);

		// 应该是Serializable的实现类
		assertTrue(exception instanceof java.io.Serializable);
	}

	@Test
	@DisplayName("测试异常消息传递")
	void testMessagePropagation() {
		String errorMessage = "测试异常消息";
		BusinessException exception = new BusinessException("40001", errorMessage);

		// getMessage()应该返回错误消息
		assertEquals(errorMessage, exception.getMessage());

		// getLocalizedMessage()默认应该返回相同消息
		assertEquals(errorMessage, exception.getLocalizedMessage());
	}

	@Test
	@DisplayName("测试空数据异常")
	void testNullDataException() {
		String errorCode = "40001";
		String errorMessage = "参数验证失败";

		BusinessException exception = new BusinessException(errorCode, errorMessage, (Object) null);

		assertNotNull(exception);
		assertEquals(errorCode, exception.getCode());
		assertEquals(errorMessage, exception.getMessage());
		assertNull(exception.getData());
	}

	@Test
	@DisplayName("测试复杂数据对象")
	void testComplexDataObject() {
		String errorCode = "40001";
		String errorMessage = "参数验证失败";

		// 创建复杂数据对象
		ComplexData complexData = new ComplexData("测试", 123, new String[] {"a", "b", "c"});
		BusinessException exception = new BusinessException(errorCode, errorMessage, complexData);

		assertNotNull(exception);
		assertEquals(errorCode, exception.getCode());
		assertEquals(errorMessage, exception.getMessage());
		assertNotNull(exception.getData());
		assertTrue(exception.getData() instanceof ComplexData);
		assertEquals(complexData, exception.getData());
	}

	// 测试用的复杂数据类
	static class ComplexData {
		private String name;
		private int value;
		private String[] items;

		public ComplexData(String name, int value, String[] items) {
			this.name = name;
			this.value = value;
			this.items = items;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null || getClass() != obj.getClass()) return false;
			ComplexData that = (ComplexData) obj;
			if (value != that.value) return false;
			if (!name.equals(that.name)) return false;
			return java.util.Arrays.equals(items, that.items);
		}

		@Override
		public int hashCode() {
			int result = name.hashCode();
			result = 31 * result + value;
			result = 31 * result + java.util.Arrays.hashCode(items);
			return result;
		}
	}
}
