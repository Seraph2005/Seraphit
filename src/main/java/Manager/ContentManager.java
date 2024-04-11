package Manager;

import Content.Comment;
import Content.Post;
import User.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Content.Comment.newComment;
import static Content.Comment.removeComment;
import static Content.Content.newPost;
import static Content.Post.removePost;

public abstract class ContentManager {
    private static List<Post> AllPosts = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    static Scanner in = new Scanner(System.in);

    //Getters and setters
    public static List<Post> getAllPosts()
    {
        return AllPosts;
    }


    //Functionalities
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
            case 5://info
            {

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








    public static void ClearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void Holder() {
        System.out.println("Enter a key to continue.");
        input.next();
    }
}
