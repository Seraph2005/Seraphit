package Manager;

import Content.Comment;
import Content.Content;
import Content.Post;
import User.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Content.Comment.newComment;
import static Content.Comment.removeComment;
import static Content.Content.newPost;
import static Content.Post.removePost;
import static Manager.Manager.checkAdministration;

public abstract class ContentManager {
    private static List<Post> allPosts = new ArrayList<>();
    private static List<SubSeraphit> allSubs = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    static Scanner in = new Scanner(System.in);

    //Getters and setters
    public static List<Post> getAllPosts()
    {
        return allPosts;
    }
    public static List<SubSeraphit> getAllSubs()
    {
        return allSubs;
    }

    //Functionalities
    public static void AdminEdition(User admin, SubSeraphit sub) {
        //Lets admin change topic, add new admin and remove members
        ClearScreen();
        System.out.println("Admin Options:\n" +
                "1- change topic\n" +
                "2- add new admin\n" +
                "3- remove members\n");
        int task = input.nextInt();
        ClearScreen();
        switch (task)
        {
            case 1://change topic
            {
                System.out.print("Enter the topic: ");
                String topic = input.next();
                if(validateTopic(topic))
                    sub.setTopic(topic);
                else {
                    System.out.println("This topic is already taken.");
                    Holder();
                }
            }
            case 2://new admin
            {
                for(User user : sub.getMembers())
                {
                    if(!sub.getAdmins().contains(user))
                        System.out.println("- " + user.getUsername());
                }
                System.out.print("Enter new admin's username: ");
                String newAdmin = input.next();
                for(User user : sub.getMembers())
                {
                    if(user.getUsername().equals(newAdmin)) {
                        if(!checkAdministration(sub, user))
                            sub.getAdmins().add(user);
                        break;
                    }
                }
            }
            case 3://remove members
            {
                for(User user : sub.getMembers())
                {
                    if(!sub.getAdmins().contains(user))
                        System.out.println("- " + user.getUsername());
                }
                System.out.print("Enter new admin's username: ");
                String newAdmin = input.next();
                for(User user : sub.getMembers())
                {
                    if(user.getUsername().equals(newAdmin)) {
                        sub.LeaveSubSeraphit(user);
                        break;
                    }
                }
            }
        }
    }

    public static void manageSubSeraphit(SubSeraphit sub, User user){
        //see and make posts in sub, see users and their profile, leaving sub
        //if admin: change topic, add new admin, remove member
        ClearScreen();
        System.out.println("- Topic: " + sub.getTopic() + "\n" +
                sub.getMembers().size() + " members\n" +
                sub.getPosts().size() + "posts\n\n" +
                "1- See posts\n" +
                "2- Make new post\n" +
                "3- See members\n" +
                "4- Leave SubSeraphit\n" +
                "5- Edit info(only admin)\n" +
                "What do you wish to do?");
        int task = input.nextInt();
        ClearScreen();
        switch (task)
        {
            case 1://see posts
            {
                for(Post post : sub.getPosts()) {
                    showPost(sub.getPosts(), user);
                }
                break;
            }
            case 2://new post
            {
                System.out.println("Title: ");
                String title = input.next();
                System.out.println("Text: ");
                String body = in.nextLine();
                Post p = new Post(sub, user, title, body);
                newPost(p, sub);
                user.getMyPosts().add(p);
                break;
            }
            case 3://see members
            {
                for(User member : sub.getMembers())
                {
                    System.out.println("- " + member.getUsername() +
                            "\nkarma: " + member.getKarma() + "\n");
                }
                System.out.print("Enter username to see profile: ");
                String username = input.next();
                for(User member : sub.getMembers())
                {
                    if(member.getUsername().equals(username))
                    {
                        member.showProfile();
                        Holder();
                        break;
                    }
                }
                break;
            }
            case 4://leave
            {
                sub.LeaveSubSeraphit(user);
                break;
            }
            case 5://edit info
            {
                if(checkAdministration(sub, user))
                    AdminEdition(user, sub);
                else{
                    System.out.println("You don't have access to this option.");
                    Holder();
                }
            }
        }
    }

