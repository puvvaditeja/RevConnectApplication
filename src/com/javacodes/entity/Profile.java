package com.javacodes.entity;

public class Profile {
	
	private int userId;
	private String name;
	private String bio;
	private String proficPicPath;
	private String location;
	private String category;
	private String contact;
	private String website;
	private String businessAddress;
	private String businessHours;
	
	public Profile() {
	}

	public Profile(int userId, String name, String bio, String proficPicPath, String location, String category,
			String contact, String website, String businessAddress, String businessHours) {
		this.userId = userId;
		this.name = name;
		this.bio = bio;
		this.proficPicPath = proficPicPath;
		this.location = location;
		this.category = category;
		this.contact = contact;
		this.website = website;
		this.businessAddress = businessAddress;
		this.businessHours = businessHours;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getProficPicPath() {
		return proficPicPath;
	}

	public void setProficPicPath(String proficPicPath) {
		this.proficPicPath = proficPicPath;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}

	public String getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}
	
	
}
