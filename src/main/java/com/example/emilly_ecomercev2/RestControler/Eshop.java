package com.example.emilly_ecomercev2.RestControler;

import com.example.emilly_ecomercev2.Model.DTO.RobaResponseDTO;
import com.example.emilly_ecomercev2.Model.DTO.RobaViewResponseDTO;
import com.example.emilly_ecomercev2.Model.Roba;
import com.example.emilly_ecomercev2.Service.RobaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("Api/eshop")
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
        public RobaViewResponseDTO findById(@PathVariable Long id) {
            return robaService.findFirstById(id);
        }

    @GetMapping("/findAllByType")
    public Page<RobaResponseDTO> findAllByType(@RequestParam String type, Pageable pageable) {
        return robaService.findAllByType(type, pageable);
    }
    @GetMapping("/pageablewithfilter")
    public Page<RobaResponseDTO> findAllByTypeAndPriceBetween(
            @RequestParam String type,
            @RequestParam int minPrice,
            @RequestParam int maxPrice,
            @RequestParam boolean popust,
            Pageable pageable) {
        return robaService.findWithFilters(type, null, minPrice, maxPrice,popust, pageable);
    }




    //ova e novoto za da rabotit i ko ke pratam string all namesto boolean
    @GetMapping("/filter/{type}/{pol}/{minPrice}/{maxPrice}/{popust}")
    public Page<RobaResponseDTO> filterProducts(
            @PathVariable String type,  // 1. Прво 'type' бидејќи е прво во патеката
            @PathVariable String pol,   // 2. Второ 'pol'
            @PathVariable Integer minPrice,
            @PathVariable Integer maxPrice,
            @PathVariable String popust,
            Pageable pageable){

    // Use "all" or "null" as path variable value to skip filter
    String typeFilter = "all".equalsIgnoreCase(type) ? null : type;
    String polFilter = "all".equalsIgnoreCase(pol) ? null : pol;
    Integer minPriceFilter = minPrice == 0 ? null : minPrice;
    Integer maxPriceFilter = maxPrice == 0 ? null : maxPrice;

    // Convert the string "all" to null, otherwise parse the boolean "true"/"false"
    Boolean popustFilter = "all".equalsIgnoreCase(popust) ? null : Boolean.parseBoolean(popust);

    // Make sure robaService.findWithFilters accepts uppercase `Boolean` object
    // instead of lowercase primitive `boolean` so it can receive `null`!
    return robaService.findWithFilters(typeFilter, polFilter, minPriceFilter, maxPriceFilter, popustFilter, pageable);
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
