package com.lepa.portal.controller;


import com.lepa.portal.dto.UsersDTO;
import com.lepa.portal.dto.UsersListDTO;
import com.lepa.portal.model.portal.Users;
import com.lepa.portal.repository.UsersRepo;
import com.lepa.portal.service.UsersService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UsersController {


    private UsersRepo usersRepo;
    private PasswordEncoder passwordEncoder;
    private UsersService usersService;
    private Authentication authentication;

    private Map<String, String> stateOk = Collections.singletonMap("Operation", "Successful");

    @Autowired
    public UsersController(UsersRepo usersRepo, PasswordEncoder passwordEncoder, UsersService usersService) {
        this.usersRepo = usersRepo;
        this.passwordEncoder = passwordEncoder;
        this.usersService = usersService;
    }

    //region Get

    @GetMapping("/Login")
    public ResponseEntity<Object> testLogin() {
        log.info("You are logged in");
        return ResponseEntity.ok(stateOk);
    }

    @GetMapping("/ADD")
    public Users add() {
        return usersService.createAdmin();
    }

    @GetMapping("")
    public List<Users> findAllUsers() {
        return usersService.getAllUsersWithPassword();
    }

    @GetMapping("/{id}")
    public Users findOneUser(@PathVariable(value = "id") long id) {
        return usersService.oneUser(id);

    }

    @GetMapping("/Role")
    public String currentRole() {
        return usersService.currentRole();
    }

    @GetMapping("/AllRoles")
    public Object getAllRoles() {
        return usersService.getAllRoles();
    }

    @GetMapping("/AllUsers")
    public List<UsersListDTO> getAllUsers() {
        return usersService.getAllUsers();
    }
    //endregion

    //region Put
    @PutMapping("/{id}")
    public Users updateUser(@PathVariable(value = "id") long id, @RequestBody UsersDTO usersDTO) {
        return usersService.updateUser(id, usersDTO);
    }
    //endregion

    //region Post
    @PostMapping("/Register")
    public Object addUser(@RequestBody UsersDTO usersDTO) {
        return usersService.createUsers(usersDTO);
    }

    @PostMapping("/SetRole/{id}")
    public ResponseEntity<Object> setRole(@PathVariable(value = "id") long id, @RequestBody String role) {
        usersService.setRole(id, role);
        return ResponseEntity.ok(stateOk);

    }

    @PostMapping("/ChangeEmail")
    public ResponseEntity<Object> changeEmail(@RequestBody String email) {
        usersService.changeEmail(email);
        return ResponseEntity.ok(stateOk);
    }

    @PostMapping("/ChangePassword")
    public ResponseEntity<Object> changePassword(@RequestBody String password) {
        usersService.changePassword(password);
        return ResponseEntity.ok(stateOk);
    }

    @PostMapping("/ResetPasswordsMail")
    public ResponseEntity<Object> resetPasswordsMail(@RequestBody UsersDTO usersDTO) throws MessagingException {
        usersService.resetPasswordbyMail(usersDTO);
        return ResponseEntity.ok(stateOk);
    }

    @PostMapping("/ResetPassword")
    public ResponseEntity<Object> resetPassword(@RequestParam String token, @RequestBody String password) {
        usersService.resetPassword(token, password);
        return ResponseEntity.ok(stateOk);
    }
    //endregion


}
