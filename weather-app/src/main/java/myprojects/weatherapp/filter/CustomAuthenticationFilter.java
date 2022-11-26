package myprojects.weatherapp.filter;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("Username is: {}",username);
        log.info("Password is: {}",password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal(); // getPrincipal() returning the object
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String access_token = com.auth0.jwt.JWT.create()
                .withSubject(user.getUsername())      // the subject can be really any string that you want (like user ID or username or anything unique)
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))              // expiration -> for example ten minute
                .withIssuer(request.getRequestURL().toString())        // issuer -> for example the of the company or the author of this token
                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);


        String refresh_token = com.auth0.jwt.JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))              // expiration -> here I can put longer time, for example a week or a month
                .withIssuer(request.getRequestURL().toString())        // issuer -> for example the of the company or the author of this token
                .sign(algorithm);

            response.setHeader("access_token",access_token);
            response.setHeader("refresh_token",refresh_token);
            response.setContentType(APPLICATION_JSON_VALUE);
            String greeting = "Hi there " +  user.getUsername() +"!";
            new ObjectMapper().writeValue(response.getOutputStream(),greeting);
    }
}
