package Content;

import Manager.Manager;
import User.*;

public class Comment extends Content{

    public Comment(SubSeraphit seraphit, User maker, String body) {
        super(seraphit, maker, body);
    }

    public void removeComment(Post post, Comment comment, User user)
    {
        boolean access = false;
        if(maker.getUsername().equals(user.getUsername()))
            access = true;
        for(User admin : comment.getAdmins())
        {
            if(admin.getUsername().equals(user.getUsername())) {
                access = true;
            }
        }
        if(!access)
        {
            System.out.println("You don't have access to this option.");
            Holder();
            return;
        }
        //removes comment from post comments
        post.getComments().remove(comment);

        //changes maker's karma
        maker.setKarma(comment.getKarma()*-1);

        //removes the comment from members upvoted list
        for(User member : seraphit.getMembers()){
            member.getUpvoted().remove(comment);
        }
    }



}
