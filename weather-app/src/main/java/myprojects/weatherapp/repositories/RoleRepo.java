package myprojects.weatherapp.repositories;

import myprojects.weatherapp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {
    Role findRoleByName(String name);
}
