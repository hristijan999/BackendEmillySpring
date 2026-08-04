package com.example.emilly_ecomercev2.Service.Impl;

import com.example.emilly_ecomercev2.Model.Korisnik;
import com.example.emilly_ecomercev2.Repository.UserRepository;
import com.example.emilly_ecomercev2.Service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    public final UserRepository userRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    @Override
    public Korisnik findByMail(String mail) {
        return userRepository.findByMail(mail);
    }

    @Override
    public Optional<Korisnik> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<Long> findLikedImageIdsByUsername(String mail) {
        return userRepository.findLikedImageIdsByUsername(mail);
    }

    @Override
    public void updatedLikedImageIdsByUsername(Long id,String mail) {
        Korisnik user=userRepository.findByMail(mail);

        if (user == null) {
            throw new RuntimeException("User not found");
        }
        List<Long>list=user.getLikedImages();
        list.add(id);
        userRepository.save(user);
    }

    public void deleteLikedId(Long id, String mail) {
        Korisnik user = userRepository.findByMail(mail);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        List<Long> likedImages = user.getLikedImages();

        if (likedImages.contains(id)) {
            likedImages.remove(id);
            userRepository.save(user);
        }
    }
    public void save(Korisnik user)
    {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        if(Objects.equals(user.getMail(), "hristijan.kolevski099@gmail.com"))
        {
            Korisnik newuser=new Korisnik(user.getMail(),encodedPassword,"ADMIN");
            userRepository.save(newuser);
        }
        else
        {

            Korisnik newuser=new Korisnik(user.getMail(),encodedPassword,"USER");
            userRepository.save(newuser);
        }
    }


@Override
public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
    Korisnik user = userRepository.findByMail(mail);
    if (user == null) {
        throw new UsernameNotFoundException("User not found");
    }

    return org.springframework.security.core.userdetails.User
            .withUsername(user.getMail())
            .password(user.getPassword())
            .authorities(new SimpleGrantedAuthority(user.getRole())) // 👈 Не додава "ROLE_", ја користи точно улогата од базата
            .build();
}
}
