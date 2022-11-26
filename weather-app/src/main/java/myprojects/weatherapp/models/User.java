package myprojects.weatherapp.models;


import myprojects.weatherapp.models.dto.CreateUserDto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.Collection;

//@EnableTransactionManagement
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    //private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    //@Fetch(value = FetchMode.SUBSELECT)
    // @IndexColumn(name="INDEX_COL")
    //@LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Role> roles = new ArrayList<>();

    /*@OneToMany(mappedBy = "user",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    // private List<City> cities = new ArrayList<>();
    private Set<City> cities = new HashSet<>();
   */

    // @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @ManyToMany(cascade = CascadeType.ALL)
        //@LazyCollection(LazyCollectionOption.FALSE)
        //@Fetch(value = FetchMode.SUBSELECT)
       // @JoinColumn(foreignKey = @ForeignKey(foreignKeyDefinition = "foreign key (user_id) references `user`(`id`) on delete set NULL"))
        // private Collection<City> cities = new HashSet<>();
    private Collection<City> cities = new ArrayList<>();

    public User() {
    }

    /*public User(String name, String username, String password,
                Collection<Role> roles,
                Collection<City> cities) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.cities = cities;
    }*/
    public User(String name, String username, String password,
                Collection<Role> roles) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User(CreateUserDto dto){
        this.name = dto.name;
        this.username = dto.username;
        this.password = dto.password;
        this.roles = roles = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    //public String getEmail() {return email;}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean addCity(City city) {
        return cities.add(city);
    }

    public Collection<City> getCities() {
        return cities;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    // this is an example, we just put static ROLE_USER for every authenticated user
    /*@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }*/

    /*public Collection<? extends GrantedAuthority> getRoles() {
        return new ArrayList<>(List.of(new SimpleGrantedAuthority("ROLE_USER")));} */
}
