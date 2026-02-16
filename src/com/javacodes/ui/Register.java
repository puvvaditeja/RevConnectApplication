package com.javacodes.ui;

import java.util.Scanner;

import com.javacodes.dao.UserData;
import com.javacodes.entity.Profile;
import com.javacodes.entity.User;

public class Register {
	
	Scanner sc = new Scanner(System.in);
	
	User user = new User();
	Profile profile = new Profile();

	public void Start() {
		
		System.out.println("\n===== REGISTER =====");
		
		System.out.println("username: ");
		user.setUserName(sc.nextLine());
		
		UserData userdata = new UserData();
		
		while (true) {
		    System.out.print("Email: ");
		    String email = sc.nextLine();
		    
		    if (!isValidGmail(email)) {
		        System.out.println(" Email must end with @gmail.com");
		        continue;
		    }

		    if (userdata.isEmailExists(email)) {
		        System.out.println(" Email already registered!");
		        System.out.println(" Redirecting to Login...\n");

		        LoginUI loginUI = new LoginUI();
		        loginUI.Start();
		        return; // STOP registration immediately
		    }

		    //  valid & not existing
		    user.setEmail(email);
		    break;
		    
		}
		
		while (true) {
		    System.out.print("Password: ");
		    String password = sc.nextLine();

		    if (isValidPassword(password)) {
		        user.setPassword(password);
		        break;
		    } else {
		        System.out.println(
		            " Password must contain:\n" +
		            " Minimum 8 characters\n" +
		            " At least 1 uppercase letter\n" +
		            " At least 1 lowercase letter\n" +
		            " At least 1 digit\n" +
		            " At least 1 special character"
		        );
		    }
		}
		
		String[] questions = {
			    "What is your pet name?",
			    "What is your favorite teacher name?",
			    "What is your birth city?",
			    "What is your favorite movie?"
			};

			System.out.println("\nChoose a security question:");

			for (int i = 0; i < questions.length; i++) {
			    System.out.println((i + 1) + ". " + questions[i]);
			}

			int qChoice;

			while (true) {
			    System.out.print("Enter choice (1-4): ");

			    if (sc.hasNextInt()) {
			        qChoice = sc.nextInt();
			        sc.nextLine();

			        if (qChoice >= 1 && qChoice <= questions.length) {
			            break;
			        }
			    } else {
			        sc.nextLine();
			    }

			    System.out.println("Invalid choice. Try again.");
			}

			user.setSecurityQuestion(questions[qChoice - 1]);

			System.out.print("Enter answer: ");
			user.setSecurityAnswer(sc.nextLine());
			
		System.out.println("\nSelect Account Type:");
        System.out.println("1. Personal User");
        System.out.println("2. Content Creator");
        System.out.println("3. Business Account");
        System.out.print("Enter choice: ");
        
        int choice = sc.nextInt();
        sc.nextLine();
                
        if(choice == 2) {
        	user.setAccountType("CREATOR");
        }else if(choice == 3) {
        	user.setAccountType("BUSINESS");
        }else{
        	user.setAccountType("PERSONAL");
        }
                
        System.out.println("\n--- Create Profile ---");
        
        System.out.print("Name: ");
        profile.setName(sc.nextLine());
        
        System.out.print("Bio: ");
        profile.setBio(sc.nextLine());
        
        System.out.print("Profile Pic Path: ");
        profile.setProficPicPath(sc.nextLine());
        
        System.out.print("Location: ");
        profile.setLocation(sc.nextLine());
       
        
        if(user.getAccountType().equals("CREATOR") || user.getAccountType().equals("BUSINESS")) {
        	
        	System.out.println("Category / Industry: ");
        	profile.setCategory(sc.nextLine());
        	
        	System.out.println("Contact Info (email/phone): ");
        	profile.setContact(sc.nextLine());
        	
        	System.out.println("Website / Social Link: ");
        	profile.setWebsite(sc.nextLine());
        	
        	if (user.getAccountType().equals("BUSINESS")) {
        		
                System.out.print("Business Address: ");
                profile.setBusinessAddress(sc.nextLine());

                System.out.print("Business Hours: ");
                profile.setBusinessHours(sc.nextLine());
            }
        }
        
        
        userdata.addRegisterData(user,profile);        
        
        System.out.println("Registration successful");
	}
	
	private boolean isValidPassword(String password) {

	    if (password.length() < 8) return false;

	    boolean hasUpper = false;
	    boolean hasLower = false;
	    boolean hasDigit = false;
	    boolean hasSpecial = false;

	    for (char ch : password.toCharArray()) {
	        if (Character.isUpperCase(ch)) hasUpper = true;
	        else if (Character.isLowerCase(ch)) hasLower = true;
	        else if (Character.isDigit(ch)) hasDigit = true;
	        else hasSpecial = true;
	    }

	    return hasUpper && hasLower && hasDigit && hasSpecial;
	}
	
	private boolean isValidGmail(String email) {
	    return email != null && email.endsWith("@gmail.com");
	}
	 
}
