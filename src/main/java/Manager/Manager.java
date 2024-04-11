package Manager;

import org.apache.commons.codec.digest.DigestUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Content.Post;
import Content.Comment;
import User.*;

public class Manager {
    private static List<User> users = new ArrayList<>();

    // Getters ans Setters
    public static List<User> getUsers() {
        return users;
    }


    //checks if the string is a real email or not
    public static boolean validateEmail(String email){
        String regex = "^.{2,}@.{2,}\\..{3,}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (matcher.find()) {
            return true;
        }
        else {
            return false;
        }
    }

    //checks entered password with hashed password assigned to user
    public static boolean validatePassword(String enteredPassword, User user) {
        return DigestUtils.sha256Hex(enteredPassword).equals(user.getPassword());
    }

    //checks if user's email has signed up or not
    public static boolean IsSigned(String email){
        for(User user : users)
            if(DigestUtils.sha256Hex(email).equals(user.getEmail()))
                return true;
        return false;
    }

    public static boolean checkAdministration(SubSeraphit sub, User user) {
        for (User admin : sub.getAdmins()) {
            if (admin.getUsername().equals(user.getUsername()))
                return true;
        }
        return false;
    }
}
