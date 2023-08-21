package pl.mm.foodemo.api.rest.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.mm.foodemo.api.dto.UserDTO;
import pl.mm.foodemo.api.dto.UserRegisterDTO;
import pl.mm.foodemo.api.dto.mapper.UserMapperDTO;
import pl.mm.foodemo.business.service.UserService;
import pl.mm.foodemo.domain.User;

import java.net.URI;

import static pl.mm.foodemo.api.rest.controller.UserRestController.API_USERS;

@RestController
@RequestMapping(API_USERS)
@AllArgsConstructor
@Validated
public class UserRestController {

    public static final String API_USERS = "/api/users";
    public static final String USER_ID = "/{userId}";

    private final UserService userService;

    private final UserMapperDTO userMapper;



    @PostMapping
    public ResponseEntity<UserRegisterDTO> registerUser(
            @Valid @RequestBody UserRegisterDTO userRegisterDTO
    ) {

        User user = userService.registerUser(userRegisterDTO);

        return ResponseEntity
                .created(URI.create(API_USERS + "/" + user.getUserId()))
                .build();
    }

    @GetMapping(USER_ID)
    public ResponseEntity<UserDTO> getUser(
            @PathVariable("userId") Long userId
    ) {
        User user = userService.findUserById(userId);

        if (user != null) {
            UserDTO userDTO = userMapper.map(user);
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping(USER_ID)
    public ResponseEntity<?> deleteUser(
            @PathVariable("userId") Long userId
    ) {
        try {
            userService.removeById(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}

