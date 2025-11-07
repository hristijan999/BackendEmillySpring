package com.example.emilly_ecomercev2.RestControler.Admin;

import com.example.emilly_ecomercev2.Model.User;
import com.example.emilly_ecomercev2.Service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("Admin/Admin")
public class Admin {
    public final UserService userService;
    private final PasswordEncoder passwordEncoder;
    public Admin(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/savePassword")
    public void savePassword(@RequestBody Map<String, String> body)
    {
        String password = body.get("password");

        String encodedPassword = passwordEncoder.encode(password);
        User user =userService.findByMail("hristijan.kolevski099@gmail.com");
        user.setPassword(encodedPassword);
        userService.save(user);
    }
}
