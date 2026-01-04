package com.example.emilly_ecomercev2.Service;

import com.example.emilly_ecomercev2.Model.Roba;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RobaService {

    Page<Roba> findAll(Pageable pageable);

    Optional<Roba> findById(Long id);
    Roba findFirstById(Long id);
    Page<Roba> findWithFilters(@Param("type") String type, @Param("pol") String pol, @Param("minPrice") Integer minPrice, @Param("maxPrice") Integer maxPrice, Pageable pageable);
    Page<Roba> findAllByType(String type, Pageable pageable);
    Page<Roba> findAllByPriceBetween(int minPrice, int maxPrice, Pageable pageable);
    Page<Roba> findAllByPriceBetweenOrderByPriceAsc(int minPrice, int maxPrice, Pageable pageable);
    Page<Roba> findAllByPriceBetweenOrderByPriceDesc(int minPrice, int maxPrice, Pageable pageable);

    public void deleteById(Long id);
    public void save(Roba roba);
    public void update(Roba roba);
}
