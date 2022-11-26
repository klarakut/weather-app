package myprojects.weatherapp.models.retrofitDto;

public class GeoRequestDto {
    public String q;
    public String appid;

    public GeoRequestDto(String q, String appid) {
        this.q = q;
        this.appid = appid;
    }

    public GeoRequestDto(String q) {
        this.q = q;
    }

    public String getQ() {
        return q;
    }

    public String getAppid() {return appid;}
}
