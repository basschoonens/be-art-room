package nl.novi.theartroom.mappers;

import nl.novi.theartroom.dtos.userdtos.UserDto;
import nl.novi.theartroom.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserDtoMapper {

    public UserDto fromUser(User user){

        var dto = new UserDto();

        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.email = user.getEmail();
        dto.authority = user.getAuthorities().iterator().next().getAuthority();

        return dto;
    }

    public User toUser(UserDto userDto) {

        var user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());

        return user;
    }

}
