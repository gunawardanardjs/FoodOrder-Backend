package lk.ijse.cmjd113.FoodOrder.config;

import lk.ijse.cmjd113.FoodOrder.dao.CartDao;
import lk.ijse.cmjd113.FoodOrder.dao.UserDao;
import lk.ijse.cmjd113.FoodOrder.entity.CartEntity;
import lk.ijse.cmjd113.FoodOrder.entity.UserEntity;
import lk.ijse.cmjd113.FoodOrder.entity.enums.Role;
import lk.ijse.cmjd113.FoodOrder.util.IDGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Runs once on startup.
 * Default admin credentials:
 *   Email    : admin@foodorder.lk
 *   Password : Admin@1234
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserDao userDao;
    private final CartDao cartDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        createAdminIfNotExists();
    }

    private void createAdminIfNotExists() {
        final String ADMIN_EMAIL = "admin@foodorder.lk";

        if (userDao.existsByEmail(ADMIN_EMAIL)) {
            log.info("Admin account already exists — skipping creation.");
            return;
        }

        // Create admin user
        UserEntity admin = new UserEntity();
        admin.setUserId(IDGenerator.userIDGenerator());
        admin.setName("System Admin");
        admin.setEmail(ADMIN_EMAIL);
        admin.setPassword(passwordEncoder.encode("Admin@1234")); // hashed — never stored as plain text
        admin.setPhone("+94 77 000 0000");
        admin.setAddress("IJSE, Panadura, Sri Lanka");
        admin.setRole(Role.ADMIN);

        UserEntity savedAdmin = userDao.save(admin);

        // Create an empty cart for admin (required by FK constraint)
        CartEntity cart = new CartEntity();
        cart.setUser(savedAdmin);
        cart.setCartItems(new ArrayList<>());
        cart.setTotalPrice(BigDecimal.ZERO);
        cartDao.save(cart);

        log.info("========================================================");
        log.info("  DEFAULT ADMIN ACCOUNT CREATED");
        log.info("  Email    : {}", ADMIN_EMAIL);
        log.info("  Password : Admin@1234");
        log.info("  Role     : ADMIN");
        log.info("  IMPORTANT: Change this password after first login!");
        log.info("========================================================");
    }
}
