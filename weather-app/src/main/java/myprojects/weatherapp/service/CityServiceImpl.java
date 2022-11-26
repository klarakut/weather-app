package myprojects.weatherapp.service;

import lombok.extern.slf4j.Slf4j;
import myprojects.weatherapp.exceptions.CityNotFoundException;
import myprojects.weatherapp.models.City;
import myprojects.weatherapp.models.User;
import myprojects.weatherapp.models.dto.CityResponseDto;
import myprojects.weatherapp.models.retrofitDto.GeoRequestDto;
import myprojects.weatherapp.models.retrofitDto.GeoResponseDto;
import myprojects.weatherapp.models.retrofitDto.WeatherRequestDto;
import myprojects.weatherapp.models.retrofitDto.WeatherResponseDto;
import myprojects.weatherapp.repositories.CityRepo;
import myprojects.weatherapp.service.retrofit.GeoRetrofit;
import myprojects.weatherapp.service.retrofit.WeatherRetrofit;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


@Service
@Transactional
@Slf4j
public class CityServiceImpl implements CityService {


    private static final String appid1 = "#";
    private final CityRepo cityRepository;
    private final GeoRetrofit geoRetrofit;
    private final WeatherRetrofit weatherRetrofit;

    public CityServiceImpl(CityRepo cityRepository, GeoRetrofit geoRetrofit, WeatherRetrofit weatherRetrofit) {
        this.cityRepository = cityRepository;
        this.geoRetrofit = geoRetrofit;
        this.weatherRetrofit = weatherRetrofit;
    }
    @Override
    public Collection<CityResponseDto> showMyCityByUser(User user) {
        Collection<City> cities = user.getCities();
        Collection<CityResponseDto> response = new HashSet<>();
        for (City c : cities){
            CityResponseDto city = new CityResponseDto(c.getName(),c.getWeather());
            response.add(city);
        }
        return response;
    }

    @Override
    public void addCity(User user, City city) {
            cityRepository.save(city);
        user.addCity(city);
        log.info("Adding city {} to user {}", city, user);
    }

    @Override
    public boolean checkUnique(User user, City city){
        Collection<City> cities = user.getCities();
        for (City c : cities){
            if(c.getName().equals(city.getName())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateCity(User user, City city){
        City wantedCity = findCity(city.getName(),user);
        WeatherResponseDto responseDto = getWantedCityWeather(wantedCity.getName(),appid1);
        String weather = responseDto.main.getTemp();
        wantedCity.setWeather(weather);
        cityRepository.save(wantedCity);
    }

    @Override
    public void removeCityFromList(String city, User user) {
        //try{
            Collection<City> cities = user.getCities();
            City wantedCity = findCity(city,user);
            if (wantedCity==null){
                throw new CityNotFoundException();
            } else {
                cities.remove(wantedCity);
            }
        /*} catch (Exception e){
            throw new CityNotFoundException("Sorry, city not found");
        }*/
    }

    public City findCity(String city, User user){
        Collection<City> cities = user.getCities();
        for (City c : cities){
            if (c.getName().equals(city)){
                return c;
            /*}
            else {
                throw new CityNotFoundException();*/
            }
        }
        return null;
    }

    @Override
    public WeatherResponseDto getWantedCityWeather(String q, String appid){
        try {
            GeoRequestDto requestGeoDto = new GeoRequestDto(q,appid);
            List<GeoResponseDto> responseGeoDto = geoRetrofit.findCity(requestGeoDto);
            String name = responseGeoDto.get(0).getName();
            String lat = responseGeoDto.get(0).getLat();
            String lon = responseGeoDto.get(0).getLon();

            WeatherRequestDto requestWeatherDto = new WeatherRequestDto(lat,lon,appid);
            return weatherRetrofit.actualWeather(requestWeatherDto);
        } catch (Exception e){
            throw new CityNotFoundException();
        }
    }

    /*@Override
    public WeatherResponseDto getWantedCityWeather(String q, String appid) throws IOException {
        //try {
        GeoRequestDto requestGeoDto = new GeoRequestDto(q,appid);
        List<GeoResponseDto> responseGeoDto = geoRetrofit.findCity(requestGeoDto);
        String name = responseGeoDto.get(0).getName();
        String lat = responseGeoDto.get(0).getLat();
        String lon = responseGeoDto.get(0).getLon();

        WeatherRequestDto requestWeatherDto = new WeatherRequestDto(lat,lon,appid);
        return weatherRetrofit.actualWeather(requestWeatherDto); // WeatherResponseDto responseDto = weatherRetrofit.actualWeather(requestWeatherDto);
        /*} catch (Exception e){
            throw new CityNotFoundException();
        }
    }*/
}
