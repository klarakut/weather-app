package myprojects.weatherapp.models.dto;

public class LoginRequestDto {
        public String email;
        public String password;

    public LoginRequestDto(String email, String password) {
            this.email = email;
            this.password = password;
        }
    public String getEmail() {
            return email;
        }

    public String getPassword() {
            return password;
        }
}
