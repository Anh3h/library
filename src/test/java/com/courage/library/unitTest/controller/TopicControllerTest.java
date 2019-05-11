package com.courage.library.unitTest.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.courage.library.controller.TopicController;
import com.courage.library.factory.TopicFactory;
import com.courage.library.model.Topic;
import com.courage.library.service.command.TopicCommand;
import com.courage.library.service.query.TopicQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TopicController.class, secure = false)
public class TopicControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TopicQuery topicQuery;

	@MockBean
	private TopicCommand topicCommand;

	@Test
	public void getTopicsRequest_returnsHttp200AndAListOfExistingTopics() throws Exception {
		Page<Topic> topics = new PageImpl<>( TopicFactory.instances() );
		given(this.topicQuery.getTopics(1, 20))
				.willReturn(topics);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/topics")
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("content").isArray())
			.andExpect(jsonPath("number").value(0));
	}

	@Test
	public void getTopicsRequestWithValidPageParams_returnsHttp200AndAListOfExistingTopics() throws Exception {
		Page<Topic> topics = new PageImpl<>( TopicFactory.instances() );
		given(this.topicQuery.getTopics(1, 5))
				.willReturn(topics);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/topics?page=1&size=5")
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("content").isArray())
				.andExpect(jsonPath("number").value(0));
	}

	@Test
	public void getTopicsRequestWithInValidPageParams_returnsHttp400() throws Exception {
		Page<Topic> topics = new PageImpl<>( TopicFactory.instances() );

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/topics?page=-1&size=5")
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void getTopic_returnsHttp200AndAnExistingTopic() throws Exception {
		Topic topic = TopicFactory.instance();
		given(this.topicQuery.getTopicById(topic.getId()))
				.willReturn(topic);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/topics/" + topic.getId())
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value(topic.getName()));
	}
}
