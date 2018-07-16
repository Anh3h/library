package com.courage.library.model.dto;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.courage.library.model.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDTO {

	private String id;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private String username;
	@NotNull
	private String email;
	@NotNull
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	@NotNull
	private Date dob;
	@NotNull
	private String telephone;
	@NotNull
	private String roleId;
	private Set<String> favoriteBookIds;

	public UserDTO() {
	}

	public UserDTO(String id, @NotNull String firstName, @NotNull String lastName,
			@NotNull String username, @NotNull String email, @NotNull String password,
			@NotNull Date dob, @NotNull String telephone, @NotNull String roleId) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.dob = dob;
		this.telephone = telephone;
		this.roleId = roleId;
		this.favoriteBookIds = new HashSet<>();
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

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Set<String> getFavoriteBookIds() {
		return favoriteBookIds;
	}

	public void setFavoriteBookIds(Set<String> favoriteBookIds) {
		this.favoriteBookIds = favoriteBookIds;
	}

	public void addFavoriteBook(String bookId) {
		this.favoriteBookIds.add(bookId);
	}
}
