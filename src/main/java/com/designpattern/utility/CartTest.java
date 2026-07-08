package com.designpattern.utility;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.designpattern.DAoImpl.CartDAOImpl;
import com.designpattern.dao.CartDAO;
import com.designpattern.model.Cart;

public class CartTest {

    public static void main(String[] args) {

        try {

            Scanner sc = new Scanner(System.in);

            Connection conn = DBConnection.getConnection();

            CartDAO dao = new CartDAOImpl(conn);

            while (true) {

                System.out.println("\n===== CART MENU =====");
                System.out.println("1. Add Cart");
                System.out.println("2. Get Cart By ID");
                System.out.println("3. Get Cart By User ID");
                System.out.println("4. Get All Carts");
                System.out.println("5. Delete Cart");
                System.out.println("6. Exit");

                System.out.print("Enter Choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

               
             // =========================
             // ADD CART
             // =========================
             case 1:

                 System.out.print("Enter User ID: ");
                 int userId = sc.nextInt();

                 Cart existingCart = dao.getCartByUserId(userId);

                 if (existingCart != null) {

                     System.out.println("❌ User already has a cart");
                     System.out.println("Cart ID    : " + existingCart.getCartId());
                     System.out.println("Created At : " + existingCart.getCreatedAt());

                 } else {

                     Cart cart = new Cart();

                     cart.setUserId(userId);

                     dao.addCart(cart);

                     System.out.println("✅ Cart Added Successfully");
                 }

                 break;

                // =========================
                // GET CART BY ID
                // =========================
                case 2:

                    System.out.print("Enter Cart ID: ");
                    int cartId = sc.nextInt();

                    Cart c1 = dao.getCart(cartId);

                    if (c1 != null) {

                        System.out.println("\n===== CART DETAILS =====");
                        System.out.println("Cart ID    : " + c1.getCartId());
                        System.out.println("User ID    : " + c1.getUserId());
                        System.out.println("Created At : " + c1.getCreatedAt());

                    } else {

                        System.out.println("❌ Cart Not Found");
                    }

                    break;

                // =========================
                // GET CART BY USER ID
                // =========================
                case 3:

                    System.out.print("Enter User ID: ");
                    int userId1 = sc.nextInt();

                    Cart c2 = dao.getCartByUserId(userId1);

                    if (c2 != null) {

                        System.out.println("\n===== USER CART =====");
                        System.out.println("Cart ID    : " + c2.getCartId());
                        System.out.println("User ID    : " + c2.getUserId());
                        System.out.println("Created At : " + c2.getCreatedAt());

                    } else {

                        System.out.println("❌ No Cart Found For User");
                    }

                    break;

                // =========================
                // GET ALL CARTS
                // =========================
                case 4:

                    List<Cart> carts = dao.getAllCarts();

                    System.out.println("\n===== ALL CARTS =====");

                    if (carts.isEmpty()) {

                        System.out.println("No Carts Found");

                    } else {

                        for (Cart c : carts) {

                            System.out.println(
                                    c.getCartId() + " | "
                                    + c.getUserId() + " | "
                                    + c.getCreatedAt());
                        }
                    }

                    break;

                // =========================
                // DELETE CART
                // =========================
                case 5:

                    System.out.print("Enter Cart ID to Delete: ");
                    int deleteId = sc.nextInt();

                    dao.deleteCart(deleteId);

                    System.out.println("❌ Cart Deleted Successfully");

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