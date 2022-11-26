package myprojects.weatherapp.api;

import myprojects.weatherapp.exceptions.CityNotFoundException;
import myprojects.weatherapp.exceptions.InvalidPasswordException;
import myprojects.weatherapp.exceptions.NameMissingException;
import myprojects.weatherapp.exceptions.NotMatchingPasswords;
import myprojects.weatherapp.exceptions.PasswordTooShortException;
import myprojects.weatherapp.exceptions.UserNotFoundException;
import myprojects.weatherapp.exceptions.UsernameAlreadyTakenException;
import myprojects.weatherapp.models.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleCityNotFoundException() {
        return new ErrorResponseDto("City not found");
    }

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleUsernameTakenException() {
        return new ErrorResponseDto("Username already taken");
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleUserNotFoundException() {
        return new ErrorResponseDto("User not found");
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleInvalidPasswordException() {
        return new ErrorResponseDto("Invalid password!");
    }

    @ExceptionHandler(PasswordTooShortException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handlePasswordTooShortException() {
        return new ErrorResponseDto("Password must be at least 8 characters long");
    }

    @ExceptionHandler(NameMissingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleNameMissingException() {
        return new ErrorResponseDto("Name is required");
    }

    @ExceptionHandler(NotMatchingPasswords.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleNotMatchingPasswords() {
        return new ErrorResponseDto("Not matching passwords");
    }
}
