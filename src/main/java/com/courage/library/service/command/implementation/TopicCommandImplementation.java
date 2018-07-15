package com.courage.library.service.command.implementation;

import com.courage.library.exception.ConflictException;
import com.courage.library.exception.NotFoundException;
import com.courage.library.model.Topic;
import com.courage.library.repository.TopicRepository;
import com.courage.library.service.command.TopicCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TopicCommandImplementation implements TopicCommand {

	@Autowired
	private TopicRepository topicRepository;

	@Override
	public Topic createTopic(Topic topic) {
		if (this.topicRepository.findByName(topic.getName()) == null) {
			topic.setId(null);
			return this.topicRepository.save(topic);
		}
		throw ConflictException.create("Conflict: Topic name already exist");
	}

	@Override
	public Topic updateTopic(Topic topic) {
		if (this.topicRepository.existsById(topic.getId())) {
			return this.topicRepository.save(topic);
		}
		throw NotFoundException.create("Not Found: Topic does not exist");
	}

	@Override
	public void deleteTopic(String topicId) {
		this.topicRepository.deleteById(topicId);
	}
}
