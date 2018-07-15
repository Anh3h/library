package com.courage.library.controller;

import javax.websocket.server.PathParam;
import java.util.Map;

import com.courage.library.exception.BadRequestException;
import com.courage.library.model.Topic;
import com.courage.library.service.command.TopicCommand;
import com.courage.library.service.command.TransactionCommand;
import com.courage.library.service.query.TopicQuery;
import com.courage.library.service.query.TransactionQuery;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("api/v1/topics")
public class TopicController {

	@Autowired
	private TopicCommand topicCommand;

	@Autowired
	private TopicQuery topicQuery;

	@ApiOperation("Create a topic")
	@PostMapping(
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Topic> createTopic(@RequestBody Topic topic) {
		Topic newTopic = this.topicCommand.createTopic(topic);
		return new ResponseEntity<>(newTopic, HttpStatus.CREATED);
	}

	@ApiOperation("Get all/some topics")
	@GetMapping(
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Page<Topic>> getTopics(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size) {
		Map<String, Integer> pageAttributes = PageValidator.validatePageAndSize(page, size);
		page = pageAttributes.get("page");
		size = pageAttributes.get("size");
		Page<Topic> topics = this.topicQuery.getTopics(page, size);
		return new ResponseEntity<>(topics, HttpStatus.OK);
	}

	@ApiOperation("Get a topic")
	@GetMapping(
			value = "/{topicId}",
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Topic> getTopic(@PathVariable("topicId") String topicId) {
		Topic topic = this.topicQuery.getTopicById(topicId);
		return new ResponseEntity<>(topic, HttpStatus.OK);
	}

	@ApiOperation("Update a topic")
	@PutMapping(
			value = "/{topicId}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Topic> updateTopic(@RequestBody Topic topic, @PathVariable("topicId") String topicId) {
		if (topic.getId() == topicId) {
			Topic updatedTopic = this.topicCommand.updateTopic(topic);
			return new ResponseEntity<>(updatedTopic, HttpStatus.OK);
		}
		throw BadRequestException.create("Bad Request: Topic id in path parameter does not match that in topic object");
	}

	@ApiOperation("Delete a topic")
	@DeleteMapping(
			value = "/{topicId}"
	)
	public ResponseEntity<HttpStatus> deleteTopic(@PathVariable("topicId") String topicId) {
		this.topicCommand.deleteTopic(topicId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
