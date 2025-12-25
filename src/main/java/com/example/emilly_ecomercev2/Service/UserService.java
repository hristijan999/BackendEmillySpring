package com.example.emilly_ecomercev2.Service;

import com.example.emilly_ecomercev2.Model.Korisnik;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserService  {
    Korisnik findByMail(@Param("mail") String mail);
    Optional<Korisnik> findById(@Param("id") Long id);


//    liked Images List
    List<Long> findLikedImageIdsByUsername(@Param("mail") String mail);
    void updatedLikedImageIdsByUsername( Long id,String mail);
    public void deleteLikedId(Long id, String mail);
    public void save(Korisnik user);
}
