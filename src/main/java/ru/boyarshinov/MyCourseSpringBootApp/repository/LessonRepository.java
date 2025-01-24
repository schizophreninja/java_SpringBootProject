package ru.boyarshinov.MyCourseSpringBootApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.boyarshinov.MyCourseSpringBootApp.entity.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Lesson findById(int id);
}
