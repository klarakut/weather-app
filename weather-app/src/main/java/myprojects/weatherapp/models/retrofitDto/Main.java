package myprojects.weatherapp.models.retrofitDto;

public class Main {
    public String temp;

    public Main(String temp) {
        setTemp(temp);
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        double weather = Double.parseDouble(temp);
        weather = weather - 273.15;
        this.temp = String.valueOf(weather);
    }
}
