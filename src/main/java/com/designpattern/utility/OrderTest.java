package com.designpattern.utility;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.designpattern.DAoImpl.OrderDAOImpl;
import com.designpattern.dao.OrderDAO;
import com.designpattern.model.Order;

public class OrderTest {

    public static void main(String[] args) {

        try {

            Scanner sc = new Scanner(System.in);
            Connection conn = DBConnection.getConnection();

            OrderDAO dao = new OrderDAOImpl(conn);

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

                    System.out.println("\n===== ADMIN ORDER MENU =====");
                    System.out.println("1. View All Orders");
                    System.out.println("2. View Orders By Restaurant");
                    System.out.println("3. Update Order Status");
                    System.out.println("4. Delete Order");
                    System.out.println("5. Exit");

                    System.out.print("Enter Choice: ");
                    int choice = sc.nextInt();
                    sc.nextLine();

                    switch (choice) {

                    case 1:

                        List<Order> allOrders = dao.getAllOrders();

                        System.out.println("\n===== ALL ORDERS =====");

                        for (Order o : allOrders) {

                            System.out.println(
                                    o.getOrderId() + " | "
                                            + o.getUserId() + " | "
                                            + o.getRestaurantId() + " | "
                                            + o.getTotalAmount() + " | "
                                            + o.getStatus());
                        }

                        break;

                    case 2:

                        System.out.print("Enter Restaurant ID: ");
                        int rid = sc.nextInt();

                        List<Order> restOrders = dao.getOrdersByRestaurant(rid);

                        System.out.println("\n===== RESTAURANT ORDERS =====");

                        for (Order o : restOrders) {

                            System.out.println(
                                    o.getOrderId() + " | "
                                            + o.getUserId() + " | "
                                            + o.getTotalAmount() + " | "
                                            + o.getStatus());
                        }

                        break;

                    case 3:

                        System.out.print("Enter Order ID: ");
                        int oid = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Status (Pending/Confirmed/Preparing/Delivered): ");
                        String status = sc.nextLine();

                        dao.updateStatus(oid, status);

                        System.out.println("✅ Status Updated Successfully");

                        break;

                    case 4:

                        System.out.print("Enter Order ID to Delete: ");
                        int delId = sc.nextInt();

                        dao.deleteOrder(delId);

                        System.out.println("❌ Order Deleted Successfully");

                        break;

                    case 5:

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

                    System.out.println("\n===== CUSTOMER ORDER MENU =====");
                    System.out.println("1. Create Order");
                    System.out.println("2. Get Order By ID");
                    System.out.println("3. Get My Orders");
                    System.out.println("4. Exit");

                    System.out.print("Enter Choice: ");
                    int choice = sc.nextInt();
                    sc.nextLine();

                    switch (choice) {

                    case 1:

                        Order o = new Order();

                        System.out.print("Enter User ID: ");
                        o.setUserId(sc.nextInt());

                        System.out.print("Enter Restaurant ID: ");
                        o.setRestaurantId(sc.nextInt());
                        sc.nextLine();

                        System.out.print("Enter Payment Method (COD/UPI/Card): ");
                        o.setPaymentMethod(sc.nextLine());

                        o.setTotalAmount(0);
                        o.setStatus("Pending");

                        int orderId = dao.createOrder(o);

                        System.out.println("✅ Order Created Successfully");
                        System.out.println("Generated Order ID = " + orderId);

                        break;

                    case 2:

                        System.out.print("Enter Order ID: ");
                        int id = sc.nextInt();

                        Order order = dao.getOrderById(id);

                        if (order != null) {

                            System.out.println("\n===== ORDER DETAILS =====");
                            System.out.println("Order ID       : " + order.getOrderId());
                            System.out.println("User ID        : " + order.getUserId());
                            System.out.println("Restaurant ID  : " + order.getRestaurantId());
                            System.out.println("Total Amount   : " + order.getTotalAmount());
                            System.out.println("Status         : " + order.getStatus());
                            System.out.println("Payment Method : " + order.getPaymentMethod());
                            System.out.println("Order Date     : " + order.getOrderDate());

                        } else {

                            System.out.println("❌ Order Not Found");
                        }

                        break;

                    case 3:

                        System.out.print("Enter User ID: ");
                        int userId = sc.nextInt();

                        List<Order> userOrders = dao.getOrdersByUser(userId);

                        System.out.println("\n===== MY ORDERS =====");

                        if (userOrders.isEmpty()) {

                            System.out.println("No Orders Found");

                        } else {

                            for (Order ord : userOrders) {

                                System.out.println(
                                        ord.getOrderId() + " | "
                                                + ord.getRestaurantId() + " | "
                                                + ord.getTotalAmount() + " | "
                                                + ord.getStatus());
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