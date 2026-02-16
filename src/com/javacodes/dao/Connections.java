package com.javacodes.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.javacodes.entity.User;

public class Connections {
	
	String mysqlDriver = "com.mysql.cj.jdbc.Driver";
	String userName = "root";
	String passwd = "root";
	String connectionUrl = "jdbc:mysql://localhost:3306/revConnect";
	
	public void createNotification(int userId, String type, String message, int referenceId) {

	    String sql =
	        "INSERT INTO notifications (user_id, type, message, reference_id) " +
	        "VALUES (?, ?, ?, ?)";

	    try (Connection con = DriverManager.getConnection(connectionUrl, userName, passwd);
	         PreparedStatement pst = con.prepareStatement(sql)) {

	        pst.setInt(1, userId);
	        pst.setString(2, type);
	        pst.setString(3, message);
	        pst.setInt(4, referenceId);

	        pst.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	public boolean sendConnectionRequest(int requestId, int userId, int receiverId) {

		String sql =
		        "INSERT INTO connection_requests (request_id, sender_id, receiver_id, status) " +
		        "VALUES (?, ?, ?, 'PENDING')";

		    try (Connection con = DriverManager.getConnection(connectionUrl, userName, passwd);
		         PreparedStatement pst = con.prepareStatement(sql)) {

		        pst.setInt(1, requestId);
		        pst.setInt(2, userId);
		        pst.setInt(3, receiverId);

		        pst.executeUpdate();
		        
				createNotification(receiverId,"CONNECTION_REQUEST","User " + userId + " sent you a connection request",requestId);

		        return true;

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		return false;
		
	}


	public void viewPendingRequests(int userId) {

		String sql =
		        "SELECT request_id, sender_id, requested_at " +
		        "FROM connection_requests " +
		        "WHERE receiver_id = ? AND status = 'PENDING'";

		    try (Connection con = DriverManager.getConnection(connectionUrl, userName, passwd);
		         PreparedStatement pst = con.prepareStatement(sql)) {

		        pst.setInt(1, userId);
		        ResultSet rs = pst.executeQuery();

		        boolean found = false;

		        while (rs.next()) {
		            found = true;
		            System.out.println("-------------------------");
		            System.out.println("Request ID : " + rs.getInt("request_id"));
		            System.out.println("From User  : " + rs.getInt("sender_id"));
		            System.out.println("Requested : " + rs.getTimestamp("requested_at"));
		        }

		        if (!found) {
		            System.out.println("No pending connection requests.");
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		
	}


	public boolean acceptFollowRequest(int requestId, int loggedInUserId) {

	    String getRequestSql =
	        "SELECT sender_id, receiver_id FROM connection_requests " +
	        "WHERE request_id = ? AND receiver_id = ? AND status = 'PENDING'";

	    String updateRequestSql =
	        "UPDATE connection_requests SET status = 'ACCEPTED' WHERE request_id = ?";

	    String insertFollowSql =
	        "INSERT INTO followers (follower_id, following_id) VALUES (?, ?)";

	    try (Connection con = DriverManager.getConnection(connectionUrl, userName, passwd)) {

	        PreparedStatement pst1 = con.prepareStatement(getRequestSql);
	        pst1.setInt(1, requestId);
	        pst1.setInt(2, loggedInUserId);

	        ResultSet rs = pst1.executeQuery();
	        if (!rs.next()) {
	            return false;
	        }

	        int followerId = rs.getInt("sender_id");   // sender follows
	        int followingId = rs.getInt("receiver_id"); // receiver is followed

	        PreparedStatement pst2 = con.prepareStatement(updateRequestSql);
	        pst2.setInt(1, requestId);
	        pst2.executeUpdate();

	        PreparedStatement pst3 = con.prepareStatement(insertFollowSql);
	        pst3.setInt(1, followerId);
	        pst3.setInt(2, followingId);
	        pst3.executeUpdate();
	        
	        createNotification(followerId, "REQUEST_ACCEPTED","Your connection request was accepted",requestId);

	        return true;
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return false;
	}
	
	public boolean rejectFollowRequest(int requestId, int userId) {
		 
		String sql =
		        "UPDATE connection_requests " +
		        "SET status = 'REJECTED' " +
		        "WHERE request_id = ? AND receiver_id = ? AND status = 'PENDING'";

		    try (Connection con = DriverManager.getConnection(connectionUrl, userName, passwd);
		         PreparedStatement pst = con.prepareStatement(sql)) {

		        pst.setInt(1, requestId);
		        pst.setInt(2, userId);

		        int rows = pst.executeUpdate();
		        return rows > 0;   // true only if a row was updated

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		return false;
	}
	
	public ArrayList<User> searchUsers(
	        String keyword,
	        int excludeUserId) {

	    String sql =
	        "SELECT user_id, username, email " +
	        "FROM user " +
	        "WHERE (username LIKE ? OR email LIKE ?) " +
	        "AND user_id != ?";

	    ArrayList<User> list = new ArrayList<>();

	    try (Connection con =
	             DriverManager.getConnection(connectionUrl, userName, passwd);
	         PreparedStatement pst =
	             con.prepareStatement(sql)) {

	        String pattern = "%" + keyword + "%";

	        pst.setString(1, pattern);
	        pst.setString(2, pattern);
	        pst.setInt(3, excludeUserId);

	        ResultSet rs = pst.executeQuery();

	        while (rs.next()) {

	            User u = new User();
	            u.setUserId(rs.getInt("user_id"));
	            u.setUserName(rs.getString("username"));
	            u.setEmail(rs.getString("email"));

	            list.add(u);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}
}
