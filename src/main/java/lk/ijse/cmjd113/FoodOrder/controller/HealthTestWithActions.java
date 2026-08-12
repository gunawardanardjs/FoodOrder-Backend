package lk.ijse.cmjd113.FoodOrder.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health-test")
public class HealthTestWithActions {
    @GetMapping
    public String healthTest() {
        return "Food Order System is running - V 1.0.0";
    }
}
