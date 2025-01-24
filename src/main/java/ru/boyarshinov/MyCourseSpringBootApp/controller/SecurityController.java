package ru.boyarshinov.MyCourseSpringBootApp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.boyarshinov.MyCourseSpringBootApp.dto.UserDto;
import ru.boyarshinov.MyCourseSpringBootApp.entity.User;
import ru.boyarshinov.MyCourseSpringBootApp.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class SecurityController {
    private UserService userService;

    public SecurityController(UserService userService) { this.userService = userService; }

    @GetMapping("/index")
    public String home() { return "index"; }

    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "На этот адрес электронной почты уже зарегистрирована учётная запись");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") long userId, Model model) {
        User user = userService.findUserById(userId);
        if (user == null) {
            return "redirect:/users";
        }

        String[] userNameParts = user.getName().split(" ");

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(userNameParts[0]);
        userDto.setLastName(userNameParts[1]);
        userDto.setEmail(user.getEmail());

        // Предположим, что у пользователя всегда одна основная роль
        // и в БД она хранится как "ROLE_ADMIN", "ROLE_USER", "ROLE_READ_ONLY".
        // Уберём префикс ROLE_ для удобства в форме:
        if (!user.getRoles().isEmpty()) {
            userDto.setRoleName(
                    user.getRoles().iterator().next().getName().replace("ROLE_", "")
            );
        } else {
            userDto.setRoleName("READ_ONLY"); // значение по умолчанию
        }

        model.addAttribute("targetUser", userDto);
        return "edit-user-form"; // имя вашего Thymeleaf-шаблона
    }

    @PostMapping("/users/edit")
    public String updateUser(@ModelAttribute("userDto") @Valid UserDto userDto,
                             BindingResult bindingResult,
                             Model model) {
        // Проверяем валидацию
        if (bindingResult.hasErrors()) {
            return "edit-user-form"; // назад на форму
        }

        // Вызываем сервис, который обновит данные пользователя
        userService.updateUser(userDto);

        // Например, вернемся на список пользователей
        return "redirect:/users";
    }

    @GetMapping("/cabinet")
    public String getCabinetPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return "redirect:/login";
        }
        String email = authentication.getName();

        User user = userService.findUserByEmail(email);
        if (user == null) {
            // Если вдруг не нашли — редирект/ошибка
            return "redirect:/login";
        }

        UserDto userDto = new UserDto();

        String[] nameParts = user.getName().split(" ");
        userDto.setFirstName(nameParts[0]);
        userDto.setLastName(nameParts.length > 1 ? nameParts[1] : "");
        userDto.setEmail(user.getEmail());
        if (!user.getRoles().isEmpty()) {
            userDto.setRoleName(user.getRoles().iterator().next().getName().replace("ROLE_", ""));
        }

        model.addAttribute("currentUser", userDto);

        return "cabinet";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
