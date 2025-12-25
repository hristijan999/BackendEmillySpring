package com.example.emilly_ecomercev2.Service.Impl;

import com.example.emilly_ecomercev2.Model.Roba;
import com.example.emilly_ecomercev2.Repository.RobaRepository;
import com.example.emilly_ecomercev2.Service.RobaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RobaServiceImpl implements RobaService {

    public final RobaRepository robaRepository;

    public RobaServiceImpl(RobaRepository robaRepository) {
        this.robaRepository = robaRepository;
    }



    @Override
    public Page<Roba> findAll(Pageable pageable) {
        return robaRepository.findAll(pageable);
    }

    @Override
    public Optional<Roba> findById(Long id) {
        return robaRepository.findById(id);
    }

    @Override
    public Roba findFirstById(Long id) {
        return robaRepository.findFirstById(id);
    }

    @Override
    public Page<Roba> findWithFilters(String type, Integer minPrice, Integer maxPrice, Pageable pageable) {
        return robaRepository.findWithFilters(type, minPrice, maxPrice, pageable);
    }


    public Page<Roba> FindAllByType(String type,Pageable pageable) {
        return robaRepository.findAllByType(type, pageable);
    }



    @Override
    public Page<Roba> findAllByType(String type, Pageable pageable) {
        return robaRepository.findAllByType(type, pageable);
    }

    @Override
    public Page<Roba> findAllByPriceBetween(int minPrice, int maxPrice, Pageable pageable) {
        return robaRepository.findAllByPriceBetween(minPrice, maxPrice, pageable);
    }

    @Override
    public Page<Roba> findAllByPriceBetweenOrderByPriceAsc(int minPrice, int maxPrice, Pageable pageable) {
        return robaRepository.findAllByPriceBetweenOrderByPriceAsc(minPrice, maxPrice, pageable);
    }

    @Override
    public Page<Roba> findAllByPriceBetweenOrderByPriceDesc(int minPrice, int maxPrice, Pageable pageable) {
        return robaRepository.findAllByPriceBetweenOrderByPriceDesc(minPrice, maxPrice, pageable);
    }


    public void save(Roba roba)
    {
        robaRepository.save(roba);
    }

    public void deleteById(Long id)
    {
        robaRepository.deleteById(id);
    }
    public void update(Roba roba)
    {
        robaRepository.save(roba);
    }

}
