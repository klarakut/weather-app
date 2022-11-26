package myprojects.weatherapp.service;

import myprojects.weatherapp.models.City;
import myprojects.weatherapp.models.User;
import myprojects.weatherapp.models.dto.CityResponseDto;
import myprojects.weatherapp.models.retrofitDto.WeatherResponseDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;

@Service
public interface CityService {
   Collection<CityResponseDto> showMyCityByUser (User user);
   void addCity(User user, City city);
   boolean checkUnique(User user, City city);
   void updateCity(User user, City city) throws IOException;
   void removeCityFromList(String city, User user);
   WeatherResponseDto getWantedCityWeather(String q, String appid) throws IOException;
   City findCity(String city, User user);
}
