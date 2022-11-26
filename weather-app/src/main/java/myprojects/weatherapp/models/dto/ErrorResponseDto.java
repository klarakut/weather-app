package myprojects.weatherapp.models.dto;

public class ErrorResponseDto implements ResponseDto {
    public final String status;

    public ErrorResponseDto(String status) {
        this.status = status;
    }
}

