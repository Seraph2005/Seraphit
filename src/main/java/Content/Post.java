package Content;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Scanner;

import Manager.Manager;
import User.User;
import User.SubSeraphit;

public class Post extends Content{
    List<Comment> comments = new ArrayList<>();
    private final String title;
    static Scanner input = new Scanner(System.in);

    public Post(SubSeraphit seraphit, User user, String title, String body)
    {
        super(seraphit, user, body);
        this.title = title;
    }

    //Getters and Setters
    public List<Comment> getComments()
    {
        return comments;
    }


    //Functionalities
    public void showPost(){
        System.out.println(this.title + "\n" +
                body + "\n\n" +
                "karma: " + karma +
                "\nMade by " + maker.getUsername() + " from " + seraphit.getTopic());
    }

    public void showComments(User user, Post post){
        int task = 0;
        for(Comment comment: comments) {
            while(task != 1)
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
                    comment.React(comment);
                }
                else if(task == 3) {
                    comment.removeComment(post, comment, user);
                }
            }
        }
        System.out.println("Enter 1 to add a new comment.");
        int add = input.nextInt();
        if (add == 1)
        {
            System.out.print("text: ");
            input.next();
            String body = input.nextLine();
            Comment comment = new Comment(seraphit, maker, body);
            post.getComments().add(comment);
        }
    }

    public void removePost(Post post, User user)
    {
        boolean access = false;
        if(maker.getUsername().equals(user.getUsername()))
            access = true;
        for(User admin : post.getAdmins())
        {
            if(admin.getUsername().equals(user.getUsername())) {
                access = true;
                break;
            }
        }
        if(!access)
        {
            System.out.println("You don't have access to this option.");
            Holder();
            return;
        }

        //Removes the post from subSeraphit
        seraphit.getPosts().remove(post);

        //changes maker's karma
        maker.setKarma(post.getKarma()*-1);

        //changes karma for those who commented the post
        for(Comment comment : post.getComments())
            comment.getMaker().setKarma(comment.getKarma() * -1);

        //removes the post from members upvoted list
        for(User member : seraphit.getMembers()){
            member.getUpvoted().remove(post);
        }

        //removes the post from all users savedList
        for(User u : Manager.getUsers())
            u.getSavedPosts().remove(post);
    }










    public static void ClearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
