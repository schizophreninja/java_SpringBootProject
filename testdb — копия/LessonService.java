package ru.boyarshinov.MyCourseSpringBootApp.service;

import ru.boyarshinov.MyCourseSpringBootApp.dto.UserDto;
import ru.boyarshinov.MyCourseSpringBootApp.entity.User;

import java.util.List;

public interface LessonService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
