package User;

import org.apache.commons.codec.digest.DigestUtils;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

import Content.*;


public class User {
    private Map<UUID, Integer> Reacts = new HashMap<>();
    private List<Post> timeline = new ArrayList<>();
    private List<SubSeraphit> subSeraphits = new ArrayList<>();
    private List<Post> myPosts = new ArrayList<>();
    private List<Post> savedPosts = new ArrayList<>();
    private List<Content> upVoted = new ArrayList<>();
    private String email;
    private String username;
    private String password;
    private int karma;
    static Scanner input = new Scanner(System.in);

    public User(String email, String password) {
        this.email = DigestUtils.sha256Hex(email);
        this.password = DigestUtils.sha256Hex(password);
        karma = 0;
    }

    //Getters And Setters
    public String getEmail()
    {
        return this.email;
    }
    public String getUsername()
    {
        return this.username;
    }
    public String getPassword()
    {
        return password;
    }
    public List<Post> getTimeline()
    {
        return timeline;
    }
    public List<Post> getMyPosts() {
        return myPosts;
    }
    public int getKarma()
    {
        return karma;
    }
    public List<Post> getSavedPosts()
    {
        return savedPosts;
    }
    public List<Content> getUpvoted() {
        return upVoted;
    }
    public List<SubSeraphit> getSubSeraphits() {
        return subSeraphits;
    }
    public Map<UUID, Integer> getReacts() {
        return Reacts;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public void setTimeline(List<Post> newTimeline)
    {
        timeline = newTimeline;
    }
    public void setKarma(int n)
    {
        karma += n;
    }


    public void showProfile()
    {
        System.out.println("Username: " + this.username + "\n" +
                "karma: " + this.karma + "\n" +
                myPosts.size() + " posts\n" +
                "User's interests:");
        for(SubSeraphit sub : this.subSeraphits)
            System.out.println("- " + sub.getTopic());
    }



}
