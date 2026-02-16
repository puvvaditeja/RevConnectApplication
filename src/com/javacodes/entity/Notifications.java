package com.javacodes.entity;

import java.sql.Timestamp;

public class Notifications {
	private int notificationId;
	private int user_id;
	private String type;
	private String massage;
	private int referenceId;
	private int is_read;
	private Timestamp created_at;
	public Notifications() {
	}
	public Notifications(int notificationId, int user_id, String type, String massage, int referenceId, int is_read,
			Timestamp created_at) {
		this.notificationId = notificationId;
		this.user_id = user_id;
		this.type = type;
		this.massage = massage;
		this.referenceId = referenceId;
		this.is_read = is_read;
		this.created_at = created_at;
	}
	public int getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMassage() {
		return massage;
	}
	public void setMassage(String massage) {
		this.massage = massage;
	}
	public int getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(int referenceId) {
		this.referenceId = referenceId;
	}
	public int getIs_read() {
		return is_read;
	}
	public void setIs_read(int is_read) {
		this.is_read = is_read;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	
	
}
