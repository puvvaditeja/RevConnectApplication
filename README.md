# RevConnect – Console-Based Social Media Application
# Project Overview

- RevConnect is a console-based social media application developed using Java, JDBC, and MySQL.
- It provides a platform for personal users, business accounts, and content creators to connect, share posts, interact with content, and build networks.
- The application follows a modular architecture (UI, DAO, Utility, and App layers) and is designed to be scalable and extendable into a microservices-based web or mobile application in future phases.

# Features
## User Authentication & Profile Management

- Register with email/username and password
- Secure login and password management
- Create and edit profile:
- Name
- Bio/About
- Profile picture path (future support)
- Location
- View own and other users' profiles
- Search users by name or username

## Post Management

- Create text posts with hashtags
- View personal posts and feed
- Edit posts
- Delete posts
- Update posts
  
## Social Interactions

- Like and unlike posts
- Comment on posts
- View comments on posts
- Delete own comments
- Share with attribution

## Network Building

- Send connection requests
- Accept or reject connection requests
- View pending requests
- Remove connections

## Notification System

- Receive notifications for:
- Connection requests
- Accepted connections
- Likes on posts
- Comments on posts
- New followers
- Post shares

## Creator and Business Account Features

- Includes all personal user features plus:
- Profile Enhancements
- Business/Creator name
- Category or industry
- Detailed bio
- Contact information
- Website and social links
- Business address
- Business hours

## Analytics

- Total likes
- Total comments
- Total shares

## Security Features

- Secure login authentication
- Password change functionality

## Database

Database: MySQL

Core Entities:

- Users
- Profiles
- Posts
- Comments
- Likes
- Followers
- Connections
- Notifications

