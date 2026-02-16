package com.javacodes.ui;

import java.util.ArrayList;
import java.util.Scanner;

import com.javacodes.dao.Connections;
import com.javacodes.dao.Notifications;
import com.javacodes.dao.UserData;
import com.javacodes.entity.User;

public class UserMenuUI {
	
	Scanner sc = new Scanner(System.in);
		
	private User loggedInUser;
	private PostUi postui;
	
	public UserMenuUI(User user) {
	    this.loggedInUser = user;
	    this.postui = new PostUi(user);  
	}


	public void start() {
		
		while(true) {
		
		System.out.println("===== USER MENU =====");
		System.out.println("1. Create Post");
        System.out.println("2. View My Posts");
        System.out.println("3. View Feed");
        System.out.println("4. Connections & Follows");
        System.out.println("5. View My Profile");
        System.out.println("6. View User Profile");
        System.out.println("7. View Notifications");
        System.out.println("0. Logout");
        
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
        case 1: 
        	postui.createPost();
        	break;
        case 2:
        	postui.viewPost();
        	break;
        case 3:
        	postui.viewFeed();
        	break;
        case 4:
        	ConnectionsUI connectionui = new ConnectionsUI(loggedInUser);
        	connectionui.Start();
        	break;
        case 5:
        	UserData userdata = new UserData();
        	userdata.viewProfile(loggedInUser.getUserId());
        	break;
        case 6:
        	viewUserProfile();
        	break;
        case 7:
            System.out.println("\n===== NOTIFICATIONS =====");
            System.out.println("1. View Notifications");
            System.out.println("2. Mark Notification as READ");
            System.out.println("3. Mark Notification as UNREAD");
            int notifyChoice = -1;

			while (true) {
			    try {
			        System.out.print("Choose Option: ");

			        choice = Integer.parseInt(sc.nextLine());

			        break;

			    } catch (NumberFormatException e) {
			        System.out.println("Invalid input. Please enter a number.");
			    }
			}
            
            Notifications notification = new Notifications();

            switch (notifyChoice) {

                case 1:
                    // View all notifications
                	notification.viewNotifications(loggedInUser.getUserId());
                    break;

                case 2:
                    // Mark as READ
                    System.out.print("Enter Notification ID to mark as READ: ");
                    int readId = sc.nextInt();
                    sc.nextLine();

                    if (notification.updateNotificationStatus(readId, loggedInUser.getUserId(), true)) {
                        System.out.println("Notification marked as READ.");
                    } else {
                        System.out.println("Invalid notification ID.");
                    }
                    break;

                case 3:
                    // Mark as UNREAD
                    System.out.print("Enter Notification ID to mark as UNREAD: ");
                    int unreadId = sc.nextInt();
                    sc.nextLine();

                    if (notification.updateNotificationStatus(unreadId, loggedInUser.getUserId(), false)) {
                        System.out.println("Notification marked as UNREAD.");
                    } else {
                        System.out.println("Invalid notification ID.");
                    }
                    break;

                default:
                    System.out.println("Invalid option.");
            }
            break;
        case 0:
        	System.out.println("Logged out...");
            return;  
        default :
        	System.out.println("Invalid Option");
        }
        
	}
	}


	private void viewUserProfile() {

	    UserData userData = new UserData();
	    Connections connection = new Connections();

	    System.out.print("Search user: ");
	    String keyword = sc.nextLine();

	    ArrayList<User> results =
	        connection.searchUsers(keyword, loggedInUser.getUserId());

	    if (results.isEmpty()) {
	        System.out.println("No users found.");
	        return;
	    }

	    System.out.println("\nMatching users:");

	    for (int i = 0; i < results.size(); i++) {
	        User u = results.get(i);
	        System.out.println(
	            (i + 1) + ". " +
	            u.getUserName() +
	            " (" + u.getEmail() + ")"
	        );
	    }

	    System.out.print("Choose user number: ");

	    int choice;

	    try {
	        choice = Integer.parseInt(sc.nextLine());
	    } catch (Exception e) {
	        System.out.println("Invalid input.");
	        return;
	    }

	    if (choice < 1 || choice > results.size()) {
	        System.out.println("Invalid choice.");
	        return;
	    }

	    User selected = results.get(choice - 1);

	    userData.viewUserProfile(selected.getUserId());
	}

	
}
