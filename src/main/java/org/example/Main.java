package org.example;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;

import Manager.Manager;
import User.User;
import User.SubSeraphit;
import Content.Post;
import Content.Comment;

import static Content.Content.newPost;
import static Manager.Manager.IsSigned;
import static Manager.Manager.getUsers;


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
            Boolean inList = false;
            for (User user : Manager.getUsers()) {
                if (user.getEmail().equals(DigestUtils.sha256Hex(email))) {
                    inList = true;
                    if (user.validatePassword(password))
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
            if (!User.validateEmail(email)) {
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
        while (task != 12) {
            ClearScreen();
            System.out.println("Welcome to your dashboard, " + user.getUsername() + ".\n" +
                    "Your karma: " + user.getKarma() + "\n" +
                    "What do you wish to do?\n" +
                    " 1- Show my timeline\n" +
                    " 2- Manage SubSeraphits\n" +
                    " 3- My inbox\n" +
                    " 4- Search Users/SubSeraphists\n" +
                    " 5- Show my posts\n" +
                    " 6- Show my comments\n" +
                    " 7- Upvoted posts\n" +
                    " 8- Upvoted comments\n" +
                    " 9- My saved posts\n" +
                    "10- Change username\n" +
                    "11- Change Password\n" +
                    "12- LogOut");
            task = input.nextInt();
            switch (task) {
                case 1://show timeline and lets user add comment or react contents
                {
                    for (Post post : user.getTimeline()) {
                        int postTask = 0;
                        while (postTask != 5) {
                            ClearScreen();
                            post.showPost();
                            System.out.println("\n1- Show comments\n" +
                                    "2- React\n" +
                                    "3- Save post\n" +
                                    "4- Remove post\n" +
                                    "5- Next");
                            postTask = input.nextInt();
                            switch (postTask) {
                                case 1:
                                    post.showComments(user, post); break;
                                case 2:
                                    post.React(post); break;
                                case 3:
                                    user.getSavedPosts().add(post); break;
                                case 4:
                                    post.removePost(post, user); break;
                            }
                        }
                    }
                    break;
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