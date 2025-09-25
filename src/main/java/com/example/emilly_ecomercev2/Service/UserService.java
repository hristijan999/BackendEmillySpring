package com.example.emilly_ecomercev2.Service;

import com.example.emilly_ecomercev2.Model.User;
import com.example.emilly_ecomercev2.Repository.UserRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserService  {
    User findByMail(@Param("mail") String mail);
    Optional<User> findById(@Param("id") Long id);


//    liked Images List
    List<Long> findLikedImageIdsByUsername(@Param("mail") String mail);
    void updatedLikedImageIdsByUsername( Long id,String mail);
    public void deleteLikedId(Long id, String mail);
    public void save(User user);
}
