package com.example.emilly_ecomercev2.RestControler;

import com.example.emilly_ecomercev2.Model.DTO.RobaResponseDTO;
import com.example.emilly_ecomercev2.Model.Roba;
import com.example.emilly_ecomercev2.Service.RobaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("Api/viewImage")
public class ViewImage {
    public final RobaService robaService;

    public ViewImage(RobaService robaService) {
        this.robaService = robaService;
    }

    @GetMapping("/findById/{id}")
    public Optional<Roba> findById(@PathVariable Long id)
    {
        return robaService.findById(id);
    }


    @GetMapping("/findAllByType")
    public Page<RobaResponseDTO> findAllByType(@RequestParam String type , Pageable pageable)
    {
        return robaService.findAllByType(type,pageable);
    }



}
