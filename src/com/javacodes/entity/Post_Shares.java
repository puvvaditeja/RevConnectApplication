package com.javacodes.entity;

import java.sql.Timestamp;

public class Post_Shares {
	private int share_id;
	private int post_id;
	private int user_id;
	private Timestamp shared_at;
	

	public Post_Shares() {
	}



	public Post_Shares(int share_id, int post_id, int user_id, Timestamp shared_at) {
		this.share_id = share_id;
		this.post_id = post_id;
		this.user_id = user_id;
		this.shared_at = shared_at;
	}
	
	public int getShare_id() {
		return share_id;
	}



	public void setShare_id(int share_id) {
		this.share_id = share_id;
	}



	public int getPost_id() {
		return post_id;
	}



	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}



	public int getUser_id() {
		return user_id;
	}



	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}



	public Timestamp getShared_at() {
		return shared_at;
	}



	public void setShared_at(Timestamp shared_at) {
		this.shared_at = shared_at;
	}
	
	
}
