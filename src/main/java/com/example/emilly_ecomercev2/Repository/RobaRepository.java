
package com.example.emilly_ecomercev2.Repository;

import com.example.emilly_ecomercev2.Model.Roba;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RobaRepository extends JpaRepository<Roba, Long> {

    /**
     * Finds products with optional filters for type, gender (pol), and price range.
     * Any filter can be null, in which case it will be ignored.
     * Supports pagination and sorting via the Pageable parameter.
     */
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

    Roba findFirstById(Long id);

    Page<Roba> findAllByType(String type, Pageable pageable);

    Page<Roba> findAllByPriceBetween(int minPrice, int maxPrice, Pageable pageable);

    Page<Roba> findAllByPriceBetweenOrderByPriceAsc(int minPrice, int maxPrice, Pageable pageable);

    Page<Roba> findAllByPriceBetweenOrderByPriceDesc(int minPrice, int maxPrice, Pageable pageable);
}