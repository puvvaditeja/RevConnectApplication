package com.javacodes.ui;

import java.util.ArrayList;
import java.util.Scanner;

import com.javacodes.dao.Connections;
import com.javacodes.dao.Posts;
import com.javacodes.entity.Post;
import com.javacodes.entity.User;

public class PostUi {
	
	Scanner sc = new Scanner(System.in);
	
	private User user;   // logged-in user

    // constructor
    public PostUi(User user) {
        this.user = user;
    }

	public void createPost() {
		
		System.out.println("\n===== CREATE POST =====");
		
		Post post = new Post();
		Posts post1 = new Posts();
		
		post.setUserId(user.getUserId());
		
		System.out.println("Content: ");
		post.setContent(sc.nextLine());
		
		System.out.println("Hashtags: ");
		post.setHashtags(sc.nextLine());
		
		post1.createPost(post);  
        
        System.out.println("\nPress ENTER to save Post and go back...");
        sc.nextLine();
		
	}

	public void viewPost() {

	    System.out.println("\n===== MY POSTS =====");

	    Posts post1 = new Posts();
	    ArrayList<Post> posts = post1.getFeedWithCounts(); // or create getPostsByUser()

	    int userId = user.getUserId();

	    boolean found = false;

	    for (Post post : posts) {

	        if (post.getUserId() != userId)
	            continue; // only show my posts

	        found = true;

	        System.out.println("-------------------------");
	        System.out.println("Post ID  : " + post.getPostId());
	        System.out.println("Content  : " + post.getContent());
	        System.out.println("Hashtags : " + post.getHashtags());
	        System.out.println("Likes    : " + post.getLikeCount());
	        System.out.println("Comments : " + post.getCommentCount());
	        System.out.println("Shares   : " + post.getShareCount());

	        System.out.println("\n[U] Update  [D] Delete Post  [C] Delete Comment  [L] Unlike  [N] Next  [Q] Quit");
	        System.out.print("Choose action: ");

	        String action = sc.nextLine().toUpperCase();

	        switch (action) {

	            case "U":
	            	 	System.out.println("\nCurrent content: " + post.getContent());
	            	    System.out.println("Current hashtags: " + post.getHashtags());

	            	    System.out.print("Enter new content (press ENTER to keep same): ");
	            	    String newContent = sc.nextLine();

	            	    if (newContent.isEmpty()) {
	            	        newContent = post.getContent();
	            	    }

	            	    System.out.print("Enter new hashtags (press ENTER to keep same): ");
	            	    String newHashtags = sc.nextLine();

	            	    if (newHashtags.isEmpty()) {
	            	        newHashtags = post.getHashtags();
	            	    }

	            	    post1.updatePost(post.getPostId(), userId, newContent, newHashtags);

	            	    System.out.println("Post updated successfully!");
	            	    break;

	            case "D":
	            	System.out.print("Are you sure you want to delete this post? (yes/no): ");
	                String confirm = sc.nextLine().toLowerCase();

	                if (confirm.equals("yes")) {
	                    post1.deletePost(post.getPostId(), userId);
	                    System.out.println("Post deleted successfully!");
	                } else {
	                    System.out.println("Delete cancelled.");
	                }

	                break;

	            case "C":
	                post1.deleteComment(post.getPostId(), userId);
	                System.out.println("Your comment deleted!");
	                break;

	            case "L":
	                post1.unlikePost(post.getPostId(), userId);
	                System.out.println("Post unliked!");
	                break;

	            case "Q":
	                return;

	            case "N":
	            default:
	                break;
	        }
	    }

	    if (!found) {
	        System.out.println("You have no posts.");
	    }

	    System.out.println("\nPress ENTER to go back...");
	    sc.nextLine();
	}


	public void viewFeed() {

	    System.out.println("\n===== FEED =====");

	    Posts post1 = new Posts();
	    ArrayList<Post> posts = post1.getFeedWithCounts();

	    if (posts.isEmpty()) {
	        System.out.println("No posts in feed.");
	        return;
	    }

	    int userId = user.getUserId(); //  FIX: use logged-in user

	    for (Post post : posts) {
	    	
	    	boolean nextPost = false;

	        while (!nextPost) {

	        System.out.println("-------------------------");
	        System.out.println("Post ID  : " + post.getPostId());
	        System.out.println("User ID  : " + post.getUserId());
	        System.out.println("Content  : " + post.getContent());
	        System.out.println("Hashtags : " + post.getHashtags());
	        System.out.println("Likes    : " + post.getLikeCount());
	        System.out.println("Comments : " + post.getCommentCount());
	        System.out.println("Shares   : " + post.getShareCount());

	        // Interaction INSIDE loop
	        System.out.println("\n[L] Like  [C] Comment  [S] Share  [N] Next  [Q] Quit");
	        System.out.print("Choose action: ");

	        String action = sc.nextLine().toUpperCase();
	        
	        if (!action.matches("[LCSNQ]")) {
	            System.out.println("Invalid choice. Try again.");
	            continue; // ask again
	        }


	        switch (action) {

	            case "L":
	                int likeId = (int)(System.currentTimeMillis() % 1000000);
	                post1.likePost(post.getPostId(), userId);
	                System.out.println("Liked!");
	                break;

	            case "C":
	                System.out.print("Enter comment: ");
	                String text = sc.nextLine();

	                int commentId = (int)(System.currentTimeMillis() % 1000000);
	                post1.commentOnPost(commentId, post.getPostId(), userId, text);
	                System.out.println("You commented on this post: \"" + text + "\"");
	                break;

	            case "S":
	                System.out.print("Search username: ");
	                String keyword = sc.nextLine();
	                
	                Connections connection = new Connections();

	                ArrayList<User> results =
	                    connection.searchUsers(keyword, userId);

	                if (results.isEmpty()) {
	                    System.out.println("No users found.");
	                    break;
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
	                int choice = Integer.parseInt(sc.nextLine());

	                if (choice < 1 || choice > results.size()) {
	                    System.out.println("Invalid choice.");
	                    break;
	                }

	                User selected = results.get(choice - 1);

	                int shareId =
	                    (int)(System.currentTimeMillis() % 1000000);

	                post1.sharePost(
	                    shareId,
	                    post.getPostId(),   // current post
	                    selected.getUserId()
	                );

	                System.out.println(
	                    "Shared with " + selected.getUserName()
	                );

	                break;

	            case "Q":
	                return; // exit feed

	            case "N":
	            default:
	                break; // go to next post
	        }
	    }
	    }

	    System.out.println("\nEnd of feed. Press ENTER to go back...");
	    sc.nextLine();
	}
}
