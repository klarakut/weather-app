package myprojects.weatherapp.service;

import myprojects.weatherapp.models.User;
import myprojects.weatherapp.models.dto.CreateUserDto;
import myprojects.weatherapp.models.dto.UserResponseDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    //Role saveRole(Role role);
    void addRoleToUser(String username, String name);
    User getUser(String username);
    List<User> getUsers();

    User getUserFromToken(HttpServletRequest request);

    UserResponseDto registerUser(CreateUserDto createUserDto);
}
