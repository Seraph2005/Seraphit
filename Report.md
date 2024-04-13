# Seraphit(My Version of reddit)
## Description
This is a terminal version of reddit, with less options and a different name. As you should know, in this app you can make different posts based on topics, comment posts and even react them.
Users can join subSeraphits(as subreddits in real app) and make posts there.

## Dependencies
- Java SE runtime 8 or later
- Gradle as package manager

## Installing
- Clone the app in [github](https://github.com)
- Download java from [Oracle](https://www.oracle.com)

# About this app

## Usage

- In each menu, you can access options by entering a number or a name.
- First you should signUp in the program, entering a **real** E-mail and a secure password.
- User menu contains options like show timeline, manage subSeraphits, show saved posts and etc.
- For creating a post, enter a subSeraphit, make a topic and then enter the body of your post.
- You can add comments by choosing "show comments" in each posts, after seeing earlier comments.
- Admins have access to add new admins, change subseraphit's info and remove posts, comments and even members.

## Classes

### Main class
- First you enter the program, you have Login and Signup options. When you enter the dashboard there are 11 options which are visible in "user menu".
- user menu: In this menu you can see username, your total karma and options below
  1. Show timeline
       > Show posts sorted by old to new
  3. Manage subSeraphit
       > Shows all subs and what you can do with each of them
  5. Create subseraphit
  7. Search
       > Searches for users and subs
  9. My posts
  11. See upvoted posts
  12. See upvoted comments
  13. See saved posts
  14. Change Username
  15. Change password
  16. Logout

### Manager
- A class for handling errors and managing users

### ContentManager(abstract)
- Handles all menues realated to contents, including subSeraphits

### Content
- Contains all the things post and comment have in common

  #### Comment(Content's Subclass)

  #### Post(Content's Subclass)

### User
- Contains all the informations about each user

### SubSeraphit
- Contains all the informations about each sub

## In the PROCESS...(Challenges & ideas)
- Security
  Passwords and E-mails are all hashed in the program.
- Unique identifiers
  - Users: username
  - SubSeraphits: Topic
  - Posts and comments: A unique UUID
- Timeline Problem
  The first problem I had was adding and removing posts from user's timeline(or any other list).
  Adding problem was because of a bad variable placement, But removing a post or comment in the middle of the for was a big problem. So, I made a new list in the begining and add removed contents to that list. After finishing the for, I removed all the contents in this list.
- Multiple reaction to the same content
  I used a HashMap which contains content's ID as key and the reaction as value. You can't add duplicate keys to HashMap.
- Removing posts or comments
  In case to remove a content, we have to remove it from several lists such as user's Upvoted contents and saved posts. We also have to reduce all user's karma who has anything to do with the post and the comment.
- Search engine
  I have a simple search engine which compares the entered string with all users usernames or subseraphits topic and show all the results containing the string.



## Credits
- resources:
  - [StackOverflow](https://stackoverflow.com)
  - [GeekForGeeks](https://www.geeksforgeeks.org)
  - [JavaTpoint](https://www.javatpoint.com)
- libraries
  - java.util.ArrayList
  - java.util.List
  - org.apache.commons.codec.digest.DigestUtils
  - java.util.Scanner
  - java.util.UUID
  - java.util.Map
  - java.util.HashMap
  - java.util.regex.Matcher
  - java.util.regex.Pattern






