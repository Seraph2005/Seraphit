package Content;

import User.User;
import User.SubSeraphit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Scanner;

public class Content{
    UUID ID;
    int karma;
    User maker;
    String body;
    SubSeraphit seraphit;
    static Scanner input = new Scanner(System.in);

    public Content(SubSeraphit seraphit, User user, String body){
        this.maker = user;
        this.body = body;
        this.seraphit = seraphit;
        ID = UUID.randomUUID();
        karma = 0;
    }

    //Getters and setters
    public String getBody()
    {
        return body;
    }
    public SubSeraphit getSeraphit()
    {
        return seraphit;
    }
    public User getMaker()
    {
        return maker;
    }
    public List<User> getAdmins()
    {
        return seraphit.getAdmins();
    }
    public int getKarma() {
        return karma;
    }

    public void setKarma(int n) {
        maker.setKarma(n);
        karma += n;
    }

    //functionalities in alphabetical order
    public static void newPost(Post post, SubSeraphit sub) {
        sub.getPosts().add(post);
        post.getMaker().getMyPosts().add(post);
        for(User user : sub.getMembers()) {
            user.getTimeline().add(post);
        }
    }

    public void React(Content content, User user) {
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
            if(content instanceof Post)
            {
                for(Post p : user.getUpPost())
                {
                    if(p.ID == content.ID){
                        inList = true;
                        break;
                    }
                }
                if(!inList)
                    user.getUpPost().add(content);
            }
            else
            {
                for(Comment c : user.getUpComment())
                {
                    if(c.ID == content.ID){
                        inList = true;
                        break;
                    }
                }
                if(!inList)
                    user.getUpComment().add(content);
            }
        }
        else{
            if(content instanceof Post)
            {
                if(user.getUpPost().contains(content))
                    user.getUpPost().remove(content);
            }
            else
            {
                if(user.getUpComment().contains(content))
                    user.getUpComment().remove(content);
            }
        }
    }






    public static void Holder() {
        System.out.println("Enter a key to continue.");
        input.next();
    }

}
