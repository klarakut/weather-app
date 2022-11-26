package myprojects.weatherapp.service.retrofit;

import myprojects.weatherapp.models.retrofitDto.WeatherRequestDto;
import myprojects.weatherapp.models.retrofitDto.WeatherResponseDto;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

@Service
public class WeatherRetrofit {
    public static final String nameOfApiUrl = "https://api.openweathermap.org";
    public static final String apiKey = "#";

    public WeatherResponseDto actualWeather(WeatherRequestDto requestDto) throws IOException{

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(nameOfApiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherRetroInterface weatherRetroInterface = retrofit.create(WeatherRetroInterface.class);
        Call<WeatherResponseDto> call = weatherRetroInterface.getWeather(requestDto.getLat(),requestDto.getLon(),requestDto.getAppid());
        WeatherResponseDto responseDto = call.execute().body();
         return responseDto;
    }
}
