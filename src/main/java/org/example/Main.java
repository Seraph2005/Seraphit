package org.example;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;

import Manager.*;
import User.User;
import User.SubSeraphit;
import Content.Post;
import Content.Comment;

import static Content.Content.newPost;
import static Manager.ContentManager.*;
import static Manager.Manager.*;
import static User.SubSeraphit.createSubSeraphit;


public class Main {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        //testers
        //User
        User admin = new User("admin@gmail.com", "ad");
        admin.setUsername("admin");
        getUsers().add(admin);
        User member = new User("seraph@gmail.com", "ser");
        member.setUsername("seraph");
        getUsers().add(member);
        //SubSeraphit
        SubSeraphit seraphim = new SubSeraphit("Seraphims");

        seraphim.getAdmins().add(admin);
        seraphim.getMembers().add(admin);
        admin.getSubSeraphits().add(seraphim);
        seraphim.getMembers().add(member);
        member.getSubSeraphits().add(seraphim);
        //contents test
        Post post = new Post(seraphim, admin, "test", "this is a test");
        newPost(post, seraphim);
        Comment adminComment = new Comment(seraphim, admin, "this cannot be removed by member");
        post.getComments().add(adminComment);
        adminComment.setKarma(5);
        while (true)
            runMenu();
    }

    public static void runMenu() {
        ClearScreen();
        System.out.println("Welcome To Seraphit!");
        System.out.println("What do you wish to do?");
        System.out.println("1- Login\n2- SignUp\n3- Quit");
        int task = input.nextInt();
        if (task == 3)
            return;
        ClearScreen();
        System.out.print("Email: ");
        String email = input.next();
        System.out.print("Password: ");
        String password = input.next();
        //LogIn
        if (task == 1) {
            boolean inList = false;
            for (User user : Manager.getUsers()) {
                if (user.getEmail().equals(DigestUtils.sha256Hex(email))) {
                    inList = true;
                    if (validatePassword(password, user))
                        UserMenu(user);
                    else {
                        System.out.println("Password is not correct.");
                        Holder();
                        return;
                    }
                }
            }
            if (!inList) {
                System.out.println("Email is incorrect.");
                Holder();
            }
        }
        //SignUp
        else if (task == 2) {
            if (!validateEmail(email)) {
                System.out.println("Email is not acceptable.");
            } else if (IsSigned(email)) {
                System.out.println("This email has already signed up.");
            } else if (password.length() < 6) {
                System.out.println("Password is not secure.");
            } else {
                User user = new User(email, password);
                //Setting a primary username based on email
                String username = email.substring(0, email.indexOf('@'));
                user.setUsername(username);
                getUsers().add(user);
                UserMenu(user);
            }
        }
    }

    public static void UserMenu(User user) {
        int task = 0;
        while (task != 11) {
            ClearScreen();
            System.out.println("Welcome to your dashboard, " + user.getUsername() + ".\n" +
                    "Your karma: " + user.getKarma() + "\n" +
                    "What do you wish to do?\n" +
                    " 1- Show my timeline\n" +
                    " 2- Manage SubSeraphits\n" +
                    " 3- Create SubSeraphit\n" +
                    " 4- Search Users/SubSeraphists\n" +
                    " 5- Show my posts\n" +
                    " 6- Upvoted posts\n" +
                    " 7- Upvoted comments\n" +
                    " 8- My saved posts\n" +
                    " 9- Change username\n" +
                    "10- Change Password\n" +
                    "11- Logout");
            task = input.nextInt();
            switch (task) {
                case 1://show timeline and lets user add comment or react contents
                {
                    showPost(user.getTimeline(), user);
                    break;
                }
                case 2://see and make posts in sub, see users and their profile, leaving sub
                    //admin: change topic, add new admin, remove user
                {
                    for(SubSeraphit seraphit : user.getSubSeraphits()){
                        showSeraphit(seraphit);
                    }
                    System.out.print("Enter subSeraphit's topic: ");
                    String topic = input.next();
                    for(SubSeraphit sub : user.getSubSeraphits()) {
                        if(sub.getTopic().equals(topic)) {
                            manageSubSeraphit(sub, user);
                            break;
                        }
                    }
                    break;
                }
                case 3: //create subseraphit
                {
                    System.out.print("topic: ");
                    String topic = input.next();
                    if(validateTopic(topic))
                        createSubSeraphit(user, topic);
                    break;
                }
                case 4: //search
                {
                    System.out.println("What are you looking for?\n" +
                            "1- a user\n" +
                            "2- a subseraphit");
                    String search = input.next();
                    switch (search)
                    {
                        case "1":
                            searchUser(user); break;
                        case "2":
                            searchSub(user); break;
                    }
                    break;
                }
                case 5: //my posts
                    showPost(user.getMyPosts(), user); break;
                case 6: //upvoted posts
                    showPost(user.getUpPost(), user); break;
                case 7: //upvoted comments
                    showUpComments(user); break;
                case 8: //saved posts
                    showPost(user.getSavedPosts(), user); break;
                case 9: //change pass
                {
                    System.out.print("Enter th new password: ");
                    String newPass = input.next();
                    if(newPass.length() > 6)
                        user.setPassword(newPass);
                }
                case 10: //change username
                {
                    System.out.print("Enter th new username: ");
                    String newUsername = input.next();
                    if(validateUsername(newUsername) && newUsername.length() > 2)
                        user.setUsername(newUsername);
                    else
                        System.out.println("This username is not acceptable."); Holder();
                }
            }
        }
    }


    public static void ClearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void Holder() {
        System.out.println("Enter a key to continue.");
        input.next();
    }
}