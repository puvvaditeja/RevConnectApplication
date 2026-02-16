package com.javacodes.ui;

import java.util.Scanner;

import com.javacodes.dao.UserData;
import com.javacodes.entity.User;

public class LoginUI {
	
	Scanner sc = new Scanner(System.in);
	
	public void Start() {
		
		while (true) {
		
			System.out.println("\n===== REVCONNECT LOGIN =====");
			System.out.println("1. Login");
			System.out.println("2. Register");
			System.out.println("3. Forgot Password");
			System.out.println("0. Exit");
			
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
					Login();
					break;
				case 2 : 
					Register register = new Register();
					register.Start();
					break;
				case 3 :
					forgotPassword();
				    break;
				case 0:
					System.out.println("Application exit by user from Login menu");
					System.out.println("Thank You");
					System.exit(0);
					
				default:
					System.out.println("Invalid Choice");
					break;
			}
		}
		
	}

	private void Login() {

	    System.out.print("Enter Email: ");
	    String email = sc.nextLine();

	    if (!isValidEmail(email)) {
	        System.out.println("Invalid email format!");
	        return;
	    }

	    System.out.print("Enter Password: ");
	    String password = sc.nextLine();

	    UserData userdata = new UserData();
	    User loggedInUser =
	        userdata.authenticate(email, password);

	    if (loggedInUser != null) {

	        System.out.println("Login successful!");
	        UserMenuUI usermenuui =
	            new UserMenuUI(loggedInUser);
	        usermenuui.start();

	    } else {

	        System.out.println("Invalid credentials!");
	        System.out.println("Returning to login menu...");
	    }
	}
	
	private boolean isValidEmail(String email) {

	    return email != null &&
	           email.matches("^[A-Za-z0-9._%+-]+@(gmail|yahoo|outlook)\\.com$");
	}
	
	private void forgotPassword() {

	    UserData userdata = new UserData();

	    System.out.print("\nEnter registered email: ");
	    String email = sc.nextLine();

	    try {

	        User user = userdata.getSecurityQuestion(email);

	        System.out.println("\nSecurity Question:");
	        System.out.println(user.getSecurityQuestion());

	        System.out.print("Your answer: ");
	        String answer = sc.nextLine();

	        if (!answer.equalsIgnoreCase(user.getSecurityAnswer())) {
	            System.out.println("Incorrect answer.");
	            return;
	        }

	        int otp = 100000 + (int)(Math.random() * 900000);

	        System.out.println("\nOTP (simulation): " + otp);

	        System.out.print("Enter OTP: ");
	        int enteredOtp = sc.nextInt();
	        sc.nextLine();

	        if (enteredOtp != otp) {
	            System.out.println("Invalid OTP.");
	            return;
	        }

	        System.out.print("Enter new password: ");
	        String newPass = sc.nextLine();

	        try {

	            userdata.resetPassword(user.getUserId(), newPass);

	            System.out.println("Password reset successful!");

	        } catch (IllegalArgumentException e) {

	            System.out.println(e.getMessage());
	        }

	    } catch (Exception e) {

	        System.out.println(e.getMessage());
	    }
	}

}
