package com.tech11.business.users.entity;

import static com.tech11.business.users.entity.User.FIND_ALL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Email;

/**
 *Entity class representing a User Object
 * 
 * @author olatunji oniyide
 */
@Entity
@Table(name = "users_table")
@NamedQuery(name = FIND_ALL, query = "SELECT u FROM User u")
public class User {

    private static final String PREFIX = "com.tech11.business.users.entity.User";
    public static final String FIND_ALL = PREFIX + ".findAll";
    
    private static final String DATE_FORMATTER = "yyyy-MM-dd";

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    
    @Column(name = "firstname", nullable = false)
    private String firstname;
    
    @Column(name = "lastname", nullable = false)
    private String lastname;
    
    @Column(name = "email", nullable = false)
    @Email
    private String email;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate birthday;

    public User() {
        //default constructor for JPA
    }

    public User(String firstname, String lastname, String email, String password, LocalDate birthday) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder().add("firstname", this.firstname)
                .add("lastname", this.lastname)
                .add("email", this.email)
                .add("password", this.password)
                .add("birthday", this.birthday.toString())
                .build();
    }

    public User(JsonObject object) {
        this.firstname = object.getString("firstname");
        this.lastname = object.getString("lastname");
        this.email = object.getString("email");
        this.password = object.getString("password");
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        this.birthday = LocalDate.parse(object.getString("birthday"), formatter);
    }

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
