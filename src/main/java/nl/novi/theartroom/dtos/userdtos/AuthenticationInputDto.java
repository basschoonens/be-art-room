package nl.novi.theartroom.dtos.userdtos;

import jakarta.validation.constraints.NotBlank;

public class AuthenticationInputDto {

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;

    public AuthenticationInputDto() {
    }
    public AuthenticationInputDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}