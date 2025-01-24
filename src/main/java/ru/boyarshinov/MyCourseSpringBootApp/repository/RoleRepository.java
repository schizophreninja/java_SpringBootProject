package ru.boyarshinov.MyCourseSpringBootApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.boyarshinov.MyCourseSpringBootApp.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
