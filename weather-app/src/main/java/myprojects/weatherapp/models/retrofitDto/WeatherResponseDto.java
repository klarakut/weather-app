package myprojects.weatherapp.models.retrofitDto;


import myprojects.weatherapp.models.dto.ResponseDto;

public class WeatherResponseDto implements ResponseDto {

    public Coord coord;
    //public Weather weather;
    //public Collection<List<String>> weather;
    public String base;
    public Main main;

    public WeatherResponseDto(Coord coord, String base, Main main){
        this.coord = coord;
        this.base = base;
        this.main = main;
    }

    public Coord getCoord() {
        return coord;
    }

   /*public Weather getWeather() {
        return weather;}*/


    public String getBase() {
        return base;
    }

    public Main getMain() {
        return main;
    }
}
