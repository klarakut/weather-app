package myprojects.weatherapp.models.dto;

public class CreateUserDto {
    public String name;
    public String username;
    //public String email;
    public String password;
    public String passwordAgain;

    /*public CreateUserDto(String username, String email, String password1, String password2) {
        this.username = username;
        this.email = email;
        this.password = password1;
        this.passwordAgain = password2;
    }*/

    public CreateUserDto(String name,String username, String password1, String password2) {
        this.name = name;
        this.username = username;
        this.password = password1;
        this.passwordAgain = password2;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }
}

