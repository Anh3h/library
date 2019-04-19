package com.courage.library.unitTest.service.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.courage.library.exception.ConflictException;
import com.courage.library.factory.TopicFactory;
import com.courage.library.model.Topic;
import com.courage.library.repository.TopicRepository;
import com.courage.library.service.command.TopicCommand;
import com.courage.library.service.command.implementation.TopicCommandImplementation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TopicCommandTest {

	@TestConfiguration
	static class TopicCommandTestConfiguration {
		@Bean
		public TopicCommand topicCommand() {
			return new TopicCommandImplementation();
		}
	}

	@Autowired
	private TopicCommand topicCommand;

	@MockBean
	private TopicRepository topicRepository;

	@Test
	public void createTopic_returnsCreatedTopic() {
		Topic topic = TopicFactory.instance();
		given(this.topicRepository.findByName(topic.getName())).willReturn(null);
		given(this.topicRepository.save(any(Topic.class))).willReturn(topic);

		Topic newTopic = this.topicCommand.createTopic(topic);

		assertThat(newTopic).isEqualToComparingFieldByField(topic);
	}

	@Test(expected = ConflictException.class)
	public void createDuplicateTopic_throwsConflictException() {
		Topic topic = TopicFactory.instance();
		given(this.topicRepository.findByName(topic.getName())).willReturn(topic);

		this.topicCommand.createTopic(topic);
	}

}
