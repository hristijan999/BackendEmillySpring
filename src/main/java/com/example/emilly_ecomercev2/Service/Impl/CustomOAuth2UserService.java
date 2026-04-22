package com.example.emilly_ecomercev2.Service.Impl;

import com.example.emilly_ecomercev2.Model.Korisnik;
import com.example.emilly_ecomercev2.Repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");


        if (email != null) {
            Korisnik existing = userRepository.findByMail(email);
            if (existing == null) {
                Korisnik newUser = new Korisnik();
                newUser.setMail(email);
                newUser.setPassword("");
                if (email.equals("hristijan.kolevski099@gmail.com")) {
                    newUser.setRole("ADMIN");
                } else {
                    newUser.setRole("USER");
                }
                newUser.setEnabled(true);
                userRepository.save(newUser);
                existing = newUser; // ✅ use the newly saved user
            }

            // ✅ Read the actual role from the database
            Set<GrantedAuthority> authorities = Collections.singleton(
                    new SimpleGrantedAuthority(existing.getRole())
            );
            return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "email");
        }

        // Fallback (email was null)
        Set<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("USER"));
        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "email");
    }
}


