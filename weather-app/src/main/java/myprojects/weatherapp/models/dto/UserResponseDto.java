package myprojects.weatherapp.models.dto;

public class UserResponseDto implements ResponseDto{
        public String username;

    public UserResponseDto(String username) {
            this.username = username;
        }
}
