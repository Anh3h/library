package com.courage.library.factory;

import java.util.UUID;

import com.courage.library.model.Topic;
import org.apache.commons.lang3.RandomStringUtils;

public class TopicFactory {

	public static Topic instance() {
		String id = UUID.randomUUID().toString();
		String name = RandomStringUtils.random(10, true, false);
		return new Topic(id, name);
	}
}
