package com.designpattern.utility;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.designpattern.DAoImpl.RestaurantDAOImpl;
import com.designpattern.dao.RestaurantDAO;
import com.designpattern.model.Restaurant;

public class RestaurantTest {

    public static void main(String[] args) {

        try {

            Scanner sc = new Scanner(System.in);

            Connection conn = DBConnection.getConnection();
            RestaurantDAO dao = new RestaurantDAOImpl(conn);

            System.out.println("===== LOGIN TYPE =====");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.print("Enter Choice: ");

            int role = sc.nextInt();
            sc.nextLine();

            boolean running = true;

            while (running) {

                // =========================
                // ADMIN MENU
                // =========================
                if (role == 1) {

                    System.out.println("\n===== ADMIN RESTAURANT MENU =====");
                    System.out.println("1. Add Restaurant");
                    System.out.println("2. Update Restaurant");
                    System.out.println("3. Delete Restaurant");
                    System.out.println("4. Get Restaurant By ID");
                    System.out.println("5. Get All Restaurants");
                    System.out.println("6. Exit");
                }

                // =========================
                // CUSTOMER MENU
                // =========================
                else {

                    System.out.println("\n===== CUSTOMER RESTAURANT MENU =====");
                    System.out.println("1. View Restaurants");
                    System.out.println("2. View Restaurant By ID");
                    System.out.println("3. Exit");
                }

                System.out.print("Enter Choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                // =========================
                // ADMIN OPERATIONS
                // =========================
                if (role == 1) {

                    switch (choice) {

                        case 1:
                            Restaurant r = new Restaurant();

                            System.out.print("Enter Name: ");
                            r.setName(sc.nextLine());

                            System.out.print("Enter Cuisine Type: ");
                            r.setCuisineType(sc.nextLine());

                            System.out.print("Enter Delivery Time: ");
                            r.setDeliveryTime(sc.nextInt());
                            sc.nextLine();

                            System.out.print("Enter Address: ");
                            r.setAddress(sc.nextLine());

                            System.out.print("Enter Admin User ID: ");
                            r.setAdminUserId(sc.nextInt());

                            System.out.print("Enter Rating: ");
                            r.setRating(sc.nextDouble());
                            sc.nextLine();

                            r.setActive(true);

                            dao.addRestaurant(r);
                            System.out.println("✅ Restaurant Added");
                            break;

                        case 2:
                            System.out.print("Enter Restaurant ID: ");
                            int updateId = sc.nextInt();
                            sc.nextLine();

                            Restaurant existing = dao.getRestaurant(updateId);

                            if (existing == null) {
                                System.out.println("❌ Not Found");
                                break;
                            }

                            System.out.print("Enter New Name: ");
                            existing.setName(sc.nextLine());

                            System.out.print("Enter New Cuisine: ");
                            existing.setCuisineType(sc.nextLine());

                            System.out.print("Enter New Delivery Time: ");
                            existing.setDeliveryTime(sc.nextInt());
                            sc.nextLine();

                            System.out.print("Enter New Address: ");
                            existing.setAddress(sc.nextLine());

                            System.out.print("Enter New Rating: ");
                            existing.setRating(sc.nextDouble());
                            sc.nextLine();

                            dao.updateRestaurant(existing);
                            System.out.println("✅ Updated");
                            break;

                        case 3:
                            System.out.print("Enter ID to Delete: ");
                            int deleteId = sc.nextInt();

                            dao.deleteRestaurant(deleteId);
                            System.out.println("❌ Deleted");
                            break;

                        case 4:
                            System.out.print("Enter ID: ");
                            int id = sc.nextInt();

                            Restaurant res = dao.getRestaurant(id);

                            if (res != null) {
                                System.out.println(res.getName() + " | " + res.getCuisineType());
                            } else {
                                System.out.println("Not Found");
                            }
                            break;

                        case 5:
                            List<Restaurant> list = dao.getAllRestaurants();

                            System.out.println("\n===== ALL RESTAURANTS =====");

                            if (list.isEmpty()) {
                                System.out.println("No restaurants found");
                                break;
                            }

                            for (Restaurant r1 : list) {
                                System.out.println(
                                        r1.getRestaurantId() + " | "
                                        + r1.getName() + " | "
                                        + r1.getCuisineType() + " | "
                                        + r1.getDeliveryTime() + " | "
                                        + r1.getRating()
                                );
                            }
                            break;

                        case 6:
                            System.out.println("Thank You!");
                            running = false;
                            break;

                        default:
                            System.out.println("Invalid Choice");
                    }
                }

                // =========================
                // CUSTOMER OPERATIONS
                // =========================
                else {

                    switch (choice) {

                        case 1:
                            List<Restaurant> list = dao.getAllRestaurants();

                            System.out.println("\n===== RESTAURANTS (CUSTOMER VIEW) =====");

                            if (list.isEmpty()) {
                                System.out.println("No restaurants available");
                                break;
                            }

                            for (Restaurant r1 : list) {
                                System.out.println(
                                        r1.getRestaurantId() + " | "
                                        + r1.getName() + " | "
                                        + r1.getCuisineType() + " | "
                                        + r1.getDeliveryTime() + " mins | "
                                        + r1.getRating()
                                );
                            }
                            break;

                        case 2:
                            System.out.print("Enter ID: ");
                            int id = sc.nextInt();

                            Restaurant res = dao.getRestaurant(id);

                            if (res != null) {
                                System.out.println(
                                        res.getName() + " | " +
                                        res.getCuisineType() + " | " +
                                        res.getRating()
                                );
                            } else {
                                System.out.println("Not Found");
                            }
                            break;

                        case 3:
                            System.out.println("Thank You!");
                            running = false;
                            break;

                        default:
                            System.out.println("Invalid Choice");
                    }
                }
            }

            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}









