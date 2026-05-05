package com.zqtravel.common.core.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** ApiResponse单元测试 */
@DisplayName("ApiResponse单元测试")
class ApiResponseTest {

	@Test
	@DisplayName("测试成功响应（无数据）")
	void testSuccessWithoutData() {
		ApiResponse<Object> response = ApiResponse.success();

		assertNotNull(response);
		assertEquals("200", response.getCode());
		assertEquals("success", response.getMessage());
		assertNull(response.getData());
		assertNotNull(response.getTimestamp());
		assertTrue(response.isSuccess());
		assertFalse(response.isError());
	}

	@Test
	@DisplayName("测试成功响应（有数据）")
	void testSuccessWithData() {
		String testData = "测试数据";
		ApiResponse<String> response = ApiResponse.success(testData);

		assertNotNull(response);
		assertEquals("200", response.getCode());
		assertEquals("success", response.getMessage());
		assertEquals(testData, response.getData());
		assertNotNull(response.getTimestamp());
		assertTrue(response.isSuccess());
	}

	@Test
	@DisplayName("测试成功响应（自定义消息）")
	void testSuccessWithCustomMessage() {
		String testData = "测试数据";
		String customMessage = "操作成功";
		ApiResponse<String> response = ApiResponse.success(customMessage, testData);

		assertNotNull(response);
		assertEquals("200", response.getCode());
		assertEquals(customMessage, response.getMessage());
		assertEquals(testData, response.getData());
		assertNotNull(response.getTimestamp());
	}

	@Test
	@DisplayName("测试错误响应")
	void testError() {
		String errorCode = "40001";
		String errorMessage = "参数验证失败";
		ApiResponse<Object> response = ApiResponse.error(errorCode, errorMessage);

		assertNotNull(response);
		assertEquals(errorCode, response.getCode());
		assertEquals(errorMessage, response.getMessage());
		assertNull(response.getData());
		assertNotNull(response.getTimestamp());
		assertTrue(response.isError());
		assertFalse(response.isSuccess());
	}

	@Test
	@DisplayName("测试错误响应（带数据）")
	void testErrorWithData() {
		String errorCode = "40001";
		String errorMessage = "参数验证失败";
		Object errorData = new Object[] {"field", "message"};
		ApiResponse<Object> response = ApiResponse.error(errorCode, errorMessage, errorData);

		assertNotNull(response);
		assertEquals(errorCode, response.getCode());
		assertEquals(errorMessage, response.getMessage());
		assertEquals(errorData, response.getData());
		assertNotNull(response.getTimestamp());
		assertTrue(response.isError());
	}

	@Test
	@DisplayName("测试参数验证失败响应")
	void testValidationError() {
		String errorMessage = "参数验证失败";
		ApiResponse<Object> response = ApiResponse.validationError(errorMessage);

		assertNotNull(response);
		assertEquals("40001", response.getCode());
		assertEquals(errorMessage, response.getMessage());
		assertNull(response.getData());
		assertTrue(response.isError());
	}

	@Test
	@DisplayName("测试业务异常响应")
	void testBusinessError() {
		String errorMessage = "业务逻辑错误";
		ApiResponse<Object> response = ApiResponse.businessError(errorMessage);

		assertNotNull(response);
		assertEquals("40002", response.getCode());
		assertEquals(errorMessage, response.getMessage());
		assertNull(response.getData());
		assertTrue(response.isError());
	}

	@Test
	@DisplayName("测试未授权响应")
	void testUnauthorizedError() {
		String errorMessage = "未授权访问";
		ApiResponse<Object> response = ApiResponse.unauthorizedError(errorMessage);

		assertNotNull(response);
		assertEquals("401", response.getCode());
		assertEquals(errorMessage, response.getMessage());
		assertNull(response.getData());
		assertTrue(response.isError());
	}

	@Test
	@DisplayName("测试禁止访问响应")
	void testForbiddenError() {
		String errorMessage = "禁止访问";
		ApiResponse<Object> response = ApiResponse.forbiddenError(errorMessage);

		assertNotNull(response);
		assertEquals("403", response.getCode());
		assertEquals(errorMessage, response.getMessage());
		assertNull(response.getData());
		assertTrue(response.isError());
	}

	@Test
	@DisplayName("测试资源不存在响应")
	void testNotFoundError() {
		String errorMessage = "资源不存在";
		ApiResponse<Object> response = ApiResponse.notFoundError(errorMessage);

		assertNotNull(response);
		assertEquals("404", response.getCode());
		assertEquals(errorMessage, response.getMessage());
		assertNull(response.getData());
		assertTrue(response.isError());
	}

	@Test
	@DisplayName("测试服务器内部错误响应")
	void testInternalError() {
		String errorMessage = "服务器内部错误";
		ApiResponse<Object> response = ApiResponse.internalError(errorMessage);

		assertNotNull(response);
		assertEquals("500", response.getCode());
		assertEquals(errorMessage, response.getMessage());
		assertNull(response.getData());
		assertTrue(response.isError());
	}

	@Test
	@DisplayName("测试构建器模式")
	void testBuilder() {
		String code = "200";
		String message = "自定义消息";
		String data = "测试数据";
		Long timestamp = System.currentTimeMillis();

		ApiResponse<String> response =
				ApiResponse.<String>builder()
						.code(code)
						.message(message)
						.data(data)
						.timestamp(timestamp)
						.build();

		assertNotNull(response);
		assertEquals(code, response.getCode());
		assertEquals(message, response.getMessage());
		assertEquals(data, response.getData());
		assertEquals(timestamp, response.getTimestamp());
	}

	@Test
	@DisplayName("测试序列化ID")
	void testSerialVersionUID() {
		// 验证serialVersionUID存在
		try {
			java.lang.reflect.Field field = ApiResponse.class.getDeclaredField("serialVersionUID");
			field.setAccessible(true);
			long serialVersionUID = field.getLong(null);
			assertEquals(1L, serialVersionUID);
		} catch (Exception e) {
			fail("serialVersionUID字段不存在或访问失败: " + e.getMessage());
		}
	}

	@Test
	@DisplayName("测试泛型支持")
	void testGenericSupport() {
		// 测试字符串类型
		ApiResponse<String> stringResponse = ApiResponse.success("字符串数据");
		assertTrue(stringResponse.getData() instanceof String);

		// 测试整数类型
		ApiResponse<Integer> integerResponse = ApiResponse.success(123);
		assertTrue(integerResponse.getData() instanceof Integer);

		// 测试对象类型
		TestData testData = new TestData("test", 123);
		ApiResponse<TestData> objectResponse = ApiResponse.success(testData);
		assertTrue(objectResponse.getData() instanceof TestData);
		assertEquals(testData, objectResponse.getData());
	}

	@Test
	@DisplayName("测试空数据响应")
	void testNullDataResponse() {
		ApiResponse<Object> response = ApiResponse.success(null);

		assertNotNull(response);
		assertEquals("200", response.getCode());
		assertEquals("success", response.getMessage());
		assertNull(response.getData());
		assertNotNull(response.getTimestamp());
	}

	// 测试用的内部类
	static class TestData {
		private String name;
		private int value;

		public TestData(String name, int value) {
			this.name = name;
			this.value = value;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null || getClass() != obj.getClass()) return false;
			TestData testData = (TestData) obj;
			return value == testData.value && name.equals(testData.name);
		}

		@Override
		public int hashCode() {
			return 31 * name.hashCode() + value;
		}
	}
}
