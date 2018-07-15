package com.courage.library.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Topic {

	@Id
	@GeneratedValue
	private Long id;
	@NotNull
	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
	private Set<Book> books;

	public Topic() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
