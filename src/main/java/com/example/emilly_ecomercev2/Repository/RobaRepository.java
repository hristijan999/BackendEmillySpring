
package com.example.emilly_ecomercev2.Repository;

import com.example.emilly_ecomercev2.Model.Roba;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RobaRepository extends JpaRepository<Roba,Long> {
    @Override
    List<Roba> findAll();



    @Override
    Optional<Roba> findById(Long id);

    Roba findFirstById(Long id);



    @Query("SELECT r FROM Roba r WHERE " +
            "(:type IS NULL OR r.type = :type) AND " +
            "(:pol IS NULL OR r.pol = :pol) AND " +
            "(:minPrice IS NULL OR r.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR r.price <= :maxPrice) AND " +
            "(:popust IS NULL OR r.popust = :popust)")
    Page<Roba> findWithFilters(
            @Param("type") String type,
            @Param("pol") String pol,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("popust") Boolean popust,
            Pageable pageable);

    Page<Roba> findAllByType(String type, Pageable pageable);
    Page<Roba> findAllByPriceBetween(int minPrice, int maxPrice, Pageable pageable);
    Page<Roba> findAllByPriceBetweenOrderByPriceAsc(int minPrice, int maxPrice, Pageable pageable);
    Page<Roba> findAllByPriceBetweenOrderByPriceDesc(int minPrice, int maxPrice, Pageable pageable);
}