package com.pedro.UniLar.profile.user;

import achama.website.awss3.file.FileService;
import achama.website.exception.BadRequestException;
import achama.website.exception.NotAllowedException;
import achama.website.exception.NotFoundException;
import achama.website.exception.UniqueKeyViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;

    public User saveUser(User user){
        try {
            user = userRepository.save(user);
            return user;
        } catch (DataIntegrityViolationException e) {
            throw new UniqueKeyViolationException("Duplicate email or NIF found");
        }
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));
    }

    public User findById(Long id){
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void enableUser(User user) {
        user.setEnabled(true);
        user.setNonLocked(true);
        userRepository.save(user);
    }

    public void updateRole(UserController.UpdateRoleRequest request, Principal connectedUser){

        var admin = getUserFromPrincipal(connectedUser);

        if(!(admin.getRole() == Role.ADMIN)){
            throw new NotAllowedException("User does not have authorization to change role");
        }

        var user = findByEmail(request.email());
        user.setRole(request.role());

        userRepository.save(user);
    }

    public void changePassword(UserController.ChangePasswordRequest request, Principal connectedUser){

        var user = getUserFromPrincipal(connectedUser);

        //Check if the current password is correct
        if(!passwordEncoder.matches(request.currentPassword(), user.getPassword())){
            throw new BadRequestException("Wrong password");
        }

        //Check if the provided password are the same
        if(!Objects.equals(request.newPassword(), request.confirmPassword())){
            throw new BadRequestException("Provided passwords are not the same");
        }

        //Update the password
        user.setPassword(passwordEncoder.encode(request.newPassword()));

        //Save new password
        userRepository.save(user);
    }

    private static User getUserFromPrincipal(Principal connectedUser) {
        return (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
    }

    public void uploadUserProfileImage(Long id, MultipartFile file) {
        User user = findById(id);
        String imageUrl = fileService.uploadImage("profile", id, file);
        user.setProfileImageUrl(imageUrl);
        userRepository.save(user);
    }


    public byte[] downloadUserProfileImage(Long id) {
        User user = findById(id);
        return fileService.downloadImage(user.getProfileImageUrl().orElse(null));
    }
}
