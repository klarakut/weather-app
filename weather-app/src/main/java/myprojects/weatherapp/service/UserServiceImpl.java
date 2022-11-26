package myprojects.weatherapp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import myprojects.weatherapp.exceptions.NameMissingException;
import myprojects.weatherapp.exceptions.NotMatchingPasswords;
import myprojects.weatherapp.exceptions.PasswordMissingException;
import myprojects.weatherapp.exceptions.ShortPasswordException;
import myprojects.weatherapp.exceptions.ShortUsernameException;
import myprojects.weatherapp.exceptions.UsernameAlreadyTakenException;
import myprojects.weatherapp.exceptions.UsernameMissingException;
import myprojects.weatherapp.models.Role;
import myprojects.weatherapp.models.User;
import myprojects.weatherapp.models.dto.CreateUserDto;
import myprojects.weatherapp.models.dto.UserResponseDto;
import myprojects.weatherapp.repositories.RoleRepo;
import myprojects.weatherapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private static final String usersRole = "ROLE_USER";

    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database",user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

   /* @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database",role.getName());
        return roleRepo.save(role);
    }*/

    @Override
    public UserResponseDto registerUser(CreateUserDto dto){
        if (dto.username.isEmpty()) {
            throw new UsernameMissingException();
        }
        if (dto.password.isEmpty()) {
            throw new PasswordMissingException();
        }
        if (dto.name.isEmpty()) {
            throw new NameMissingException();
        }

        if (!dto.password.equals(dto.passwordAgain)){
            throw new NotMatchingPasswords();
        }

        boolean usernameExist = userRepo.findByUsername(dto.username).isPresent();
        if (usernameExist) {
            throw new UsernameAlreadyTakenException();
        }

        if (dto.username.length() < 4) {
            throw new ShortUsernameException();
        }

        if (dto.password.length() < 8) {
            throw new ShortPasswordException();
        }

        User user = new User(dto);
        Role role = roleRepo.findRoleByName(usersRole);
        user.getRoles().add(role);

        log.info("Saving new user {} to the database",user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        return new UserResponseDto(user.getUsername());
    }


    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}",roleName,username);
        User user = userRepo.findByUsername(username).get();
        Role role = roleRepo.findRoleByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user {}",username);
        return userRepo.findByUsername(username).get();
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }

    @Override
    public User getUserFromToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String access_token = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(access_token);
        String username = decodedJWT.getSubject();
        return getUser(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).get();
        if(user == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.error("User found in the database: {}",username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
