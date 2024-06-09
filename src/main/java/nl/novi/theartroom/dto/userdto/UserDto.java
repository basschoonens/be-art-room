package nl.novi.theartroom.dto.userdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDto {

    @NotBlank(message = "Username is mandatory")
    public String username;

    @NotBlank(message = "Password is mandatory")
    public String password;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    public String email;

    public String authority;

    public UserDto() {
    }

    public UserDto(String username, String password, String email, String authority) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.authority = authority;
    }

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

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}