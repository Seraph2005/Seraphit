package Content;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Scanner;

import Manager.*;
import User.User;
import User.SubSeraphit;

import static Content.Comment.newComment;
import static Manager.Manager.checkAdministration;

public class Post extends Content{
    List<Comment> comments = new ArrayList<>();
    private final String title;
    static Scanner input = new Scanner(System.in);
    static Scanner in = new Scanner(System.in);

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
    public String getTitle()
    {
        return title;
    }


    //Functionalities in alphabetical order
    public static void removePost(List<Post> removed, User user)
    {
        for(Post post : removed)
        {
            boolean access = false;
            if(post.getMaker().getUsername().equals(user.getUsername()))
                access = true;
            if(checkAdministration(post.getSeraphit(), user))
                access = true;
            if(!access)
            {
                System.out.println("You don't have access to this option.");
                Holder();
                continue;
            }

            //Removes the post from subSeraphit and user's myPost
            post.getSeraphit().getPosts().remove(post);
            user.getMyPosts().remove(post);

            //changes maker's karma
            post.getMaker().setKarma(post.getKarma()*-1);

            //changes karma for those who commented the post
            for(Comment comment : post.getComments())
                comment.getMaker().setKarma(comment.getKarma() * -1);

            //removes the post from members upvoted list
            for(User member : post.getSeraphit().getMembers()){
                member.getUpvoted().remove(post);
            }

            //removes the post from all users savedList
            for(User u : Manager.getUsers())
                u.getSavedPosts().remove(post);
        }
    }










    public static void ClearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
