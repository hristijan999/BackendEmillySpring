package com.example.emilly_ecomercev2.Repository;

import com.example.emilly_ecomercev2.Model.Roba;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RobaRepository extends JpaRepository<Roba,Long> {
    @Override
    List<Roba> findAll();

    @Override
    Optional<Roba> findById(Long id);
    Roba findFirstById(Long id);






    Page<Roba> findAllByType(String type, Pageable pageable);
    Page<Roba> findAllByPriceBetween(int minPrice, int maxPrice, Pageable pageable);
    Page<Roba> findAllByPriceBetweenOrderByPriceAsc(int minPrice, int maxPrice, Pageable pageable);
    Page<Roba> findAllByPriceBetweenOrderByPriceDesc(int minPrice, int maxPrice, Pageable pageable);
}
