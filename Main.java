import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import Management.Manage;

import static Users.Account.IsUsernameInList;

public class Main {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        runMenu();
    }

    public static void runMenu() {
        ClearScreen();
        System.out.println("Welcome To Reddit!");
        System.out.println("What do you wish to do?");
        System.out.println("1- Login\n2- SignUp\n3- Quit");
        int task = input.nextInt();
        if (task == 3)
            return;
        ClearScreen();
        System.out.print("Email: ");
        String username = input.next();
        System.out.print("Password: ");
        String password = input.next();
        if (task == 1) {
            for (User user : Manage.getUsers()) {
                if (user.getEmail().equals(Email)) {
                    if (user.validatePassword(password))
                        UserMenu(student);
                    else {
                        System.out.println("Password is not correct.");
                        break;
                    }
                }
                System.out.println("UsernameNotFound!");
        }
    }

    public static void ClearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