    public static void searchSub(User user) {
        System.out.print("Find: ");
        String name = input.next();
        name = name.toLowerCase();
        boolean found = false;
        for(SubSeraphit sub : allSubs){
            if(sub.getTopic().toLowerCase().contains(name)){
                found = true;
                System.out.println("- " + sub.getTopic());
            }
        }
        if(!found)
        {
            System.out.println("Nothing was found.");
            Holder();
        }
        else
        {
            System.out.print("Which one did you mean? ");
            String topic = input.next();
            ClearScreen();
            for(SubSeraphit sub : allSubs){
                if(sub.getTopic().equals(topic)){
                    showSeraphit(sub);
                    System.out.println("Enter 1 to join.");
                    String join = input.next();
                    if(join.equals("1"))
                    {
                        sub.getMembers().add(user);
                        user.getSubSeraphits().add(sub);
                    }
                    break;
                }
            }
        }
    }

    public static void showComments(User user, Post post){
        List<Comment> removed = new ArrayList<>();
        for(Comment comment: post.getComments()) {
            int task = 0;
            while(task != 1 && task != 3)
            {
                ClearScreen();
                System.out.println(comment.getMaker().getUsername() + "\n" +
                        comment.getBody() + "\n" +
                        "karma: " + comment.getKarma() +
                        "\n\n1- next\n" +
                        "2- react\n" +
                        "3- remove");
                task = input.nextInt();
                if(task == 2) {
                    comment.React(comment, user);
                }
                else if(task == 3) {
                    removed.add(comment);
                }
            }
        }
        removeComment(post, removed, user);
        System.out.println("Enter 1 to add a new comment.");
        int add = input.nextInt();
        if (add == 1)
        {
            System.out.print("text: ");
            String body = in.nextLine();
            newComment(post, user, body);
        }
    }

    public static void showUpComments(User user){
        for(Comment comment: user.getUpComment()) {
            int task = 0;
            while(task != 1)
            {
                ClearScreen();
                System.out.println(comment.getMaker().getUsername() + "\n" +
                        comment.getBody() + "\n" +
                        "karma: " + comment.getKarma() +
                        "\n\n1- next\n" +
                        "2- react\n");
                task = input.nextInt();
                if(task == 2) {
                    comment.React(comment, user);
                }
            }
        }
    }

    public static void showPost(List<Post> posts, User user){
        List<Post> removed = new ArrayList<>();
        for (Post post : posts) {
            int task = 0;
            while (task < 4) {
                ClearScreen();
                System.out.println(post.getTitle() + "\n" +
                        post.getBody() + "\n\n" +
                        "karma: " + post.getKarma() +
                        "\nMade by " + post.getMaker().getUsername() + " from " + post.getSeraphit().getTopic());
                System.out.println("\n1- Show comments\n" +
                        "2- React\n" +
                        "3- Save post\n" +
                        "4- Remove post\n" +
                        "5- Next");
                task = input.nextInt();
                switch (task) {
                    case 1:
                        showComments(user, post); break;
                    case 2:
                        post.React(post, user); break;
                    case 3:
                        user.getSavedPosts().add(post); break;
                    case 4:
                        removed.add(post); break;
                }
            }
        }
        removePost(removed, user);
    }

    //see and make posts in sub, see users and their profile, leaving sub, if admin: add new admin, remove user
    public static void showSeraphit(SubSeraphit sub) {
        System.out.println("- " + sub.getTopic() + "\n" +
                sub.getMembers().size() + " members\n" +
                sub.getPosts().size() + " posts\n");
    }

    //see if this topic is already taken
    public static boolean validateTopic(String topic){
        for (SubSeraphit sub : allSubs)
        {
            if(sub.getTopic().equals(topic))
                return false;
        }
        return true;
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
