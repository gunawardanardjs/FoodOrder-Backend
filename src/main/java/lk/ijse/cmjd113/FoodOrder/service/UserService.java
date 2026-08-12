package lk.ijse.cmjd113.FoodOrder.service;

import lk.ijse.cmjd113.FoodOrder.dto.response.UserDto;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    UserDto getUserByID(String userId);
    List<UserDto> getAllUsers();
    UserDto getCurrentUser(String email);
    void deleteUser(String userId);
    void updateUser(String userId, UserDto userDto);
}
