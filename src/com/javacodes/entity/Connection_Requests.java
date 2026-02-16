package com.javacodes.entity;

import java.sql.Timestamp;

public class Connection_Requests {
	private int requestId;
	private int senderId;
	private int receiverId;
	private String status;
	private Timestamp requestedAt;
	
	public Connection_Requests() {
	}
	
	public Connection_Requests(int requestId, int senderId, int receiverId, String status, Timestamp requestedAt) {
		this.requestId = requestId;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.status = status;
		this.requestedAt = requestedAt;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	public int getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getRequestedAt() {
		return requestedAt;
	}
	public void setRequestedAt(Timestamp requestedAt) {
		this.requestedAt = requestedAt;
	}
	
	
}
