package com.courage.library.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.courage.library.model.Topic;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;

public class TopicFactory {

	public static Topic instance() {
		String id = UUID.randomUUID().toString();
		String name = RandomStringUtils.random(10, true, false);
		return new Topic(id, name);
	}

	public static List<Topic> instances() {
		List<Topic> topics = new ArrayList<>();
		topics.add(instance());
		topics.add(instance());
		topics.add(instance());
		return topics;
	}
}
