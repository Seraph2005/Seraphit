package User;

import Content.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import org.apache.commons.codec.digest.DigestUtils;

public class SubSeraphit {
    private List<User> admins = new ArrayList<>();
    private List<User> members = new ArrayList<>();
    private List<Post> posts = new ArrayList<>();
    private String topic;
    private UUID ID;

    public SubSeraphit(String topic){
        this.topic = topic;
        ID = UUID.randomUUID();
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
}
