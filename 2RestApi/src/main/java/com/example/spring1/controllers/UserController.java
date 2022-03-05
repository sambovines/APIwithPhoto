package com.example.spring1.controllers;

import com.example.spring1.exceptions.helper.ValidationError;
import com.example.spring1.exceptions.UserNotFoundException;
import com.example.spring1.exceptions.UserPutException;
import com.example.spring1.model.User;
import com.example.spring1.service.PhotoService;
import com.example.spring1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController extends UserExceptionHandler {

    private final UserService userService;
    private final PhotoService photoService;

    @Autowired
    public UserController(UserService userService, PhotoService photoService) {
        this.userService = userService;
        this.photoService = photoService;
    }

    @ResponseBody
    @GetMapping(value = "{id}/photo", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable Long id ) throws IOException, UserNotFoundException {
        // TODO
        User user = userService.findById(id);
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(StreamUtils.copyToByteArray(new FileInputStream(new File(user.getPhoto()))));
    }


    @PostMapping(value = "{id}/photo")
    public ResponseEntity<User> addPhotoToCurrentUser(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws Exception {

        String photoPath = photoService.savePhoto(file);
        User user = userService.setPhotoToUser(id, photoPath);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers()
            throws UserNotFoundException {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<User> addNewUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws UserNotFoundException {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> putUserById(@PathVariable Long id, @Valid @RequestBody User newUser) throws UserNotFoundException, UserPutException {
        return new ResponseEntity<>(userService.putUser(newUser, id), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<User> patchUserById(@PathVariable Long id, @RequestBody User newUser) throws UserNotFoundException {
        return new ResponseEntity<>(userService.patchUser(newUser, id), HttpStatus.OK);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }


    /**
     * Перехватываем ошибки валидации @valid
     *
     * @param exception
     * @param request
     * @return Вовращаем объект ValidationError с перечислением ошибок валидации
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationError> handleMethodArgNotValid(MethodArgumentNotValidException exception, HttpServletRequest request) {
        ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST, request.getServletPath());
        BindingResult bindingResult = exception.getBindingResult();

        Map<String, String> validationErrors = new HashMap<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        error.setValidationErrors(validationErrors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
