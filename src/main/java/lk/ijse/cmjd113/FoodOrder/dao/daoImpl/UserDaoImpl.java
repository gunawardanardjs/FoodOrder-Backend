package lk.ijse.cmjd113.FoodOrder.dao.daoImpl;

import lk.ijse.cmjd113.FoodOrder.dao.UserDao;
import lk.ijse.cmjd113.FoodOrder.entity.UserEntity;
import lk.ijse.cmjd113.FoodOrder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDaoImpl implements UserDao {
    private final UserRepository userRepository;

    @Override
    public UserEntity save(UserEntity user) {
        log.debug("Saving user: {}", user.getEmail());
        return userRepository.save(user);
    }
    @Override
    public Optional<UserEntity> findById(String userId) { return userRepository.findById(userId); }
    @Override
    public Optional<UserEntity> findByEmail(String email) { return userRepository.findByEmail(email); }
    @Override
    public List<UserEntity> findAll() { return userRepository.findAll(); }
    @Override
    public void deleteById(String userId) { userRepository.deleteById(userId); }
    @Override
    public boolean existsByEmail(String email) { return userRepository.existsByEmail(email); }
}
