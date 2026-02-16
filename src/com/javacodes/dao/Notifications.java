package com.javacodes.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Notifications {
	
	String mysqlDriver = "com.mysql.cj.jdbc.Driver";
	String userName = "root";
	String passwd = "root";
	String connectionUrl = "jdbc:mysql://localhost:3306/revConnect";
	
	
	public void viewNotifications(int userId) {

	    String sql =
	        "SELECT notification_id, type, message, created_at, is_read " +
	        "FROM notifications " +
	        "WHERE user_id = ? " +
	        "ORDER BY created_at DESC";

	    try (Connection con = DriverManager.getConnection(connectionUrl, userName, passwd);
	         PreparedStatement pst = con.prepareStatement(sql)) {

	        pst.setInt(1, userId);
	        ResultSet rs = pst.executeQuery();

	        boolean found = false;

	        while (rs.next()) {
	            found = true;
	            System.out.println("-------------------------");
	            System.out.println("Type    : " + rs.getString("type"));
	            System.out.println("Message : " + rs.getString("message"));
	            System.out.println("Time    : " + rs.getTimestamp("created_at"));
	            System.out.println("Read    : " + rs.getBoolean("is_read"));
	        }

	        if (!found) {
	            System.out.println("No notifications.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public boolean updateNotificationStatus(int notificationId, int userId, boolean isRead) {

		 String sql =
			        "UPDATE notifications SET is_read = ? " +
			        "WHERE notification_id = ? AND user_id = ?";

			    try (Connection con = DriverManager.getConnection(connectionUrl, userName, passwd);
			         PreparedStatement pst = con.prepareStatement(sql)) {

			        pst.setBoolean(1, isRead);
			        pst.setInt(2, notificationId);
			        pst.setInt(3, userId);

			        int rows = pst.executeUpdate();
			        return rows > 0;

			    } catch (Exception e) {
			        e.printStackTrace();
			    }
		return false;
	}
}
