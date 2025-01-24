package ru.boyarshinov.MyCourseSpringBootApp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.boyarshinov.MyCourseSpringBootApp.repository.LessonRepository;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="TEACHERS")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="fathername")
    private String fatherName;

    @Column(name="age")
    private int age;

    @Column(name="salary")
    private int salary;

    @Column(name="lessonid")
    private int lessonId;
    //private String lessonName = LessonRepository.findById(lessonId);
}