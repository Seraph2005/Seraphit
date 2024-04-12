package User;

import Content.*;
import Manager.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class SubSeraphit {
    private List<User> admins = new ArrayList<>();
    private List<User> members = new ArrayList<>();
    private List<Post> posts = new ArrayList<>();
    private String topic;
    static Scanner input = new Scanner(System.in);

    public SubSeraphit(String topic){
        this.topic = topic;
    }

    //Getters and Setters
    public String getTopic() {
        return topic;
    }
    public List<User> getAdmins()
    {
        return admins;
    }
    public List<User> getMembers()
    {
        return members;
    }
    public List<Post> getPosts()
    {
        return posts;
    }

    public void setTopic(String topic)
    {
        this.topic = topic;
    }

    //Functionalities
    public static void createSubSeraphit(User admin, String topic){
        SubSeraphit sub = new SubSeraphit(topic);
        sub.getAdmins().add(admin);
        sub.getMembers().add(admin);
        admin.getSubSeraphits().add(sub);
    }

    public void LeaveSubSeraphit(User user) {
        //removing subseraphit from users subs
        for(SubSeraphit sub : user.getSubSeraphits()) {
            if (sub.getTopic().equals(topic)) {
                user.getSubSeraphits().remove(sub);
                break;
            }
        }

        //removing member from sub members
        members.remove(user);

        //removing sub posts from user's timeline
        List<Post> newTimeline = new ArrayList<>();
        newTimeline.addAll(user.getTimeline());
        for(Post post : user.getTimeline())
        {
            if(post.getSeraphit().getTopic().equals(topic))
                newTimeline.remove(post);
        }
        user.setTimeline(newTimeline);
    }



}
