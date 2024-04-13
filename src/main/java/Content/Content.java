package Content;

import User.User;
import User.SubSeraphit;

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
    public int getKarma() {
        return karma;
    }

    public void setKarma(int n) {
        maker.setKarma(n);
        karma += n;
    }

    //Controllers
    public static void Holder() {
        System.out.println("Enter a key to continue.");
        input.next();
    }
}
