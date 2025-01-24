package ru.boyarshinov.MyCourseSpringBootApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.boyarshinov.MyCourseSpringBootApp.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("SELECT AVG(t.salary) FROM Teacher t")
    Double findAverageSalary();
}
