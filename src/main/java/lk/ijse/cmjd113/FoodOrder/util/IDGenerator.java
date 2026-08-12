package lk.ijse.cmjd113.FoodOrder.util;

import java.util.UUID;

public class IDGenerator {
    // User
    public static String userIDGenerator(){
        return "USR-" + UUID.randomUUID();
    }

    // Food Item
    public static String foodIDGenerator(){
        return "FOD-"+ UUID.randomUUID();
    }

    // Category
    public static String categoryIDGenerator(){
        return "CAT-"+ UUID.randomUUID();
    }

    // Order
    public static String orderIDGenerator(){
        return "ORD-"+ UUID.randomUUID();
    }

    // Payment
    public static String paymentIDGenerator(){
        return "PMT-"+ UUID.randomUUID();
    }
}
