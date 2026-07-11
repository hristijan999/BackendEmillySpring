package com.example.emilly_ecomercev2.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Korisnik implements UserDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;
    String mail;
    String password;
    String role;
    @ElementCollection(fetch = FetchType.LAZY)
    List<Long> LikedImages;

    String location;
    String verificationToken;
    @Column(nullable = false)
    boolean isEnabled;

    public Korisnik(String mail, String encodedPassword, String admin) {
        this.mail = mail;
        this.password = encodedPassword;
        this.role = admin;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return mail;
    }
}
