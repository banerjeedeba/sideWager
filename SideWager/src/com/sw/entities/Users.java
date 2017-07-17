package com.sw.entities;

public class Users {

	private String email;
	private String username;
	private String key;
	private String id;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Users(String email, String username, String key, String id) {
		super();
		this.email = email;
		this.username = username;
		this.key = key;
		this.id = id;
	}
	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
