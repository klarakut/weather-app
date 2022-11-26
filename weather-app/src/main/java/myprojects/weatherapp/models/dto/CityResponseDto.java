package myprojects.weatherapp.models.dto;

import myprojects.weatherapp.models.City;

public class CityResponseDto implements ResponseDto{
    public String name;
    public String weather;

    public CityResponseDto(String name, String weather) {
        this.name = name;
        this.weather = weather;
    }

    public CityResponseDto(City city){
        this.name = city.getName();
        this.weather = city.getWeather();
    }
}
