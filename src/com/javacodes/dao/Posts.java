package com.javacodes.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.javacodes.entity.Comment;
import com.javacodes.entity.Post;

public class Posts {
	
	String mysqlDriver = "com.mysql.cj.jdbc.Driver";
	String userName = "root";
	String passwd = "root";
	String connectionUrl = "jdbc:mysql://localhost:3306/revConnect";
	
	public void createPost(Post post) {
		
		String sqlQuery = "INSERT INTO POSTS (user_id,content,hashtags) VALUES (?,?,?)";
		
		try (			
			Connection con = DriverManager.getConnection(connectionUrl,userName,passwd);
			
			PreparedStatement pst = con.prepareStatement(sqlQuery)){
			
			pst.setInt(1,post.getUserId());
			pst.setString(2,post.getContent());
			pst.setString(3,post.getHashtags());
			
			pst.executeUpdate();
			
			con.close();
			
		} catch (Exception e) {
			
			throw new RuntimeException("Failed to create post.", e);
		}		
		
	}
	
	public void viewPostsByUser(int userId) {

	    String sqlQuery = "SELECT post_id, content, hashtags FROM posts WHERE user_id = ?";

	    try (
	        Connection con = DriverManager.getConnection(connectionUrl, userName, passwd);

	        PreparedStatement pst = con.prepareStatement(sqlQuery)){
	        pst.setInt(1, userId);

	        ResultSet rs = pst.executeQuery();

	        boolean found = false;

	        while (rs.next()) {
	            found = true;
	            System.out.println("------------------");
	            System.out.println("Post ID  : " + rs.getInt("post_id"));
	            System.out.println("Content  : " + rs.getString("content"));
	            System.out.println("Hashtags : " + rs.getString("hashtags"));
	        }

	        if (!found) {
	            System.out.println("No posts found.");
	        }

	        con.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


	public ArrayList<Post> getFeedWithCounts() {
		
		String sql = "SELECT p.post_id, p.user_id, p.content, p.hashtags, " +
		        "(SELECT COUNT(*) FROM post_likes l WHERE l.post_id = p.post_id) AS like_count, " +
		        "(SELECT COUNT(*) FROM post_comments c WHERE c.post_id = p.post_id) AS comment_count, " +
		        "(SELECT COUNT(*) FROM post_shares s WHERE s.post_id = p.post_id) AS share_count " +
		        "FROM posts p ORDER BY p.created_at DESC";
		
		ArrayList<Post> posts = new ArrayList<>();

	        try (Connection con = DriverManager.getConnection(connectionUrl, userName, passwd);
	             Statement st = con.createStatement();
	             ResultSet rs = st.executeQuery(sql)) {

	            while (rs.next()) {
	                Post post = new Post();
	                post.setPostId(rs.getInt("post_id"));
	                post.setUserId(rs.getInt("user_id"));
	                post.setContent(rs.getString("content"));
	                post.setHashtags(rs.getString("hashtags"));
	                post.setLikeCount(rs.getInt("like_count"));
	                post.setCommentCount(rs.getInt("comment_count"));
	                post.setShareCount(rs.getInt("share_count"));

	                posts.add(post);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return posts;
        
        
		
	}


	public boolean likePost(int postId, int userId) {

	    String sql =
	        "INSERT INTO post_likes (post_id, user_id) VALUES (?, ?)";

	    String ownerSql =
	        "SELECT user_id FROM posts WHERE post_id = ?";

	    try (
	        Connection con =
	            DriverManager.getConnection(connectionUrl, userName, passwd);
	        PreparedStatement pst =
	            con.prepareStatement(sql)
	    ) {

	        pst.setInt(1, postId);
	        pst.setInt(2, userId);

	        pst.executeUpdate();

	        // get post owner
	        PreparedStatement pst2 =
	            con.prepareStatement(ownerSql);

	        pst2.setInt(1, postId);

	        ResultSet rs = pst2.executeQuery();

	        if (!rs.next()) {
	            throw new IllegalArgumentException("Post not found.");
	        }

	        int postOwnerId = rs.getInt("user_id");

	        createNotification(
	            postOwnerId,
	            "LIKE",
	            "Someone liked your post",
	            postId
	        );

	        return true;

	    } catch (Exception e) {

	        if (e.getMessage() != null &&
	            e.getMessage().toLowerCase().contains("uq_like")) {

	            throw new IllegalStateException(
	                "You already liked this post."
	            );
	        }

	        throw new RuntimeException(
	            "Failed to like post.", e
	        );
	    }
	}


	public boolean commentOnPost(int commentId, int postId, int userId, String commentText) {

		String sql =
		        "INSERT INTO post_comments (comment_id, post_id, user_id, comment_text) VALUES (?, ?, ?, ?)";

		    try (
		        Connection con = DriverManager.getConnection(connectionUrl, userName, passwd);
		        PreparedStatement pst = con.prepareStatement(sql)
		    ) {
		    	pst.setInt(1, commentId);
		        pst.setInt(2, postId);
		        pst.setInt(3, userId);
		        pst.setString(4, commentText);

		        pst.executeUpdate();
		        return true;   

		    } catch (Exception e) {
		    	 throw new RuntimeException(
		    	            "Failed to comment on post.", e
		    	        );
		    }

	}


	public boolean sharePost(int shareId, int postId, int userId) {

		String sql =
		        "INSERT INTO post_shares (share_id, post_id, user_id) VALUES (?, ?, ?)";

		    try (
		        Connection con = DriverManager.getConnection(connectionUrl, userName, passwd);
		        PreparedStatement pst = con.prepareStatement(sql)
		    ) {
		        pst.setInt(1, shareId);   // manual share id
		        pst.setInt(2, postId);
		        pst.setInt(3, userId);

		        pst.executeUpdate();
		        return true;

		    } catch (Exception e) {
		    	 throw new RuntimeException(
		    	            "Failed to share post.", e
		    	        );
		    }
	}
	
	public boolean updatePost(
	        int postId,
	        int userId,
	        String content,
	        String hashtags) {

	    String sql =
	        "UPDATE posts SET content = ?, hashtags = ? " +
	        "WHERE post_id = ? AND user_id = ?";

	    try (
	        Connection con =
	            DriverManager.getConnection(connectionUrl, userName, passwd);
	        PreparedStatement pst =
	            con.prepareStatement(sql)
	    ) {

	        pst.setString(1, content);
	        pst.setString(2, hashtags);
	        pst.setInt(3, postId);
	        pst.setInt(4, userId);

	        int rows = pst.executeUpdate();

	        if (rows == 0) {
	            throw new IllegalArgumentException(
	                "Post not found or not owned by user."
	            );
	        }

	        return true;

	    } catch (IllegalArgumentException e) {

	        // throw business exception
	        throw e;

	    } catch (Exception e) {

	        throw new RuntimeException(
	            "Failed to update post.", e
	        );
	    }
	}
	
	public boolean deleteComment(int postId, int userId) {

	    String sql = "DELETE FROM post_comments WHERE post_id=? AND user_id=?";

	    try (Connection con = DriverManager.getConnection(connectionUrl, userName, passwd);
	         PreparedStatement pst = con.prepareStatement(sql)) {

	        pst.setInt(1, postId);
	        pst.setInt(2, userId);

	        int rows = pst.executeUpdate();
	        if (rows == 0) {
	            throw new IllegalArgumentException(
	                "Comment not found."
	            );
	        }

	        return true;

	    } catch (IllegalArgumentException e) {
	        throw e;
	    } catch (Exception e) {
	        throw new RuntimeException(
	            "Failed to delete comment.", e
	        );
	    }
	}
	
	public boolean deletePost(int postId, int userId) {

	    String sql = "DELETE FROM posts WHERE post_id = ? AND user_id = ?";

	    try (Connection con = DriverManager.getConnection(connectionUrl, userName, passwd);
	         PreparedStatement pst = con.prepareStatement(sql)) {

	        pst.setInt(1, postId);
	        pst.setInt(2, userId);

	        int rows = pst.executeUpdate();
	        if (rows == 0) {
	            throw new IllegalArgumentException(
	                "Post not found or not owned by user."
	            );
	        }

	        return true;

	    } catch (IllegalArgumentException e) {
	        throw e;
	    } catch (Exception e) {
	        throw new RuntimeException(
	            "Failed to delete post.", e
	        );
	    }
	}
	
public boolean unlikePost(int postId, int userId) {
		
		String sql = "DELETE FROM post_likes WHERE post_id = ? AND user_id = ?";

	    try (Connection con = DriverManager.getConnection(connectionUrl, userName, passwd);
	         PreparedStatement pst = con.prepareStatement(sql)) {

	        pst.setInt(1, postId);
	        pst.setInt(2, userId);

	        int rows = pst.executeUpdate();

	        if (rows == 0) {
	            throw new IllegalArgumentException(
	                "Like not found."
	            );
	        }

	        return true;

	    } catch (IllegalArgumentException e) {
	        throw e;
	    } catch (Exception e) {
	        throw new RuntimeException(
	            "Failed to unlike post.", e
	        );
	    }
	}
	
	public ArrayList<Comment> getCommentsByPost(int postId) {

    String sql =
        "SELECT u.username, c.comment_text " +
        "FROM post_comments c " +
        "JOIN user u ON c.user_id = u.user_id " +
        "WHERE c.post_id = ? " +
        "ORDER BY c.comment_id ASC";

    ArrayList<Comment> list = new ArrayList<>();

    try (Connection con = DriverManager.getConnection(connectionUrl, userName, passwd);
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setInt(1, postId);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Comment c = new Comment();
            c.setUserId(rs.getInt("userid"));
            c.setCommentText(rs.getString("comment_text"));
            list.add(c);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
	}
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
	
}
