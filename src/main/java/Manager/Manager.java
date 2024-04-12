package Manager;

import org.apache.commons.codec.digest.DigestUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

import Content.Post;
import Content.Comment;
import User.*;

public class Manager {
    private static List<User> allUsers = new ArrayList<>();
    static Scanner input = new Scanner(System.in);

    // Getters ans Setters
    public static List<User> getUsers() {
        return allUsers;
    }



    public static boolean checkAdministration(SubSeraphit sub, User user) {
        for (User admin : sub.getAdmins()) {
            if (admin.getUsername().equals(user.getUsername()))
                return true;
        }
        return false;
    }

    //checks if user's email has signed up or not
    public static boolean IsSigned(String email){
        for(User user : allUsers)
            if(DigestUtils.sha256Hex(email).equals(user.getEmail()))
                return true;
        return false;
    }

    public static void searchUser(User user) {
        System.out.print("Find: ");
        String name = input.next();
        name = name.toLowerCase();
        boolean found = false;
        for (User u : allUsers) {
            if (u.getUsername().toLowerCase().contains(name)) {
                found = true;
                System.out.println("- " + u.getUsername());
            }
        }
        if (!found) {
            System.out.println("Nothing was found.");
            Holder();
        } else {
            System.out.print("Which one did you mean? ");
            String topic = input.next();
            ClearScreen();
            for (User u : allUsers) {
                if (u.getUsername().equals(topic)) {
                    System.out.println("- " + u.getUsername() + "\n" +
                            "karma: " + u.getKarma() + "\n" +
                            "Enter 1 to see profile.");
                    String see = input.next();
                    if (see.equals("1")) {
                        u.showProfile();
                        Holder();
                    }
                    break;
                }
            }
        }
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

    public static boolean validateUsername(String username) {
        for(User user : allUsers)
            if(username.equals(user.getUsername()))
                return false;
        return true;
    }



    public static void ClearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void Holder() {
            System.out.println("Enter a key to continue.");
            input.next();
        }

}
