package ru.boyarshinov.MyCourseSpringBootApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.boyarshinov.MyCourseSpringBootApp.entity.Role;
import ru.boyarshinov.MyCourseSpringBootApp.repository.RoleRepository;
import ru.boyarshinov.MyCourseSpringBootApp.repository.UserRepository;
import ru.boyarshinov.MyCourseSpringBootApp.dto.UserDto;
import ru.boyarshinov.MyCourseSpringBootApp.entity.User;


import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto){
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_READ_ONLY");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    public void updateUser(UserDto userDto){
        User user = userRepository.findById(userDto.getId());
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());

        // TODO: refactor
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        Role role = roleRepository.findByName("ROLE_" + userDto.getRoleName());

        List<Role> newRoles = new ArrayList<>();
        newRoles.add(role);
        user.setRoles(newRoles);

        userRepository.save(user);
    }


    @Override
    public User findUserByEmail(String email) { return userRepository.findByEmail(email); }
    public User findUserById(long id) { return userRepository.findById(id); }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private Role createReadOnlyRole() {
        Role roleRO = new Role();
        roleRO.setName("ROLE_READ_ONLY");
        return roleRepository.save(roleRO);
    }
}
