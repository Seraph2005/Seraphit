package Manager;

import org.apache.commons.codec.digest.DigestUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Content.Post;
import Content.Comment;
import User.User;

public class Manager {
    private static List<User> users = new ArrayList<>();
    private static List<Post> AllPosts = new ArrayList<>();

    // Getters ans Setters
    public static List<User> getUsers() {
        return users;
    }
    public static List<Post> getAllPosts()
    {
        return AllPosts;
    }

    //checks if user's email has signed up or not
    public static Boolean IsSigned(String email){
        for(User user : users)
            if(DigestUtils.sha256Hex(email).equals(user.getEmail()))
                return true;
        return false;
    }

}
