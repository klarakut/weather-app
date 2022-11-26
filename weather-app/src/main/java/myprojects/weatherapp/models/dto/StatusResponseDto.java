package myprojects.weatherapp.models.dto;

public class StatusResponseDto implements ResponseDto{
        public String status;

    public StatusResponseDto(String status) {
            this.status = status;
        }

    public String getStatus() {
            return status;
        }
}
