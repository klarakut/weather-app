package myprojects.weatherapp.service.retrofit;

import myprojects.weatherapp.models.retrofitDto.GeoResponseDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface GeoRetroInterface {

    @GET("/geo/1.0/direct")
    Call<List<GeoResponseDto>> getMyCity(@Query("q") String q,
                                         //@Query("lon") String lon,
                                         @Query("appid") String appid);
}
