package com.javacodes.entity;

public class User {
	
	private int userId;
	private String userName;
	private String email;
	private String password;
	private String accountType;
	private String securityQuestion;
	private String securityAnswer;
	
	public User() {
	}

	public User(int userId, String userName, String email, String password, String accountType) {
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.accountType = accountType;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	public String getSecurityQuestion() { return securityQuestion; }
	public void setSecurityQuestion(String q) { this.securityQuestion = q; }

	public String getSecurityAnswer() { return securityAnswer; }
	public void setSecurityAnswer(String a) { this.securityAnswer = a; }
	
	 
}
