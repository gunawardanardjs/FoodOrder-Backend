package lk.ijse.cmjd113.FoodOrder.service.serviceImpl;

import jakarta.transaction.Transactional;
import lk.ijse.cmjd113.FoodOrder.dao.UserDao;
import lk.ijse.cmjd113.FoodOrder.dto.response.UserDto;
import lk.ijse.cmjd113.FoodOrder.exception.DataNotFoundException;
import lk.ijse.cmjd113.FoodOrder.service.UserService;
import lk.ijse.cmjd113.FoodOrder.util.IDGenerator;
import lk.ijse.cmjd113.FoodOrder.util.MappingDtoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final MappingDtoEntity mappingDtoEntity;

    @Override
    public void saveUser(UserDto userDto) {
        userDto.setUserId(IDGenerator.userIDGenerator());
        userDao.save(mappingDtoEntity.toUserEntity(userDto));
    }

    @Override
    public UserDto getUserByID(String userId) {
        var foundUser = userDao.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found: " + userId));
        return mappingDtoEntity.toUserDto(foundUser);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return mappingDtoEntity.getUserDtoList(userDao.findAll());
    }

    @Override
    public UserDto getCurrentUser(String email) {
        var foundUser = userDao.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User not found: " + email));
        return mappingDtoEntity.toUserDto(foundUser);
    }

    @Override
    public void deleteUser(String userId) {
        userDao.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found: " + userId));
        userDao.deleteById(userId);
        log.info("User deleted: {}", userId);
    }

    @Override
    public void updateUser(String userId, UserDto userDto) {
        var foundUser = userDao.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found: " + userId));

        // Mutate the FOUND entity — don't replace it
        if (userDto.getName() != null)
            foundUser.setName(userDto.getName());
        if (userDto.getEmail() != null)
            foundUser.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null)
            foundUser.setPassword(userDto.getPassword());
        if (userDto.getPhone() != null)
            foundUser.setPhone(userDto.getPhone());
        if (userDto.getAddress() != null)
            foundUser.setAddress(userDto.getAddress());
        if (userDto.getRole() != null)
            foundUser.setRole(userDto.getRole());

        userDao.save(foundUser);
    }
}
