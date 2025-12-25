package com.example.emilly_ecomercev2.Repository;

import com.example.emilly_ecomercev2.Model.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Korisnik, Long> {
    @Query("SELECT u FROM Korisnik u WHERE u.mail = :mail")
    Korisnik findByMail(@Param("mail") String mail);

    @Query("SELECT u FROM Korisnik u WHERE u.id = :id")
    Optional<Korisnik> findById(@Param("id") Long id);

    @Query("SELECT k.LikedImages FROM Korisnik k WHERE k.mail = :mail")
    List<Long> findLikedImageIdsByUsername(@Param("mail") String mail);


}
