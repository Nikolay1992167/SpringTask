package by.aston.controller;

import by.aston.dto.request.RequestAuthorization;
import by.aston.dto.request.UserRequest;
import by.aston.dto.response.UserResponse;
import by.aston.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{uuid}")
    public ResponseEntity<UserResponse> findById(@PathVariable UUID uuid) {

        return ResponseEntity.ok(userService.findById(uuid));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {

        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<UserResponse> save(@RequestBody UserRequest userRequest) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.save(userRequest));
    }

    @PostMapping("/authorization")
    public ResponseEntity<String> authorization(@RequestBody RequestAuthorization userRequest) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.login(userRequest));
    }

    @PatchMapping
    public ResponseEntity<UserResponse> changingPassword(@RequestBody Map<String, Object> fields) {

        return ResponseEntity.ok(userService.changingPassword(fields));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<UserResponse> update(@PathVariable UUID uuid,
                                               @RequestBody UserRequest userRequest) {

        return ResponseEntity.ok(userService.update(uuid, userRequest));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {

        userService.delete(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
