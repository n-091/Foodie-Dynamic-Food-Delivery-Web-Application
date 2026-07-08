package com.designpattern.utility;

import java.util.List;
import java.util.Scanner;

import com.designpattern.DAoImpl.UserDAOImpl;
import com.designpattern.dao.UserDAO;
import com.designpattern.model.User;

public class UserTest {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UserDAO dao = new UserDAOImpl();

        while (true) {

            System.out.println("\n===== USER MENU =====");
            System.out.println("1. Add User");
            System.out.println("2. Update User");
            System.out.println("3. Delete User");
            System.out.println("4. Get User By ID");
            System.out.println("5. Get All Users");
            System.out.println("6. Exit");
            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

            case 1:

                User u = new User();

                System.out.print("Enter User Name: ");
                u.setUserName(sc.nextLine());

                System.out.print("Enter Email: ");
                u.setEmail(sc.nextLine());

                System.out.print("Enter Password: ");
                u.setPassword(sc.nextLine());

                System.out.print("Enter Address: ");
                u.setAddress(sc.nextLine());

                System.out.print("Enter Phone Number: ");
                u.setPhoneNo(sc.nextLine());

                System.out.print("Enter Role (customer/admin): ");
                u.setRole(sc.nextLine());

                dao.addUser(u);

                break;

            case 2:

                System.out.print("Enter User ID: ");
                int updateId = sc.nextInt();
                sc.nextLine();

                User existingUser = dao.getUser(updateId);

                if (existingUser == null) {
                    System.out.println("User Not Found");
                    break;
                }

                System.out.println("\n===== CURRENT DETAILS =====");
                System.out.println("Name     : " + existingUser.getUserName());
                System.out.println("Email    : " + existingUser.getEmail());
                System.out.println("Password : " + existingUser.getPassword());
                System.out.println("Address  : " + existingUser.getAddress());
                System.out.println("Phone    : " + existingUser.getPhoneNo());
                System.out.println("Role     : " + existingUser.getRole());

                System.out.println("\n===== UPDATE MENU =====");
                System.out.println("1. Update Name");
                System.out.println("2. Update Email");
                System.out.println("3. Update Password");
                System.out.println("4. Update Address");
                System.out.println("5. Update Phone");
                System.out.println("6. Update Role");
                System.out.println("7. Update Complete Profile");

                System.out.print("Enter Choice: ");
                int fieldChoice = sc.nextInt();
                sc.nextLine();

                switch (fieldChoice) {

                case 1:
                    System.out.print("Enter New Name: ");
                    existingUser.setUserName(sc.nextLine());
                    break;

                case 2:
                    System.out.print("Enter New Email: ");
                    existingUser.setEmail(sc.nextLine());
                    break;

                case 3:
                    System.out.print("Enter New Password: ");
                    existingUser.setPassword(sc.nextLine());
                    break;

                case 4:
                    System.out.print("Enter New Address: ");
                    existingUser.setAddress(sc.nextLine());
                    break;

                case 5:
                    System.out.print("Enter New Phone Number: ");
                    existingUser.setPhoneNo(sc.nextLine());
                    break;

                case 6:
                    System.out.print("Enter New Role: ");
                    existingUser.setRole(sc.nextLine());
                    break;

                case 7:

                    System.out.print("Enter User Name: ");
                    existingUser.setUserName(sc.nextLine());

                    System.out.print("Enter Email: ");
                    existingUser.setEmail(sc.nextLine());

                    System.out.print("Enter Password: ");
                    existingUser.setPassword(sc.nextLine());

                    System.out.print("Enter Address: ");
                    existingUser.setAddress(sc.nextLine());

                    System.out.print("Enter Phone Number: ");
                    existingUser.setPhoneNo(sc.nextLine());

                    System.out.print("Enter Role: ");
                    existingUser.setRole(sc.nextLine());

                    break;

                default:
                    System.out.println("Invalid Choice");
                    break;
                }

                dao.updateUser(existingUser);
                break;
            case 3:

                System.out.print("Enter User ID to Delete: ");
                int deleteId = sc.nextInt();

                dao.deleteUser(deleteId);

                break;

            case 4:

                System.out.print("Enter User ID: ");
                int id = sc.nextInt();

                User user = dao.getUser(id);

                if (user != null) {

                    System.out.println("\n===== USER DETAILS =====");
                    System.out.println("User ID      : " + user.getUserId());
                    System.out.println("User Name    : " + user.getUserName());
                    System.out.println("Email        : " + user.getEmail());
                    System.out.println("Password     : " + user.getPassword());
                    System.out.println("Address      : " + user.getAddress());
                    System.out.println("Phone Number : " + user.getPhoneNo());
                    System.out.println("Role         : " + user.getRole());

                } else {

                    System.out.println("User Not Found");
                }

                break;

            case 5:

                List<User> users = dao.getAllUser();

                System.out.println("\n===== ALL USERS =====");

                for (User usr : users) {

                    System.out.println(
                            usr.getUserId() + " | "
                            + usr.getUserName() + " | "
                            + usr.getEmail() + " | "
                            + usr.getPhoneNo() + " | "
                            + usr.getRole());
                }

                break;

            case 6:

                System.out.println("Thank You...");
                sc.close();
                System.exit(0);
                break;

            default:

                System.out.println("Invalid Choice");
            }
        }
    }
}