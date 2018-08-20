package com.courage.library.model.dto;

public class PasswordDTO {

	private String email;
	private String password;

	public PasswordDTO() {
	}

	public PasswordDTO(String email, String password) {
		this.email = email;
		this.password = password;
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
}
