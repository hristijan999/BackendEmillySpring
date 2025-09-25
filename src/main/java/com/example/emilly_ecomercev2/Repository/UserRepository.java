package com.example.emilly_ecomercev2.Repository;

import com.example.emilly_ecomercev2.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.mail = :mail")
    User findByMail(@Param("mail") String mail);

    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> findById(@Param("id") Long id);

    @Query("SELECT k.LikedImages FROM User k WHERE k.mail = :mail")
    List<Long> findLikedImageIdsByUsername(@Param("mail") String mail);


}
