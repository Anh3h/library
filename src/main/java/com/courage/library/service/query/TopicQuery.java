package com.courage.library.service.query;

import com.courage.library.model.Topic;
import org.springframework.data.domain.Page;

public interface TopicQuery {

	Topic getTopicById(Long id);
	Page<Topic> getTopics(Integer pageNumber, Integer pageSize);
}
