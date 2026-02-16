package com.javacodes.ui;

import java.util.ArrayList;
import java.util.Scanner;

import com.javacodes.dao.Connections;
import com.javacodes.entity.User;

public class ConnectionsUI {
	
	Scanner sc = new Scanner(System.in);
	
	private User user;
	
	public ConnectionsUI(User user) {
        this.user = user;
    }

	public void Start() {
		
		
		System.out.println("\n===== NETWORK MENU =====");
	    System.out.println("1. Send Connection Request");
	    System.out.println("2. View Pending Requests");
	    System.out.println("3. Accept Requests");
	    System.out.println("4. Reject Request");
	    System.out.println("5. UnFollow User");
	    System.out.println("0. Back");
	    
	    int choice = -1;

		while (true) {
		    try {
		        System.out.print("Choose Option: ");

		        choice = Integer.parseInt(sc.nextLine());

		        break;

		    } catch (NumberFormatException e) {
		        System.out.println("Invalid input. Please enter a number.");
		    }
		}
	    
	    	    
	    switch (choice) {
	    	case 1 :
	    		sendRequest();
	    		break;
	    	case 2 : 
	    		viewRequest();
	    		break;
	    	case 3 :
	    		acceptRequest();
	    		break;
	    	case 4 : 
	    		rejectRequest();
	    		return;
	    	default : 
	    		System.out.println("Invalid Option");
	    		break;
	    }
	    
	    
	}

	private void rejectRequest() {

		System.out.print("Enter Request ID to reject: ");
	    int requestId = sc.nextInt();

	    Connections connection = new Connections();
	    boolean success =
	    		connection.rejectFollowRequest(requestId, user.getUserId());

	    if (success) {
	        System.out.println("Connection request rejected.");
	    } else {
	        System.out.println("Invalid request or already handled.");
	    }
		
	}

	private void acceptRequest() {
		
		 System.out.print("Enter Request ID to accept: ");
		    int requestId = sc.nextInt();

		    Connections connection = new Connections();
		    boolean success =
		    		connection.acceptFollowRequest(requestId, user.getUserId());

		    if (success) {
		        System.out.println("Follow  request accepted!");
		    } else {
		        System.out.println("Invalid request or already handled.");
		    }
		
		    System.out.println("\nPress ENTER to accept request and go back...");
		    sc.nextLine();
	}

	private void sendRequest() {

		Connections connection = new Connections();

	    System.out.print("\nSearch user: ");
	    String keyword = sc.nextLine();

	    ArrayList<User> results =
	    		connection.searchUsers(keyword, user.getUserId());

	    if (results.isEmpty()) {
	        System.out.println("No users found.");
	        return;
	    }

	    System.out.println("\nSearch results:");

	    for (int i = 0; i < results.size(); i++) {

	        User u = results.get(i);

	        System.out.println(
	            (i + 1) + ". " +
	            u.getUserName() +
	            " (" + u.getEmail() + ")"
	        );
	    }

	    System.out.print("\nSelect user number to connect: ");
	    int choice = sc.nextInt();
	    sc.nextLine();

	    if (choice < 1 || choice > results.size()) {
	        System.out.println("Invalid selection.");
	        return;
	    }

	    User selected = results.get(choice - 1);

	    int requestId =
	        (int)(System.currentTimeMillis() % 1000000);

	    boolean success =
	    		connection.sendConnectionRequest(
	            requestId,
	            user.getUserId(),
	            selected.getUserId()
	        );

	    if (success) {
	        System.out.println("Connection request sent!");
	    } else {
	        System.out.println("Failed to send request.");
	    }

	    System.out.println("\nPress ENTER to go back...");
	    sc.nextLine();
	}
	

	private void viewRequest() {
		
		System.out.println("\n===== PENDING CONNECTION REQUESTS =====");
		
		Connections connection = new Connections();
		connection.viewPendingRequests(user.getUserId());
	    
	    System.out.println("\nPress ENTER to view request and go back...");
	    sc.nextLine();
		
	}
	
	
		
}
	   
