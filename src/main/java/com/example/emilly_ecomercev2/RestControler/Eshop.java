package com.example.emilly_ecomercev2.RestControler;

import com.example.emilly_ecomercev2.Model.Roba;
import com.example.emilly_ecomercev2.Service.RobaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/eshop")
public class  Eshop {

    public final RobaService robaService;

    public Eshop(RobaService robaService) {
        this.robaService = robaService;
    }

    @GetMapping("/findAll")
    public Page<Roba> findAll(Pageable pageable) {

        return robaService.findAll(pageable);
    }

        @GetMapping("findById/{id}")
        public Roba findById(@PathVariable Long id) {
            return robaService.findFirstById(id);
        }

    @GetMapping("/findAllByType")
    public Page<Roba> findAllByType(@RequestParam String type, Pageable pageable) {
        return robaService.findAllByType(type, pageable);
    }
    @GetMapping("/pageablewithfilter")
    public Page<Roba> findAllByTypeAndPriceBetween(
            @RequestParam String type,
            @RequestParam int minPrice,
            @RequestParam int maxPrice,
            Pageable pageable) {
        return robaService.findWithFilters(type, null, minPrice, maxPrice, pageable);
    }

    @GetMapping("/filter/{minPrice}/{maxPrice}/{type}/{pol}")
    public Page<Roba> filterProducts(
            @PathVariable Integer minPrice,
            @PathVariable Integer maxPrice,
            @PathVariable String type,
            @PathVariable String pol,
            Pageable pageable) {
        // Use "all" or "null" as path variable value to skip filter
        String typeFilter = "all".equalsIgnoreCase(type) ? null : type;
        String polFilter = "all".equalsIgnoreCase(pol) ? null : pol;
        Integer minPriceFilter = minPrice == 0 ? null : minPrice;
        Integer maxPriceFilter = maxPrice == 0 ? null : maxPrice;


        return robaService.findWithFilters(typeFilter, polFilter, minPriceFilter, maxPriceFilter, pageable);
    }

    @GetMapping("/findAllByPriceBetween")
    public Page<Roba> findAllByPriceBetween(
            @RequestParam int minPrice,
            @RequestParam int maxPrice,
            Pageable pageable) {
        return robaService.findAllByPriceBetween(minPrice, maxPrice, pageable);
    }

    @GetMapping("/findAllByPriceBetweenOrderByPriceAsc")
    public Page<Roba> findAllByPriceBetweenOrderByPriceAsc(
            @RequestParam int minPrice,
            @RequestParam int maxPrice,
            Pageable pageable) {
        return robaService.findAllByPriceBetweenOrderByPriceAsc(minPrice, maxPrice, pageable);
    }

    @GetMapping("/findAllByPriceBetweenOrderByPriceDesc")
    public Page<Roba> findAllByPriceBetweenOrderByPriceDesc(
            @RequestParam int minPrice,
            @RequestParam int maxPrice,
            Pageable pageable) {
        return robaService.findAllByPriceBetweenOrderByPriceDesc(minPrice, maxPrice, pageable);
    }

}
