package nl.novi.theartroom.dtos.userdtos;

public class AuthenticationOutputDto {

    private final String jwt;

    public AuthenticationOutputDto(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

}