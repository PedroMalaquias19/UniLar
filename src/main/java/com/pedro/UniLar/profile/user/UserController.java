package com.pedro.UniLar.profile.user;

import com.pedro.UniLar.profile.user.entities.User;
import com.pedro.UniLar.profile.user.enums.Role;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @PatchMapping("password/")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ){
        userService.changePassword(request, connectedUser);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("role/")
    public ResponseEntity<?> updateRole(
            @RequestBody UpdateRoleRequest request,
            Principal connectedUser
    ){
        userService.updateRole(request, connectedUser);
        return ResponseEntity.accepted().build();
    }

    @PostMapping(
            path = "{id}/image/upload/",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> uploadUserProfileImage(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file){
        userService.uploadUserProfileImage(id, file);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/image/download/")
    public byte[] downloadUserProfileImage(@PathVariable("id") Long id){
        return userService.downloadUserProfileImage(id);
    }

    @Builder
    public record ChangePasswordRequest(String currentPassword, String newPassword, String confirmPassword) {
    }

    @Builder
    public record UpdateRoleRequest(String email, Role role){
    }

    //TODO. Get a new password without the old
}
