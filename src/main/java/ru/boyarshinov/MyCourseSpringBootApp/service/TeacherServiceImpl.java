package ru.boyarshinov.MyCourseSpringBootApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.boyarshinov.MyCourseSpringBootApp.dto.UserDto;
import ru.boyarshinov.MyCourseSpringBootApp.entity.Role;
import ru.boyarshinov.MyCourseSpringBootApp.entity.User;
import ru.boyarshinov.MyCourseSpringBootApp.repository.RoleRepository;
import ru.boyarshinov.MyCourseSpringBootApp.repository.TeacherRepository;
import ru.boyarshinov.MyCourseSpringBootApp.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public int getAverageSalary() {
        Double avg = teacherRepository.findAverageSalary();
        return (int)Math.round((avg != null) ? avg : 0.0);
    }
}
