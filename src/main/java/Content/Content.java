package Content;

import User.User;
import User.SubSeraphit;
import javafx.geometry.Pos;

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
        this.seraphit = seraphit;
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

    //functionalities
    public static void newPost(Post post, SubSeraphit sub)
    {
        sub.getPosts().add(post);
        for(User user : sub.getMembers()) {
            user.getTimeline().add(post);
        }
    }

    public void React(Content content) {
        System.out.println("Choose your reaction:\n" +
                "-1 for DownVote\n" +
                " 0 for retract\n" +
                " 1 for UpVote");
        int react = input.nextInt();

        //changes karma points based on reaction and controls if the user has reacted before or not
        if (maker.getReacts().containsKey(ID)) {
            int karmaChanges = react - maker.getReacts().get(ID);
            setKarma(karmaChanges);
        }
        else {
            setKarma(react);
        }
        maker.getReacts().put(ID, react);

        //removes the content from upvoted list or adds it if necessary
        if(react == 1) {
            boolean inList = false;
            for(Content c : maker.getUpvoted())
            {
                if(c.ID == content.ID){
                    inList = true;
                    break;
                }
            }
            if(!inList)
                maker.getUpvoted().add(content);
        }
        else{
            for(Content c : maker.getUpvoted())
            {
                if(c.ID == content.ID) {
                    maker.getUpvoted().remove(content);
                    break;
                }
            }
        }
    }








    public static void Holder() {
        System.out.println("Enter a key to continue.");
        input.next();
    }

}
