package com.designpattern.utility;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.designpattern.DAoImpl.OrderItemDAOImpl;
import com.designpattern.dao.OrderItemDAO;
import com.designpattern.model.OrderItem;

public class OrderItemTest {

    public static void main(String[] args) {

        try {

            Scanner sc = new Scanner(System.in);
            Connection conn = DBConnection.getConnection();

            OrderItemDAO dao = new OrderItemDAOImpl(conn);

            while (true) {

                System.out.println("\n===== ORDER ITEM MENU =====");
                System.out.println("1. Add Order Item");
                System.out.println("2. View Items By Order ID");
                System.out.println("3. Delete Order Item");
                System.out.println("4. Exit");

                System.out.print("Enter Choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    // =========================
                    // ADD ORDER ITEM
                    // =========================
                    case 1:

                        OrderItem item = new OrderItem();

                        System.out.print("Enter Order ID: ");
                        item.setOrderId(sc.nextInt());

                        System.out.print("Enter Menu ID: ");
                        item.setMenuId(sc.nextInt());

                        System.out.print("Enter Quantity: ");
                        int qty = sc.nextInt();

                        System.out.print("Enter Price per item: ");
                        double price = sc.nextDouble();
                        sc.nextLine();

                        double total = qty * price;

                        item.setQuantity(qty);
                        item.setItemTotal(total);

                        dao.addOrderItem(item);

                        System.out.println("✅ Order Item Added (Total = " + total + ")");
                        break;

                    // =========================
                    // VIEW BY ORDER ID
                    // =========================
                    case 2:

                        System.out.print("Enter Order ID: ");
                        int orderId = sc.nextInt();

                        List<OrderItem> list = dao.getItemsByOrderId(orderId);

                        System.out.println("\n===== ORDER ITEMS =====");

                        if (list.isEmpty()) {
                            System.out.println("No items found");
                        } else {

                            double grandTotal = 0;

                            for (OrderItem i : list) {

                                System.out.println(
                                        i.getOrderItemId() + " | "
                                        + i.getMenuId() + " | "
                                        + i.getQuantity() + " | "
                                        + i.getItemTotal()
                                );

                                grandTotal += i.getItemTotal();
                            }

                            System.out.println("----------------------");
                            System.out.println("TOTAL BILL = " + grandTotal);
                        }

                        break;

                    // =========================
                    // DELETE ITEM
                    // =========================
                    case 3:

                        System.out.print("Enter Order Item ID: ");
                        int id = sc.nextInt();

                        dao.deleteOrderItem(id);

                        System.out.println("❌ Order Item Deleted");
                        break;

                    // =========================
                    // EXIT
                    // =========================
                    case 4:

                        System.out.println("Thank You!");
                        sc.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid Choice");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}