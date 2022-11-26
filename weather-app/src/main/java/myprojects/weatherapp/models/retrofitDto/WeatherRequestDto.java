package myprojects.weatherapp.models.retrofitDto;

public class WeatherRequestDto {
    String lat;
    String lon;
    String appid;

    public WeatherRequestDto(String lat, String lon, String appid) {
        this.lat = lat;
        this.lon = lon;
        this.appid = appid;
    }

    public String getAppid() {
        return appid;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}
