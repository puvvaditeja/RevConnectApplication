package com.javacodes.entity;

import java.sql.Timestamp;

public class Post_Likes {
	private int like_id;
	private int post_id;
	private int user_id;
	private Timestamp liked_at;
	public Post_Likes() {
	}
	public Post_Likes(int like_id, int post_id, int user_id, Timestamp liked_at) {
		this.like_id = like_id;
		this.post_id = post_id;
		this.user_id = user_id;
		this.liked_at = liked_at;
	}
	public int getLike_id() {
		return like_id;
	}
	public void setLike_id(int like_id) {
		this.like_id = like_id;
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
	public Timestamp getLiked_at() {
		return liked_at;
	}
	public void setLiked_at(Timestamp liked_at) {
		this.liked_at = liked_at;
	}
	
	
}
