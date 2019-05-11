package com.courage.library.unitTest.service.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.courage.library.exception.NotFoundException;
import com.courage.library.factory.TopicFactory;
import com.courage.library.model.Topic;
import com.courage.library.repository.TopicRepository;
import com.courage.library.service.query.TopicQuery;
import com.courage.library.service.query.implementation.TopicQueryImplementation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TopicQueryTest {

	@TestConfiguration
	static class TopicQueryTestConfiguration {
		@Bean
		public TopicQuery topicQuery() {
			return new TopicQueryImplementation();
		}
	}

	@Autowired
	private TopicQuery topicQuery;

	@MockBean
	private TopicRepository topicRepository;

	@Test
	public void getTopics_returnsAPageOfTopic() {
		Page<Topic> pagedTopics = new PageImpl<>(TopicFactory.instances());
		given(this.topicRepository.findAll(PageRequest.of(0, 2)))
			.willReturn(pagedTopics);

		Page<Topic> returnedTopics = this.topicQuery.getTopics(1, 2);

		assertThat(returnedTopics.getContent()).isEqualTo(pagedTopics.getContent());
	}

	@Test
	public void getTopic_returnsAnExistingTopic() {
		Topic topic = TopicFactory.instance();
		given(this.topicRepository.getOne(topic.getId())).willReturn(topic);

		Topic gottenTopic = this.topicQuery.getTopicById(topic.getId());

		assertThat(gottenTopic).isEqualTo(topic);
	}

	@Test(expected = NotFoundException.class)
	public void getNonExistingTopic_throwsNotFoundException() {
		String topicId = UUID.randomUUID().toString();
		given(this.topicRepository.getOne(topicId)).willReturn(null);

		this.topicQuery.getTopicById(topicId);
	}

}
