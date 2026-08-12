package lk.ijse.cmjd113.FoodOrder.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd ' | ' HH:mm:ss");

    // Current Time
    public static LocalDateTime currentDateTime() {
        return LocalDateTime.now();
    }

    // Format
    public static String currentDateTimeString() {
        return currentDateTime().format(FORMAT);
    }
}
