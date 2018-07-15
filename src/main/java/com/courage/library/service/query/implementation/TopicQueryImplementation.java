package com.courage.library.service.query.implementation;

import com.courage.library.exception.NotFoundException;
import com.courage.library.model.Topic;
import com.courage.library.repository.TopicRepository;
import com.courage.library.service.query.TopicQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TopicQueryImplementation implements TopicQuery {

	@Autowired
	private TopicRepository topicRepository;

	@Override
	public Topic getTopicById(Long id) {
		Topic topic = this.topicRepository.getOne(id);
		if (topic == null) {
			throw NotFoundException.create("Not Found: Topic does not exist");
		}
		return topic;
	}

	@Override
	public Page<Topic> getTopics(Integer pageNumber, Integer pageSize) {
		return this.topicRepository.findAll(PageRequest.of(pageNumber-1, pageSize));
	}
}
