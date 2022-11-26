package myprojects.weatherapp.service.retrofit;

import myprojects.weatherapp.models.retrofitDto.GeoRequestDto;
import myprojects.weatherapp.models.retrofitDto.GeoResponseDto;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

@Service
public class GeoRetrofit {

    public static final String nameOfApiUrl = "http://api.openweathermap.org";
    public static final String apiKey = "#";

    public List<GeoResponseDto> findCity(GeoRequestDto requestDto) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(nameOfApiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GeoRetroInterface geoRetroInterface = retrofit.create(GeoRetroInterface.class);
        Call<List<GeoResponseDto>> call = geoRetroInterface.getMyCity(requestDto.getQ(),requestDto.getAppid());
        List<GeoResponseDto> responseDto = call.execute().body();
        return responseDto;
    }
}
