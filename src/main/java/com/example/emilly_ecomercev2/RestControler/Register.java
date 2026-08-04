package com.example.emilly_ecomercev2.RestControler;

import com.example.emilly_ecomercev2.Model.Korisnik;
import com.example.emilly_ecomercev2.Service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("Api/Register")
@CrossOrigin
public class Register {

    public final UserService userService;



    public Register(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;

    }

    @PostMapping()
    public void register(@RequestBody Korisnik user)
    {
            userService.save(user);
    }
    @GetMapping("/me")
    public Map<String, Object> currentUser(Principal principal, Authentication authentication) {
        Map<String, Object> userInfo = new HashMap<>();
        if(principal != null)
        {
            userInfo.put("username", principal.getName());

            userInfo.put("roles", authentication.getAuthorities()
                    .stream()
                    .map(a -> a.getAuthority())
                    .toList());
        }

        return userInfo;
    }


}
