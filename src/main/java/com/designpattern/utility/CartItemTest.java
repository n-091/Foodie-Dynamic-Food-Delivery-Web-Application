package com.designpattern.utility;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.designpattern.DAoImpl.CartItemDAOImpl;
import com.designpattern.dao.CartItemDAO;
import com.designpattern.model.CartItem;

public class CartItemTest {

    public static void main(String[] args) {

        try {

            Scanner sc = new Scanner(System.in);

            Connection conn = DBConnection.getConnection();

            CartItemDAO dao = new CartItemDAOImpl(conn);

            while (true) {

                System.out.println("\n===== CART ITEM MENU =====");
                System.out.println("1. Add Cart Item");
                System.out.println("2. Get Cart Item By ID");
                System.out.println("3. View Items By Cart ID");
                System.out.println("4. Update Quantity");
                System.out.println("5. Delete Cart Item");
                System.out.println("6. Exit");

                System.out.print("Enter Choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                // =========================
                // ADD CART ITEM
                // =========================
                case 1:

                    CartItem item = new CartItem();

                    System.out.print("Enter Cart ID: ");
                    item.setCartId(sc.nextInt());

                    System.out.print("Enter Menu ID: ");
                    item.setMenuId(sc.nextInt());

                    System.out.print("Enter Quantity: ");
                    item.setQuantity(sc.nextInt());

                    dao.addCartItem(item);

                    System.out.println("✅ Cart Item Added Successfully");

                    break;

                // =========================
                // GET CART ITEM BY ID
                // =========================
                case 2:

                    System.out.print("Enter Cart Item ID: ");
                    int cartItemId = sc.nextInt();

                    CartItem c1 = dao.getCartItem(cartItemId);

                    if (c1 != null) {

                        System.out.println("\n===== CART ITEM DETAILS =====");
                        System.out.println("Cart Item ID : " + c1.getCartItemId());
                        System.out.println("Cart ID      : " + c1.getCartId());
                        System.out.println("Menu ID      : " + c1.getMenuId());
                        System.out.println("Quantity     : " + c1.getQuantity());

                    } else {

                        System.out.println("❌ Cart Item Not Found");
                    }

                    break;

                // =========================
                // VIEW ITEMS BY CART ID
                // =========================
                case 3:

                    System.out.print("Enter Cart ID: ");
                    int cartId = sc.nextInt();

                    List<CartItem> items = dao.getItemsByCartId(cartId);

                    System.out.println("\n===== CART ITEMS =====");

                    if (items.isEmpty()) {

                        System.out.println("No Items Found");

                    } else {

                        for (CartItem c : items) {

                            System.out.println(
                                    c.getCartItemId() + " | "
                                    + c.getCartId() + " | "
                                    + c.getMenuId() + " | "
                                    + c.getQuantity());
                        }
                    }

                    break;

                // =========================
                // UPDATE QUANTITY
                // =========================
                case 4:

                    System.out.print("Enter Cart Item ID: ");
                    int updateId = sc.nextInt();

                    System.out.print("Enter New Quantity: ");
                    int qty = sc.nextInt();

                    dao.updateQuantity(updateId, qty);

                    System.out.println("✅ Quantity Updated");

                    break;

                // =========================
                // DELETE CART ITEM
                // =========================
                case 5:

                    System.out.print("Enter Cart Item ID: ");
                    int deleteId = sc.nextInt();

                    dao.deleteCartItem(deleteId);

                    System.out.println("❌ Cart Item Deleted");

                    break;

                // =========================
                // EXIT
                // =========================
                case 6:

                    System.out.println("Thank You!");
                    sc.close();
                    System.exit(0);

                    break;

                default:

                    System.out.println("❌ Invalid Choice");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}