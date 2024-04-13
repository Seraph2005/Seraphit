package Content;

import java.util.ArrayList;
import java.util.List;

import Manager.*;
import User.User;
import User.SubSeraphit;

import static Manager.Manager.checkAdministration;

public class Post extends Content{
    List<Comment> comments = new ArrayList<>();
    private final String title;

    public Post(SubSeraphit seraphit, User user, String title, String body) {
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
    public static void newPost(Post post, SubSeraphit sub) {
        sub.getPosts().add(post);
        post.getMaker().getMyPosts().add(post);
        for(User user : sub.getMembers()) {
            user.getTimeline().add(post);
        }
    }

    public void ReactPost(Post post, User user) {
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
            for(Post p : user.getUpPost())
            {
                if(p.ID == post.ID){
                    inList = true;
                    break;
                }
            }
            if(!inList)
                user.getUpPost().add(post);
        }
        else{
            user.getUpPost().remove(post);
        }
    }

    public static void removePost(List<Post> removed, User user) {
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
                member.getUpPost().remove(post);
            }

            //removes the post from all users savedList
            for(User u : Manager.getUsers())
                u.getSavedPosts().remove(post);
        }
    }

}
