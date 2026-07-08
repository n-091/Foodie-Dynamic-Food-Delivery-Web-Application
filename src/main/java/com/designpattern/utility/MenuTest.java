package com.designpattern.utility;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.designpattern.DAoImpl.MenuDAOImpl;
import com.designpattern.dao.MenuDAO;
import com.designpattern.model.Menu;

public class MenuTest {

    public static void main(String[] args) {

        try {
            Scanner sc = new Scanner(System.in);
            Connection conn = DBConnection.getConnection();

            MenuDAO dao = new MenuDAOImpl(conn);

            System.out.println("===== LOGIN TYPE =====");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.print("Enter Choice: ");

            int role = sc.nextInt();
            sc.nextLine();

            while (true) {

                // =========================
                // ADMIN MENU
                // =========================
                if (role == 1) {
                    System.out.println("\n===== ADMIN MENU SYSTEM =====");
                    System.out.println("1. Add Menu Item");
                    System.out.println("2. Update Menu Item");
                    System.out.println("3. Delete Menu Item");
                    System.out.println("4. Get Menu By ID");
                    System.out.println("5. View All Menu Items");
                    System.out.println("6. Exit");

                    System.out.print("Enter Choice: ");
                    int choice = sc.nextInt();
                    sc.nextLine();

                    switch (choice) {
                        case 1:
                            Menu m = new Menu();
                            System.out.print("Enter Restaurant ID: ");
                            m.setRestaurantId(sc.nextInt());
                            sc.nextLine();
                            System.out.print("Enter Item Name: ");
                            m.setItemName(sc.nextLine());
                            System.out.print("Enter Description: ");
                            m.setDescription(sc.nextLine());
                            System.out.print("Enter Price: ");
                            m.setPrice(sc.nextDouble());
                            sc.nextLine();
                            m.setAvailable(true);
                            dao.addMenu(m);
                            System.out.println("✅ Menu Item Added Successfully");
                            break;

                        case 2:
                            System.out.print("Enter Menu ID: ");
                            int updateId = sc.nextInt();
                            sc.nextLine();
                            Menu existing = dao.getMenu(updateId);
                            if (existing == null) {
                                System.out.println("❌ Menu Not Found");
                                break;
                            }
                            System.out.print("Enter New Item Name: ");
                            existing.setItemName(sc.nextLine());
                            System.out.print("Enter New Description: ");
                            existing.setDescription(sc.nextLine());
                            System.out.print("Enter New Price: ");
                            existing.setPrice(sc.nextDouble());
                            sc.nextLine();
                            dao.updateMenu(existing);
                            System.out.println("✅ Menu Updated Successfully");
                            break;

                        case 3:
                            System.out.print("Enter Menu ID to Delete: ");
                            int deleteId = sc.nextInt();
                            dao.deleteMenu(deleteId);
                            System.out.println("❌ Menu Deleted Successfully");
                            break;

                        case 4:
                            System.out.print("Enter Menu ID: ");
                            int id = sc.nextInt();
                            Menu menu = dao.getMenu(id);
                            if (menu != null) {
                                System.out.println("\n===== MENU DETAILS =====");
                                System.out.println("Menu ID       : " + menu.getMenuId());
                                System.out.println("Restaurant ID : " + menu.getRestaurantId());
                                System.out.println("Item Name     : " + menu.getItemName());
                                System.out.println("Description   : " + menu.getDescription());
                                System.out.println("Price         : " + menu.getPrice());
                                System.out.println("Available     : " + menu.isAvailable());
                            } else {
                                System.out.println("❌ Menu Not Found");
                            }
                            break;

                        case 5:
                            List<Menu> allMenusAdmin = dao.getAllMenu();
                            System.out.println("\n===== ALL MENU ITEMS =====");
                            if (allMenusAdmin.isEmpty()) {
                                System.out.println("No Menu Items Found");
                            } else {
                                for (Menu m1 : allMenusAdmin) {
                                    System.out.println(
                                        m1.getMenuId() + " | "
                                        + m1.getItemName() + " | ₹"
                                        + m1.getPrice() + " | "
                                        + (m1.isAvailable() ? "Available" : "Not Available")
                                        + " | Restaurant ID: " + m1.getRestaurantId()
                                    );
                                }
                            }
                            break;

                        case 6:
                            System.out.println("Thank You!");
                            sc.close();
                            System.exit(0);

                        default:
                            System.out.println("❌ Invalid Choice");
                    }
                }

                // =========================
                // CUSTOMER MENU
                // =========================
                else {
                    System.out.println("\n===== CUSTOMER MENU SYSTEM =====");
                    System.out.println("1. View Menu By Restaurant ID");
                    System.out.println("2. Get Menu By ID");
                    System.out.println("3. View All Menu Items");
                    System.out.println("4. Exit");

                    System.out.print("Enter Choice: ");
                    int choice = sc.nextInt();
                    sc.nextLine();

                    switch (choice) {
                        case 1:
                            System.out.print("Enter Restaurant ID: ");
                            int rid = sc.nextInt();
                            List<Menu> list = dao.getAllMenuByRestaurant(rid);
                            System.out.println("\n===== MENU ITEMS =====");
                            if (list.isEmpty()) {
                                System.out.println("No Menu Items Found");
                            } else {
                                for (Menu m1 : list) {
                                    System.out.println(
                                        m1.getMenuId() + " | "
                                        + m1.getItemName() + " | ₹"
                                        + m1.getPrice() + " | "
                                        + (m1.isAvailable() ? "Available" : "Not Available")
                                    );
                                }
                            }
                            break;

                        case 2:
                            System.out.print("Enter Menu ID: ");
                            int id = sc.nextInt();
                            Menu menu = dao.getMenu(id);
                            if (menu != null) {
                                System.out.println("\n===== MENU DETAILS =====");
                                System.out.println("Item Name   : " + menu.getItemName());
                                System.out.println("Description : " + menu.getDescription());
                                System.out.println("Price       : ₹" + menu.getPrice());
                                System.out.println("Available   : " + menu.isAvailable());
                            } else {
                                System.out.println("❌ Menu Not Found");
                            }
                            break;

                        case 3:
                            List<Menu> allMenusCustomer = dao.getAllMenu();
                            System.out.println("\n===== ALL MENU ITEMS =====");
                            if (allMenusCustomer.isEmpty()) {
                                System.out.println("No Menu Items Found");
                            } else {
                                for (Menu m1 : allMenusCustomer) {
                                    System.out.println(
                                        m1.getMenuId() + " | "
                                        + m1.getItemName() + " | ₹"
                                        + m1.getPrice() + " | "
                                        + (m1.isAvailable() ? "Available" : "Not Available")
                                        + " | Restaurant ID: " + m1.getRestaurantId()
                                    );
                                }
                            }
                            break;

                        case 4:
                            System.out.println("Thank You!");
                            sc.close();
                            System.exit(0);

                        default:
                            System.out.println("❌ Invalid Choice");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
