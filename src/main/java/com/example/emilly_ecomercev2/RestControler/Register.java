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
@RequestMapping("/Register")
@CrossOrigin
public class Register {

    public final UserService userService;
    private final PasswordEncoder passwordEncoder;


    public Register(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping()
    public void register(@RequestBody Korisnik user)
    {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        if(Objects.equals(user.getMail(), "hristijan.kolevski099@gmail.com"))
        {
            Korisnik newuser=new Korisnik(user.getMail(),encodedPassword,"ADMIN");
            userService.save(newuser);
        }
        else
        {

            Korisnik newuser=new Korisnik(user.getMail(),encodedPassword,"USER");
            userService.save(newuser);
        }

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
