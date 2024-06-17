package nl.novi.theartroom.controller;

import nl.novi.theartroom.dto.userdto.UserDto;
import nl.novi.theartroom.exception.BadRequestException;
import nl.novi.theartroom.exception.UnauthorizedAccessException;
import nl.novi.theartroom.service.userservice.UserService;
import nl.novi.theartroom.util.UriBuilderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private UserDto userDto;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> userDtos = userService.getUsers();
        return ResponseEntity.ok().body(userDtos);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable("username") String username) {
        UserDto optionalUser = userService.getUser(username);
        return ResponseEntity.ok().body(optionalUser);
    }

    @PostMapping(value = "/user")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        String newUsername = userService.createUser(dto);
        userService.addAuthority(newUsername, "ROLE_USER");
        URI location = UriBuilderUtil.buildUriBasedOnStringId(newUsername, "/{username}");

        return ResponseEntity.created(location).build();
    }

    @PostMapping(value = "/artist")
    public ResponseEntity<UserDto> createArtist(@RequestBody UserDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        String newUsername = userService.createUser(dto);
        userService.addAuthority(newUsername, "ROLE_ARTIST");
        URI location = UriBuilderUtil.buildUriBasedOnStringId(newUsername, "/{username}");

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<UserDto> updateUserPassword(@PathVariable("username") String username, @RequestBody UserDto dto) {
        String currentUsername = userService.getCurrentLoggedInUsername();
        if (!currentUsername.equals(username)) {
            throw new UnauthorizedAccessException("You are not authorized to update this user.");
        }
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            String encryptedPassword = passwordEncoder.encode(dto.getPassword());
            dto.setPassword(encryptedPassword);
        }
        userService.updateUser(username, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }

    @PostMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addAuthority(username, authorityName);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }
}