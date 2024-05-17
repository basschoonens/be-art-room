package nl.novi.theartroom.dtos.userdtos;

import nl.novi.theartroom.models.Authority;

import java.util.Set;

    //TODO Authority omzetten naar Roles

public class UserDto {

    public String username;

    public String password;
    public String email;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}