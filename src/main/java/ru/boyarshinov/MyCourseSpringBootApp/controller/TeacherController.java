package ru.boyarshinov.MyCourseSpringBootApp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.boyarshinov.MyCourseSpringBootApp.repository.TeacherRepository;
import ru.boyarshinov.MyCourseSpringBootApp.entity.Teacher;
import ru.boyarshinov.MyCourseSpringBootApp.service.TeacherService;
import ru.boyarshinov.MyCourseSpringBootApp.service.UserService;

import java.util.Optional;

@Controller
public class TeacherController {
    @Autowired
    private TeacherRepository teacherRepository;
    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/listTeachers")
    public ModelAndView getALlTeachers() {
        ModelAndView mav = new ModelAndView("list-teachers");
        mav.addObject("teachers", teacherRepository.findAll());
        return mav;
    }

    @GetMapping("/addTeacherForm")
    public ModelAndView addTeacherForm() {
        ModelAndView mav = new ModelAndView("add-teacher-form");
        Teacher teacher = new Teacher();
        mav.addObject("teacher", teacher);
        return mav;
    }

    @PostMapping("/saveTeacher")
    public String saveTeacher(@ModelAttribute Teacher teacher) {
        teacherRepository.save(teacher);
        return "redirect:/listTeachers";
    }

    @GetMapping("/showTeacherUpdateForm")
    public ModelAndView showTeacherUpdateForm(@RequestParam Long teacherId) {
        ModelAndView mav = new ModelAndView("add-teacher-form");
        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
        Teacher teacher = new Teacher();
        if(optionalTeacher.isPresent()){
            teacher = optionalTeacher.get();
        }
        mav.addObject("teacher", teacher);
        return mav;
    }

    @GetMapping("/deleteTeacher")
    public String deleteTeacher(@RequestParam Long teacherId){
        teacherRepository.deleteById(teacherId);
        return "redirect:/listTeachers";
    }

    @GetMapping("/calcAvgTeacherSalary")
    public String showAverageSalaryForm() {
        return "calculate-avg-salary";
    }

    @PostMapping("/calcAvgTeacherSalary")
    public String calcAverageSalary(Model model) {
        int avgSalary = teacherService.getAverageSalary();
        model.addAttribute("avgSalary", avgSalary);
        return "calculate-avg-salary";
    }

}