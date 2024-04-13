package Content;

import java.util.List;

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

    public void ReactComment(Comment comment, User user) {
        System.out.println("Choose your reaction:\n" +
                "-1 for DownVote\n" +
                " 0 for retract\n" +
                " 1 for UpVote");
        int react = input.nextInt();

        //changes karma points based on reaction and controls if the user has reacted before or not
        if (user.getReacts().containsKey(ID)) {
            int karmaChanges = react - user.getReacts().get(ID);
            setKarma(karmaChanges);
        }
        else {
            setKarma(react);
        }
        user.getReacts().put(ID, react);

        //removes the content from upvoted list or adds it if necessary
        if(react == 1) {
            boolean inList = false;
            for(Comment c : user.getUpComment())
            {
                if(c.ID == comment.ID){
                    inList = true;
                    break;
                }
            }
            if(!inList)
                user.getUpComment().add(comment);
        }
        else{
            if(user.getUpComment().contains(comment))
                user.getUpComment().remove(comment);
        }
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
