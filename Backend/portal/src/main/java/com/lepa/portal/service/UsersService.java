package com.lepa.portal.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lepa.portal.dto.UsersDTO;
import com.lepa.portal.dto.UsersListDTO;
import com.lepa.portal.enums.Role;
import com.lepa.portal.exception.UserNotFoundException;
import com.lepa.portal.exception.WrongRoleException;
import com.lepa.portal.model.portal.Token;
import com.lepa.portal.model.portal.Users;
import com.lepa.portal.repository.TokenRepo;
import com.lepa.portal.repository.UsersRepo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Data
@Service
@Slf4j
public class UsersService {


    private UsersRepo usersRepo;
    private PasswordEncoder passwordEncoder;
    private MailService mailService;
    private TokenRepo tokenRepo;
    private Environment environment;
    ModelMapper modelMapper;

    @Autowired
    public UsersService(UsersRepo usersRepo, PasswordEncoder passwordEncoder, MailService mailService, TokenRepo tokenRepo, Environment environment,ModelMapper modelMapper) {
        this.usersRepo = usersRepo;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.tokenRepo = tokenRepo;
        this.environment = environment;
        this.modelMapper= modelMapper;
    }

    public List<Users> getAllUsersWithPassword() {
        return usersRepo.findAll();
    }

    public Users oneUser(long id) {
        return usersRepo.findUsersById(id).orElseThrow(() -> new UserNotFoundException("User with " + id + " id doesn't exist"));
    }

    public Users updateUser(long id, UsersDTO usersDTO) {
        Users users = usersRepo.findUsersById(id).orElseThrow(() -> new UserNotFoundException("User doesn't exist"));
        if ((usersRepo.existsByEmail(usersDTO.getEmail()) || usersRepo.existsByUsername(usersDTO.getUsername()))) {
            throw new SecurityException("Email or Login is already taken");
        }
        users.setPassword(passwordEncoder.encode(usersDTO.getPassword()));
        users.setUsername(usersDTO.getUsername());
        users.setEmail(usersDTO.getEmail());
        return addUsers(users);

    }

    public Users createUsers(UsersDTO usersDTO) {
        if ((usersRepo.existsByEmail(usersDTO.getEmail()) || usersRepo.existsByUsername(usersDTO.getUsername()))) {
            throw new SecurityException("Email or Login is already taken");
        }
        usersDTO.setPassword(passwordEncoder.encode(usersDTO.getPassword()));
        usersDTO.setRole(Role.USER);
        Users user = modelMapper.map(usersDTO, Users.class);
        return addUsers(user);
    }

    public Users createAdmin() {
        Users admin = new Users();
        admin.setEmail("ADMIN@admin.pl");
        admin.setUsername("ADMIN");
        admin.setRole(Role.ADMIN);
        admin.setPassword(passwordEncoder.encode("nope"));
        if (Boolean.TRUE.equals(usersRepo.existsByEmail(admin.getEmail()) || usersRepo.existsByUsername(admin.getUsername()))) {
            return admin;
        }

        return usersRepo.save(admin);
    }

    private Users addUsers(Users newUser) {
        return usersRepo.save(newUser);
    }

    public Users returnCurrentUser() {

        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            throw new UserNotFoundException("You aren't logged");

        } else {
            return (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
    }

    public long returnCurrentUserId() {
        return returnCurrentUser().getId();
    }

    public String currentRole() {
        return "{\"role\":\"" + returnCurrentUser().getRole() + "\"}";
    }

    public Users setRole(long id, String role) {
        if (returnCurrentUser().getRole().toString().equals("ADMIN")) {
            Users userRole = usersRepo.findUsersById(id).orElseThrow(() -> new UserNotFoundException("User doesn't exist"));
            userRole.setRole(Role.values()[Integer.parseInt(role)]);
            return usersRepo.save(userRole);
        } else {
            throw new WrongRoleException("Your aren't an admin");
        }
    }

    public Object getAllRoles() {
        Role[] roleList = Role.values();
        JsonArray roleArray = new JsonArray();
        for (Role role : roleList) {
            JsonObject objectRole = new JsonObject();
            objectRole.addProperty("id", role.ordinal());
            objectRole.addProperty("role", role.name());
            roleArray.add(objectRole);
        }
        return new Gson().toJson(roleArray);
    }

    public List<UsersListDTO> getAllUsers() {
        List<Users> usersList = usersRepo.findAll();
        return usersList.stream().map(users -> modelMapper.map(users, UsersListDTO.class)).collect(Collectors.toList());

    }

    public Users changePassword(String password) {
        Users currentUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        currentUser.setPassword(passwordEncoder.encode(password));
        return usersRepo.save(currentUser);
    }

    public Users changeEmail(String email) {
        Users currentUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        currentUser.setEmail(email);
        return usersRepo.save(currentUser);
    }

    public void resetPasswordbyMail(UsersDTO usersDTO) throws MessagingException {
        if (!usersDTO.getEmail().isEmpty()) {
            sendMail(usersDTO.getEmail(), "Restart Password ");
        }
    }

    private void sendMail(String email, String subject) throws MessagingException {
        String token = UUID.randomUUID().toString();
        Users users = usersRepo.findUsersByEmail(email).orElseThrow(() -> new UserNotFoundException("User with this email doesn't exist"));
        Token newToken = tokenRepo.findByUsers(users).orElse(new Token());
        newToken.setTokenValue(token);
        newToken.setUsers(users);
        tokenRepo.save(newToken);
        String urlToken = environment.getProperty("mail.token")+"/resetPassword?token="+ token;
        mailService.sendMail(email, subject, urlToken);
    }

    public Users resetPassword(String token, String newPassword) {
        Users user;
        try {
            user = tokenRepo.findByTokenValue(token).getUsers();
        } catch (NullPointerException e) {
            throw new NullPointerException("user doesn't exist");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        return usersRepo.save(user);
    }


}
