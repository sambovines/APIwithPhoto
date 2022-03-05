package com.example.spring1.service;

import com.example.spring1.exceptions.UserNotFoundException;
import com.example.spring1.exceptions.UserPutException;
import com.example.spring1.model.User;
import com.example.spring1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Поиск пользователя по идентификатору
     *
     * @param id идентификатор
     * @return пользователь с заданным ид
     * @throws UserNotFoundException пользователь с заданнм ид не найден
     */
    public User findById(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user with id=" + id + " not found"));
    }

    /**
     * Получить список всех пользователей
     *
     * @return список пользователей
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }


    /**
     * Добавить пользователя в бд
     *
     * @param user объект пользователя
     * @return созданный пользователь
     */
    public User addUser(User user) {
        user = userRepository.save(user);
        return user;
    }

    /**
     * Полное иззменение пользователя
     *
     * @param newUser новый объект пользователя
     * @param id      айди пользователя которого будем изменять
     * @return измененный объект пользователя
     * @throws UserNotFoundException пользователь не найден
     * @throws UserPutException      одно из полей пустое(недопустимо)
     */
    public User putUser(User newUser, Long id) throws UserNotFoundException, UserPutException {
        // в методе put все поля должны быть заданы

        boolean isOneFielsEmpty = false;

        if (newUser.getFirstName() == null || newUser.getFirstName().isEmpty())
            isOneFielsEmpty = true;
        else if (newUser.getLastName() == null || newUser.getLastName().isEmpty())
            isOneFielsEmpty = true;
        else if (newUser.getEmail() == null || newUser.getEmail().isEmpty())
            isOneFielsEmpty = true;
        else if (newUser.getPatronymic() == null || newUser.getPatronymic().isEmpty())
            isOneFielsEmpty = true;
        else if (newUser.getPhoneNumber() == null || newUser.getPatronymic().isEmpty())
            isOneFielsEmpty = true;
        else if (newUser.getBirthday() == null)
            isOneFielsEmpty = true;


        if (isOneFielsEmpty)
            throw new UserPutException("one fields is empty");

        User user = this.findById(id);
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setPhoneNumber(newUser.getPhoneNumber());

        user.setPatronymic(newUser.getPatronymic());
        user.setBirthday(newUser.getBirthday());
        user.setEmail(newUser.getEmail());

        User save = userRepository.save(user);
        return save;
    }

    /**
     * Частичное изменение полей
     *
     * @param newUser Новый объект
     * @param id      айди по которому изменяем пользователя
     * @return измененный объект пользователя
     * @throws UserNotFoundException пользователь с заданным айди не найдент
     */
    public User patchUser(User newUser, Long id) throws UserNotFoundException {
        User user = this.findById(id);

        if (newUser.getFirstName() != null && !newUser.getFirstName().isEmpty())
            user.setFirstName(newUser.getFirstName());

        if (newUser.getLastName() != null && !newUser.getLastName().isEmpty())
            user.setLastName(newUser.getFirstName());

        if (newUser.getPhoneNumber() != null && !newUser.getPhoneNumber().isEmpty())
            user.setPhoneNumber(newUser.getPhoneNumber());

        if (newUser.getEmail() != null && !newUser.getEmail().isEmpty())
            user.setEmail(newUser.getEmail());

        if (newUser.getPatronymic() != null && !newUser.getPatronymic().isEmpty())
            user.setPatronymic(newUser.getPatronymic());

        if (newUser.getBirthday() != null)
            user.setBirthday(newUser.getBirthday());

        return userRepository.save(user);
    }

    /**
     * Удаление пользователя
     *
     * @param id айди удаляемого пользователя
     * @return объект до удаления
     * @throws UserNotFoundException Пользователь с заданным id не найден
     */
    public User deleteUser(Long id) throws UserNotFoundException {
        User byId = findById(id);
        userRepository.delete(byId);
        return byId;
    }

    /**
     * Сохранить пользователя
     *
     * @param user сохраняемый пользователь
     * @return объект user извлеченный из бд после сохранения
     */
    public User save(User user) {
        return userRepository.save(user);
    }


    public User setPhotoToUser(Long id, String photoPath) throws UserNotFoundException {
        User byId = findById(id);

        byId.setPhoto(photoPath);

        return save(byId);
    }


}
