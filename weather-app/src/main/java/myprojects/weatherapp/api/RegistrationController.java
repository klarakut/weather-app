package myprojects.weatherapp.api;

import myprojects.weatherapp.models.dto.CreateUserDto;
import myprojects.weatherapp.models.dto.ResponseDto;
import myprojects.weatherapp.models.dto.UserResponseDto;
import myprojects.weatherapp.service.RoleService;
import myprojects.weatherapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RegistrationController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("/registration")
    public ResponseEntity<? extends ResponseDto> registration(@RequestBody CreateUserDto dto){
        UserResponseDto userResponse = userService.registerUser(dto);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }
}
