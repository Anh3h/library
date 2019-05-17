package com.courage.library.unitTest.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.courage.library.controller.HealthCheckController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = HealthCheckController.class, secure = false)
public class HealthCheckControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void healthCheckTestRequest_returnsHttp200() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/healthCheck"))
			.andExpect(status().isOk());
	}

}
