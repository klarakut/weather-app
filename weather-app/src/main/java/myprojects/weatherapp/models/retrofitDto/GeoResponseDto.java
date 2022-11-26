package myprojects.weatherapp.models.retrofitDto;

public class GeoResponseDto {
    public String name;

    public String lat;
    public String lon;

    public GeoResponseDto(String name, String lat, String lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}
