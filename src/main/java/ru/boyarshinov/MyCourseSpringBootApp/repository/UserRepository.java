package ru.boyarshinov.MyCourseSpringBootApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.boyarshinov.MyCourseSpringBootApp.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findById(long id);
}
