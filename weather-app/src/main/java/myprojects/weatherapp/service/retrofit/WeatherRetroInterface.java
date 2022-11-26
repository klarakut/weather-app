package myprojects.weatherapp.service.retrofit;

import myprojects.weatherapp.models.retrofitDto.WeatherResponseDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherRetroInterface {

    @GET("/data/2.5/weather")
    Call<WeatherResponseDto> getWeather(@Query("lat") String lat,
                                        @Query("lon") String lon,
                                        @Query("appid") String appid);
}
