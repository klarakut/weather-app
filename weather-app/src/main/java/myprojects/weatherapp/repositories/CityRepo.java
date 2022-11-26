package myprojects.weatherapp.repositories;

import myprojects.weatherapp.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepo extends JpaRepository<City,Long> {
    Optional<City> findCityByName(String name);
}
