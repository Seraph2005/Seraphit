package Manager;

import Content.Comment;
import Content.Post;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

import User.*;

import static Content.Post.newPost;

public class Manager {
    private static List<User> allUsers = new ArrayList<>();
    static Scanner input = new Scanner(System.in);

    // Getters ans Setters
    public static List<User> getUsers() {
        return allUsers;
    }


    //Functionalities in alphabetical order
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

    public static void makeTestCases() {
        //testers
        //User
        User admin = new User("admin@gmail.com", "ad");
        admin.setUsername("admin");
        getUsers().add(admin);
        User member = new User("seraph@gmail.com", "ser");
        member.setUsername("seraph");
        getUsers().add(member);
        //SubSeraphit
        SubSeraphit seraphim = new SubSeraphit("Seraphims");

        seraphim.getAdmins().add(admin);
        seraphim.getMembers().add(admin);
        admin.getSubSeraphits().add(seraphim);
        seraphim.getMembers().add(member);
        member.getSubSeraphits().add(seraphim);
        //contents test
        Post post = new Post(seraphim, admin, "test", "this is a test");
        newPost(post, seraphim);
        Comment adminComment = new Comment(seraphim, admin, "this cannot be removed by member");
        post.getComments().add(adminComment);
        adminComment.setKarma(5);
    }

    public static void searchUser() {
        System.out.print("Find: ");
        String name = input.nextLine();
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
            String topic = input.nextLine();
            ClearScreen();
            for (User u : allUsers) {
                if (u.getUsername().equals(topic)) {
                    System.out.println("- " + u.getUsername() + "\n" +
                            "karma: " + u.getKarma() + "\n" +
                            "Enter 1 to see profile.");
                    String see = input.nextLine();
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




    //Controlers
    public static void ClearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void Holder() {
            System.out.println("Enter a key to continue.");
            input.next();
        }

}
