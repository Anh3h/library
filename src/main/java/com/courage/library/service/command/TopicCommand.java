package com.courage.library.service.command;

import com.courage.library.model.Topic;

public interface TopicCommand {

	Topic createTopic(Topic topic);
	Topic updateTopic(Topic topic);
	void deleteTopic(String topicId);
}
