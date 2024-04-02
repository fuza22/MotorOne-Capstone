package it.epicode.Capstone.controller;

import com.cloudinary.Cloudinary;
import it.epicode.Capstone.exception.BadRequestException;
import it.epicode.Capstone.exception.CustomResponse;
import it.epicode.Capstone.exception.NotFoundException;
import it.epicode.Capstone.model.entities.Drivers;
import it.epicode.Capstone.model.entities.User;
import it.epicode.Capstone.model.request.PasswordRequest;
import it.epicode.Capstone.model.request.RegisterRequest;
import it.epicode.Capstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("/search")
    public ResponseEntity<CustomResponse> getAll(Pageable pageable){

        try{

            return CustomResponse.success(HttpStatus.OK.toString(), userService.getAll(pageable), HttpStatus.OK);

        }catch(Exception e){

            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @GetMapping("/search/{id}")
    public ResponseEntity<CustomResponse> getUserById(@PathVariable int id){

        try {
            return CustomResponse.success(HttpStatus.OK.toString(), userService.getUserById(id), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/create")
    public ResponseEntity<CustomResponse> saveUser(@RequestBody @Validated RegisterRequest userRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        try{
            return CustomResponse.success(HttpStatus.OK.toString(), userService.saveUser(userRequest), HttpStatus.OK);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<CustomResponse> updateUser(@PathVariable int id, @RequestBody @Validated RegisterRequest userRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        try {
            return CustomResponse.success(HttpStatus.OK.toString(), userService.updateUser(id, userRequest), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deleteUser(@PathVariable int id){

        try {
            userService.deleteUserById(id);
            return CustomResponse.emptyResponse("L'utente con id = " + id + " è stato cancellato", HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PatchMapping("/edit/password/{userId}")
    public ResponseEntity<CustomResponse> changePassword(@PathVariable int userId, @RequestBody @Validated PasswordRequest passwordRequest, BindingResult bindingResult){

        if(bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());

            User u = userService.getUserById(userId);

            System.out.println("Pizzette golose");
            if(!encoder.matches(passwordRequest.getOldPassword(), u.getPassword())) throw new RuntimeException("Le password non combaciano");
            System.out.println("Ficarotta");
            return CustomResponse.success(HttpStatus.OK.toString(), userService.updatePassword(userId, passwordRequest.getNewPassword()), HttpStatus.OK);

    }

    @PatchMapping("/role/{email}")
    public User changeRole(@PathVariable String email, @RequestBody String role){

        return userService.updateRoleUser(email, role);

    }


    @GetMapping("/{userId}/favorite")
    public ResponseEntity<CustomResponse> getFavorite(@PathVariable int userId) throws NotFoundException {
        User user = userService.getUserById(userId);
        List<Drivers> favList = user.getFavDrivers();
        return CustomResponse.success(favList, HttpStatus.OK);
    }

    @PostMapping("/{userId}/favorite/drivers/{driverId}")
    public ResponseEntity<User> addDriverToFavorites(@PathVariable int userId, @PathVariable int driverId) {
        try {
            User updatedUser = userService.addDriverToFavorites(userId, driverId);
            return ResponseEntity.ok(updatedUser);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}/favorite/drivers/{driverId}")
    public ResponseEntity<User> removeDriverFromFavorites(@PathVariable int userId, @PathVariable int driverId) {
        try {
            User updatedUser = userService.removeDriverFromFavorites(userId, driverId);
            return ResponseEntity.ok(updatedUser);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{userId}/favorite/constructors/{constructorId}")
    public ResponseEntity<User> addConstructorToFavorites(@PathVariable int userId, @PathVariable int constructorId) {
        try {
            User updatedUser = userService.addConstructorsToFavorites(userId, constructorId);
            return ResponseEntity.ok(updatedUser);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}/favorite/constructors/{constructorId}")
    public ResponseEntity<User> removeConstructorFromFavorites(@PathVariable int userId, @PathVariable int constructorId) {
        try {
            User updatedUser = userService.removeConstructorsFromFavorites(userId, constructorId);
            return ResponseEntity.ok(updatedUser);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{userId}/favorite/circuits/{circuitId}")
    public ResponseEntity<User> addCircuitToFavorites(@PathVariable int userId, @PathVariable int circuitId) {
        try {
            User updatedUser = userService.addCircuitsToFavorites(userId, circuitId);
            return ResponseEntity.ok(updatedUser);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}/favorite/circuits/{circuitId}")
    public ResponseEntity<User> removeCircuitFromFavorites(@PathVariable int userId, @PathVariable int circuitId) {
        try {
            User updatedUser = userService.removeCircuitsFromFavorites(userId, circuitId);
            return ResponseEntity.ok(updatedUser);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/upload")
    public ResponseEntity<CustomResponse> uploadImmagine(@PathVariable int id,@RequestParam("upload") MultipartFile file){
        try {
            User u = userService.uploadAvatar(id, (String)cloudinary.uploader().upload(file.getBytes(), new HashMap()).get("url"));
            return CustomResponse.success(HttpStatus.OK.toString(), u, HttpStatus.OK);
        }
        catch (IOException | NotFoundException e){
            return CustomResponse.error(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
