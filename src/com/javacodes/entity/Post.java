package com.javacodes.entity;

import java.sql.Timestamp;

public class Post {
	
	private int postId;
	private int userId;
	private String content;
	private String hashtags;
	private Timestamp createdAt;
	
	private int likeCount;
	private int commentCount;
	private int shareCount;
	
	public Post() {
	}

	public Post(int postId, int userId, String content, String hashtags, Timestamp createdAt) {
		this.postId = postId;
		this.userId = userId;
		this.content = content;
		this.hashtags = hashtags;
		this.createdAt = createdAt;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHashtags() {
		return hashtags;
	}

	public void setHashtags(String hashtags) {
		this.hashtags = hashtags;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public int getLikeCount() {
	    return likeCount;
	}

	public void setLikeCount(int likeCount) {
	    this.likeCount = likeCount;
	}

	public int getCommentCount() {
	    return commentCount;
	}

	public void setCommentCount(int commentCount) {
	    this.commentCount = commentCount;
	}

	public int getShareCount() {
	    return shareCount;
	}

	public void setShareCount(int shareCount) {
	    this.shareCount = shareCount;
	}
	
	
}
