package com.javacodes.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.javacodes.entity.Profile;
import com.javacodes.entity.User;

public class UserData {
	
	String mysqlDriver = "com.mysql.cj.jdbc.Driver";
	String userName = "root";
	String passwd = "root";
	String connectionUrl = "jdbc:mysql://localhost:3306/revConnect";

	public User authenticate(String email, String password) {
		
		String sqlQuery = "SELECT * FROM user WHERE EMAIL = ? AND PASSWORD = ?";
		
//		boolean result = false;
				
		try {
			
			Class.forName(mysqlDriver);
			
			Connection con = DriverManager.getConnection(connectionUrl, userName, passwd);
			
			PreparedStatement st = con.prepareStatement(sqlQuery);
			
			st.setString(1, email);
	        st.setString(2, password);
			
			ResultSet rs = st.executeQuery();
			
			if (rs.next()) {
				User user = new User();
			    user.setUserId(rs.getInt("user_id"));
			    return user;
			}
			
			con.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
	

	public void addRegisterData(User user, Profile profile) {

	    String userSql =
	        "INSERT INTO user " +
	        "(username,email,password,user_type,security_question,security_answer) " +
	        "VALUES (?,?,?,?,?,?)";

	    String profileSql =
	        "INSERT INTO profile " +
	        "(user_id,name,bio,profilepicpath,location,category," +
	        "contact_info,website,business_address,business_hours) " +
	        "VALUES (?,?,?,?,?,?,?,?,?,?)";

	    try (Connection con =
	             DriverManager.getConnection(connectionUrl, userName, passwd)) {

	        // Insert user & get generated ID
	        PreparedStatement pst =
	            con.prepareStatement(userSql,
	                                 Statement.RETURN_GENERATED_KEYS);

	        pst.setString(1, user.getUserName());
	        pst.setString(2, user.getEmail());
	        pst.setString(3, user.getPassword());
	        pst.setString(4, user.getAccountType());
	        pst.setString(5, user.getSecurityQuestion());
	        pst.setString(6, user.getSecurityAnswer());

	        pst.executeUpdate();

	        ResultSet keys = pst.getGeneratedKeys();

	        if (keys.next()) {
	            user.setUserId(keys.getInt(1));
	        } else {
	            throw new RuntimeException("Failed to get user ID.");
	        }

	        // Insert profile
	        PreparedStatement pst1 =
	            con.prepareStatement(profileSql);

	        pst1.setInt(1, user.getUserId());
	        pst1.setString(2, profile.getName());
	        pst1.setString(3, profile.getBio());
	        pst1.setString(4, profile.getProficPicPath());
	        pst1.setString(5, profile.getLocation());
	        pst1.setString(6, profile.getCategory());
	        pst1.setString(7, profile.getContact());
	        pst1.setString(8, profile.getWebsite());
	        pst1.setString(9, profile.getBusinessAddress());
	        pst1.setString(10, profile.getBusinessHours());

	        pst1.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


	


	public void viewProfile(int userId) {

		String sqlQuery = "SELECT u.username, u.email, u.user_type, " +
		        "p.name, p.bio, p.profilepicpath, p.location, p.category, " +
		        "p.contact_info, p.website, p.business_address, p.business_hours " +
		        "FROM user u JOIN profile p ON u.user_id = p.user_id " +
		        "WHERE u.user_id = ?";
		
		try {
			
			Connection con = DriverManager.getConnection(connectionUrl, userName, passwd);
	        PreparedStatement pst = con.prepareStatement(sqlQuery);
	        
	        pst.setInt(1, userId);
	        		
	        ResultSet rs = pst.executeQuery();
	        
	        if (rs.next()) {
	            System.out.println("\n===== MY PROFILE =====");
	            System.out.println("Username : " + rs.getString("username"));
	            System.out.println("Email    : " + rs.getString("email"));
	            System.out.println("Type     : " + rs.getString("user_type"));
	            System.out.println("Name     : " + rs.getString("name"));
	            System.out.println("Bio      : " + rs.getString("bio"));
	            System.out.println("Location : " + rs.getString("location"));
	            System.out.println("Category : " + rs.getString("category"));
	            System.out.println("Contact  : " + rs.getString("contact_info"));
	            System.out.println("Website  : " + rs.getString("website"));
	            System.out.println("Business Address  : " + rs.getString("business_address"));
	            System.out.println("Business Hours    : " + rs.getString("business_hours"));
	        } else {
	            System.out.println("Profile not found.");
	        }
	        
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}


	public void viewUserProfile(int userId) {

		String sql =
		        "SELECT u.username, u.email, u.user_type, " +
		        "p.name, p.bio, p.profilepicpath, p.location, " +
		        "p.category, p.contact_info, p.website, " +
		        "p.business_address, p.business_hours " +
		        "FROM user u JOIN profile p ON u.user_id = p.user_id " +
		        "WHERE u.user_id = ?";

		    try (
		        Connection con = DriverManager.getConnection(connectionUrl, userName, passwd);
		        PreparedStatement pst = con.prepareStatement(sql)
		    ) {

		        pst.setInt(1, userId);
		        ResultSet rs = pst.executeQuery();

		        if (rs.next()) {
		        	System.out.println("\n===== USER PROFILE =====");
		            System.out.println("Username : " + rs.getString("username"));
		            System.out.println("Email    : " + rs.getString("email"));
		            System.out.println("Type     : " + rs.getString("user_type"));
		            System.out.println("Name     : " + rs.getString("name"));
		            System.out.println("Bio      : " + rs.getString("bio"));
		            System.out.println("Location : " + rs.getString("location"));
		            System.out.println("Category : " + rs.getString("category"));
		            System.out.println("Contact  : " + rs.getString("contact_info"));
		            System.out.println("Website  : " + rs.getString("website"));
		            System.out.println("Business Address  : " + rs.getString("business_address"));
		            System.out.println("Business Hours    : " + rs.getString("business_hours"));
		        } else {
		            System.out.println("User not found.");
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		
	}



	public boolean isEmailExists(String email) {

	    String sql = "SELECT 1 FROM user WHERE email = ?";

	    try (Connection con = DriverManager.getConnection(connectionUrl, userName, passwd);
	         PreparedStatement pst = con.prepareStatement(sql)) {

	        pst.setString(1, email);
	        ResultSet rs = pst.executeQuery();

	        return rs.next(); // true if email exists

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return false;
	}
	
	

	public User getSecurityQuestion(String email) {

	    String sql =
	        "SELECT user_id, security_question, security_answer " +
	        "FROM user WHERE email=?";

	    try (
	        Connection con = DriverManager.getConnection(connectionUrl, userName, passwd);
	        PreparedStatement pst = con.prepareStatement(sql)
	    ) {

	        pst.setString(1, email);
	        ResultSet rs = pst.executeQuery();

	        if (!rs.next()) {
	            throw new IllegalArgumentException("Email not found.");
	        }

	        User user = new User();
	        user.setUserId(rs.getInt("user_id"));
	        user.setSecurityQuestion(rs.getString("security_question"));
	        user.setSecurityAnswer(rs.getString("security_answer"));

	        return user;

	    } catch (Exception e) {
	        throw new RuntimeException("Failed to fetch security question.", e);
	    }
	}
	
	public void resetPassword(int userId, String newPassword) {

	    String oldPassword = getCurrentPassword(userId);

	    if (oldPassword.equals(newPassword)) {
	        throw new IllegalArgumentException(
	            "New password must be different from old password."
	        );
	    }

	    String sql = "UPDATE user SET password=? WHERE user_id=?";

	    try (
	        Connection con = DriverManager.getConnection(connectionUrl, userName, passwd);
	        PreparedStatement pst = con.prepareStatement(sql)
	    ) {

	        pst.setString(1, newPassword);
	        pst.setInt(2, userId);

	        pst.executeUpdate();

	    } catch (Exception e) {
	        throw new RuntimeException("Password reset failed.", e);
	    }
	}
	
	public String getCurrentPassword(int userId) {

	    String sql = "SELECT password FROM user WHERE user_id=?";

	    try (
	        Connection con = DriverManager.getConnection(connectionUrl, userName, passwd);
	        PreparedStatement pst = con.prepareStatement(sql)
	    ) {

	        pst.setInt(1, userId);
	        ResultSet rs = pst.executeQuery();

	        if (!rs.next()) {
	            throw new IllegalArgumentException("User not found.");
	        }

	        return rs.getString("password");

	    } catch (Exception e) {
	        throw new RuntimeException("Failed to fetch password.", e);
	    }
	}

	
}

