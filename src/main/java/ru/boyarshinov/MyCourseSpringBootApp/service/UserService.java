package ru.boyarshinov.MyCourseSpringBootApp.service;

import ru.boyarshinov.MyCourseSpringBootApp.dto.UserDto;
import ru.boyarshinov.MyCourseSpringBootApp.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    void updateUser(UserDto userDto);

    User findUserByEmail(String email);

    User findUserById(long id);

    List<UserDto> findAllUsers();
}
