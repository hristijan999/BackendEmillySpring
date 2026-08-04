package com.example.emilly_ecomercev2.Service;

import com.example.emilly_ecomercev2.Model.DTO.RobaResponseDTO;
import com.example.emilly_ecomercev2.Model.DTO.RobaViewResponseDTO;
import com.example.emilly_ecomercev2.Model.Roba;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RobaService {

    Page<Roba> findAll(Pageable pageable);

    RobaViewResponseDTO findById(Long id);

    RobaViewResponseDTO findFirstById(Long id);
    Page<RobaResponseDTO> findWithFilters(@Param("type") String type, @Param("pol") String pol, @Param("minPrice") Integer minPrice, @Param("maxPrice") Integer maxPrice, @Param("popust") Boolean popust, Pageable pageable);

    Page<RobaResponseDTO> findAllByType(String type, Pageable pageable);

    Page<Roba> findAllByPriceBetween(int minPrice, int maxPrice, Pageable pageable);
    Page<Roba> findAllByPriceBetweenOrderByPriceAsc(int minPrice, int maxPrice, Pageable pageable);
    Page<Roba> findAllByPriceBetweenOrderByPriceDesc(int minPrice, int maxPrice, Pageable pageable);

    public void deleteById(Long id);
    public void save(Roba roba);
    public void update(Roba roba, Long id);
    List<Roba> findAllById(Iterable<Long> ids);
}
