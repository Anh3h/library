package com.courage.library;

import java.util.HashMap;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class Authenticate {

	private static TestRestTemplate restTemplate = new TestRestTemplate();
	private static HttpHeaders headers = new HttpHeaders();

	public static String getAccessToken(String baseUrl) {
		if (headers.getContentType() == null) {
			headers.add("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=");
			headers.setContentType(MediaType.APPLICATION_JSON);
		}
		String url = baseUrl + "/oauth/token?grant_type={password}&username={username}&password={password}";
		HttpEntity httpEntity = new HttpEntity<>(null, headers);
		Map<String, String> params = getRequestParam();

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
				String.class, params);

		return JsonPath.parse(response.getBody()).read("access_token").toString();
	}

	private static Map<String, String> getRequestParam() {
		Map<String, String> params = new HashMap<>();
		params.put("grant_type", "password");
		params.put("username", "admin@gmail.com");
		params.put("password", "password");
		return params;
	}
}
