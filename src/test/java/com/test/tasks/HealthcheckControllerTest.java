package com.test.tasks;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HealthcheckConfiguration.class})
public class HealthcheckControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@Before
	public void setup() {
//		this.mockMvc = MockMvcBuilders.standaloneSetup(new HealthcheckController()).build();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@Test
	public void test_healthcheck_get_valid() throws Exception {
		this.mockMvc.perform(get("/healthcheck").param("format", "short"))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void test_healthcheck_get_empty_parameter() throws Exception {
		this.mockMvc.perform(get("/healthcheck"))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void test_healthcheck_get_not_valid_parameter() throws Exception {
		this.mockMvc.perform(get("/healthcheck").param("FORMAT", "short"))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void test_healthcheck_post() throws Exception {
		this.mockMvc.perform(post("/healthcheck"))
		.andDo(print())
		.andExpect(status().isMethodNotAllowed());
	}
	
	@Test
	public void test_healthcheck_put() throws Exception {
		this.mockMvc.perform(put("/healthcheck"))
		.andDo(print())
		.andExpect(status().isMethodNotAllowed());
	}
	
	@Test
	public void test_healthcheck_delete() throws Exception {
		this.mockMvc.perform(delete("/healthcheck"))
		.andDo(print())
		.andExpect(status().isMethodNotAllowed());
//		.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
	}
	
	@Test
	public void test_request_notfound() throws Exception {
		this.mockMvc.perform(get("/"))
		.andDo(print())
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void test_healthcheck_delete_response_contenttype_json() throws Exception {
		this.mockMvc.perform(delete("/healthcheck"))
		.andDo(print())
		.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
	}
	
	@Test
	public void test_healthcheck_put_response_contenttype_json() throws Exception {
		this.mockMvc.perform(put("/healthcheck"))
		.andDo(print())
		.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
	}
	
	@Test
	public void test_healthcheck_post_response_contenttype_json() throws Exception {
		this.mockMvc.perform(post("/healthcheck"))
		.andDo(print())
		.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
	}
	
	@Test
	public void test_healthcheck_get_response_contenttype_json() throws Exception {
		this.mockMvc.perform(get("/healthcheck").param("format", "full"))
		.andDo(print())
		.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
	}
}
