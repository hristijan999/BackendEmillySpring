package com.example.emilly_ecomercev2.Service.Impl;

import com.example.emilly_ecomercev2.Model.User;
import com.example.emilly_ecomercev2.Repository.UserRepository;
import com.example.emilly_ecomercev2.Service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    public final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User findByMail(String mail) {
        return userRepository.findByMail(mail);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<Long> findLikedImageIdsByUsername(String mail) {
        return userRepository.findLikedImageIdsByUsername(mail);
    }

    @Override
    public void updatedLikedImageIdsByUsername(Long id,String mail) {
        User user=userRepository.findByMail(mail);

        if (user == null) {
            throw new RuntimeException("User not found");
        }
        List<Long>list=user.getLikedImages();
        list.add(id);
        userRepository.save(user);
    }

    public void deleteLikedId(Long id, String mail) {
        User user = userRepository.findByMail(mail);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        List<Long> likedImages = user.getLikedImages();

        if (likedImages.contains(id)) {
            likedImages.remove(id);
            userRepository.save(user);
        }
    }
    public void save(User user)
    {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        User user = userRepository.findByMail(mail);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getMail())
                .password(user.getPassword()) // Should already be encoded!
                .roles(user.getRole()) // e.g. "ADMIN" or "USER"
                .build();
    }
}
