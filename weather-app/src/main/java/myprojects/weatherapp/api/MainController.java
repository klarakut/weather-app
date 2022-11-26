package myprojects.weatherapp.api;

import myprojects.weatherapp.models.City;
import myprojects.weatherapp.models.User;
import myprojects.weatherapp.models.dto.CityResponseDto;
import myprojects.weatherapp.models.dto.ResponseDto;
import myprojects.weatherapp.models.dto.StatusResponseDto;
import myprojects.weatherapp.models.retrofitDto.WeatherResponseDto;
import myprojects.weatherapp.service.CityService;
import myprojects.weatherapp.service.UserService;
import myprojects.weatherapp.service.retrofit.GeoRetrofit;
import myprojects.weatherapp.service.retrofit.WeatherRetrofit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/api")
public class MainController {

    private static final String appid = "#";
    private final WeatherRetrofit weatherRetrofit;
    private final GeoRetrofit geoRetrofit;
    private final CityService cityService;
    private final UserService userService;

    public MainController(WeatherRetrofit weatherRetrofit, GeoRetrofit geoRetrofit, CityService cityService, UserService userService) {
        this.weatherRetrofit = weatherRetrofit;
        this.geoRetrofit = geoRetrofit;
        this.cityService = cityService;
        this.userService = userService;
    }

   /* @GetMapping("/w")
    public WeatherResponseDto showMeWeather(@RequestParam(value = "lat") String lat,
                                            @RequestParam (value = "lon") String lon,
                                            @RequestParam(value = "appid") String appid) throws IOException, IOException {

        WeatherRequestDto requestDto = new WeatherRequestDto(lat,lon,appid);
        WeatherResponseDto responseDto = weatherRetrofit.actualWeather(requestDto);
        return responseDto;
    }

    @GetMapping("/city")
    public List<GeoResponseDto> showMeCity(@RequestParam(value = "q") String q,
                                           @RequestParam(value = "appid") String appid) throws IOException {

        GeoRequestDto requestDto = new GeoRequestDto(q,appid);
        List<GeoResponseDto> responseDto = geoRetrofit.findCity(requestDto);
        return responseDto;
    }

   @GetMapping("/city?appid=" + appid)
    public List<GeoResponseDto> showMeCity(@RequestParam(value = "q") String q) throws IOException {
        GeoRequestDto requestDto = new GeoRequestDto(q);//,appid);
        List<GeoResponseDto> responseDto = geoRetrofit.findCity(requestDto);
        return responseDto;
    }*/

    @GetMapping("/showMeWeather")
    public ResponseEntity<? extends ResponseDto> showMe(@RequestParam (value = "q")String q) throws IOException{
        WeatherResponseDto responseDto = cityService.getWantedCityWeather(q,appid);
        String weather = responseDto.main.getTemp();
        StatusResponseDto statusResponseDto = new StatusResponseDto("Temperature in " + q + " is " + weather + " degree");
        return new ResponseEntity<>(statusResponseDto, HttpStatus.OK);
    }

    @GetMapping("/user/addCity")
    public ResponseEntity<Collection<City>>addCity(@RequestParam (value = "q")String wantedCity, HttpServletRequest request, HttpServletResponse response) throws IOException{
        WeatherResponseDto responseDto = cityService.getWantedCityWeather(wantedCity,appid);
        String weather = responseDto.main.getTemp();

        City city = new City(wantedCity,weather);
        User user = userService.getUserFromToken(request);

        boolean duplicatedCity = cityService.checkUnique(user,city);
        if (!duplicatedCity) {
            cityService.addCity(user, city);
        } else {
            cityService.updateCity(user, city);
        }
        Collection<City> cities = user.getCities();
        return new ResponseEntity<>(cities,HttpStatus.OK);
    }

    @GetMapping("/user/myCities")
    public ResponseEntity<Collection<CityResponseDto>> showMyCities(HttpServletRequest request, HttpServletResponse response){
        User user = userService.getUserFromToken(request);

        Collection<CityResponseDto> responseDtos = cityService.showMyCityByUser(user);
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @PostMapping("/user/removeCity")
    public ResponseEntity<? extends ResponseDto> removeCityFromList(@RequestParam(value = "city") String city, HttpServletRequest request){
        User user = userService.getUserFromToken(request);

        cityService.removeCityFromList(city, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/user/updateCity")
    public ResponseEntity<? extends ResponseDto> updateCity(@RequestParam(value = "city") String city, HttpServletRequest request) throws IOException {
        User user = userService.getUserFromToken(request);
        City wantedCity = cityService.findCity(city,user);

        cityService.updateCity(user,wantedCity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
