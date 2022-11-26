package myprojects.weatherapp;

import myprojects.weatherapp.models.Role;
import myprojects.weatherapp.models.User;
import myprojects.weatherapp.service.RoleService;
import myprojects.weatherapp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class WeatherAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherAppApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	/*
	@Bean
	CommandLineRunner run (UserService userService, RoleService roleService){
        return args -> {

        // creating roles:
            roleService.saveRole(new Role("ROLE_USER"));
            roleService.saveRole(new Role("ROLE_MANAGER"));
            roleService.saveRole(new Role("ROLE_ADMIN"));
            roleService.saveRole(new Role("ROLE_SUPER_ADMIN"));

            //userService.saveUser(new User("John","john","john@mail.com","1234", new ArrayList<>()));
            //userService.saveUser(new User("Will","will","will@mail.com","1234", new ArrayList<>()));
            userService.saveUser(new User("Alex","alex","1234", new ArrayList<>()));
            // userService.saveUser(new User("Will S","will","1234", new ArrayList<>()));
            userService.saveUser(new User("Samantha","sam","1234", new ArrayList<>()));

            //userService.addRoleToUser("john","ROLE_USER");
            userService.addRoleToUser("alex","ROLE_ADMIN");
            userService.addRoleToUser("sam","ROLE_USER");
        };
    }*/

}
