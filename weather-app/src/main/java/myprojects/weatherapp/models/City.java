package myprojects.weatherapp.models;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    //private String state;
    private String weather;

    //@Temporal(TemporalType.TIME)
    //@CreationTimestamp
    //@Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedAt;

    /*public City(String name, String state, String weather ){ //, Date lastUpdatedAt) {
        this.name = name;
        this.state = state;
        this.weather = weather;
        //setWeather(weather);
        //this.lastUpdatedAt = lastUpdatedAt;
    }*/
    public City(String name, String weather){ //, Date lastUpdatedAt) {
        this.name = name;
        //this.weather = weather;
        this.weather = setWeather(weather);
        //this.lastUpdatedAt = lastUpdatedAt;
    }

    public City() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getWeather() {
        return weather;
    }

    public String setWeather(String weather) {
        double w = Double.parseDouble(weather);
        w = w - 273.15;
        this.weather = String.valueOf(w);
        return weather;
    }

    //public void setState(String state) {this.state = state;}


    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Date lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
}
