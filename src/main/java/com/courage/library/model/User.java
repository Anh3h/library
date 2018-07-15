package com.courage.library.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {

	@Id
	private String id;
	@NotNull
	@Column(nullable = false)
	private String firstName;
	@NotNull
	@Column(nullable = false)
	private String lastName;
	private String username;
	@NotNull
	@Column(nullable = false)
	private String email;
	@NotNull
	@Column(nullable = false)
	@JsonIgnore
	private String password;
	private Date dob;
	private String telephone;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "favorite_books", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"))
	private Set<Book> favoriteBooks;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Comment> comments;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Transaction> transactions;

	public User() {
	}

	public User(@NotNull String firstName, @NotNull String lastName,
			@NotNull String username, @NotNull String email, @NotNull String password,
			@NotNull Date dob, @NotNull String telephone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.dob = dob;
		this.telephone = telephone;
		this.role = new Role();
		this.favoriteBooks = new HashSet<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Set<Book> getFavoriteBooks() {
		return favoriteBooks;
	}

	public void setFavoriteBooks(Set<Book> favoriteBooks) {
		this.favoriteBooks = favoriteBooks;
	}
}
