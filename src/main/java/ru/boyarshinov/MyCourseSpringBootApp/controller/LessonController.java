package ru.boyarshinov.MyCourseSpringBootApp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.boyarshinov.MyCourseSpringBootApp.entity.Lesson;
import ru.boyarshinov.MyCourseSpringBootApp.repository.LessonRepository;

import java.util.Optional;

@Slf4j
@Controller
public class LessonController {
    @Autowired
    private LessonRepository lessonRepository;

    @GetMapping("/listLessons")
    public ModelAndView getALlLessons() {
        log.info("/listLessons -> connection");
        ModelAndView mav = new ModelAndView("list-lessons");
        mav.addObject("lessons", lessonRepository.findAll());
        return mav;
    }

    @GetMapping("/addLessonForm")
    public ModelAndView addLessonForm() {
        ModelAndView mav = new ModelAndView("add-lesson-form");
        Lesson lesson = new Lesson();
        mav.addObject("lesson", lesson);
        return mav;
    }

    @PostMapping("/saveLesson")
    public String saveLesson(@ModelAttribute Lesson lesson) {
        lessonRepository.save(lesson);
        return "redirect:/listLessons";
    }

    @GetMapping("/showLessonUpdateForm")
    public ModelAndView showTeacherUpdateForm(@RequestParam Long lessonId) {
        ModelAndView mav = new ModelAndView("add-lesson-form");
        Optional<Lesson> optionalLesson = lessonRepository.findById(lessonId);
        Lesson lesson = new Lesson();
        if(optionalLesson.isPresent()){
            lesson = optionalLesson.get();
        }
        mav.addObject("lesson", lesson);
        return mav;
    }

    @GetMapping("/deleteLesson")
    public String deleteTeacher(@RequestParam Long lessonId){
        lessonRepository.deleteById(lessonId);
        return "redirect:/listLessons";
    }
}