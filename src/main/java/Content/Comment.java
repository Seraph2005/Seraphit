package Content;

import java.util.List;
import java.util.ArrayList;

import Manager.*;
import User.*;

import static Manager.Manager.checkAdministration;

public class Comment extends Content{

    public Comment(SubSeraphit seraphit, User maker, String body) {
        super(seraphit, maker, body);
    }

    //functionallities in alphabeticall order
    public static void newComment(Post post, User maker, String body){
        Comment comment = new Comment(post.getSeraphit(), maker, body);
        post.getComments().add(comment);
    }

    public static void removeComment(Post post, List<Comment> removed, User user) {
        for(Comment comment : removed)
        {
            boolean access = false;
            if(post.getMaker().getUsername().equals(user.getUsername()))
                access = true;
            if(checkAdministration(comment.getSeraphit(), user))
                access = true;
            if(!access)
            {
                System.out.println("You don't have access to this option.");
                Holder();
                continue;
            }
            //removes comment from post comments
            post.getComments().remove(comment);

            //changes maker's karma
            post.getMaker().setKarma(comment.getKarma()*-1);

            //removes the comment from members upvoted list
            for(User member : post.getSeraphit().getMembers()){
                member.getUpComment().remove(comment);
            }
        }
    }


}
