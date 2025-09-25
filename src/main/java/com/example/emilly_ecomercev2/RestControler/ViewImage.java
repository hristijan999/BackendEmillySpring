package com.example.emilly_ecomercev2.RestControler;

import com.example.emilly_ecomercev2.Model.Roba;
import com.example.emilly_ecomercev2.Service.RobaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/viewImage")
public class ViewImage {
    public final RobaService robaService;

    public ViewImage(RobaService robaService) {
        this.robaService = robaService;
    }

    @GetMapping("/findById")
    public Optional<Roba> findById(@RequestParam Long id)
    {
        return robaService.findById(id);
    }
    @GetMapping("/findAllByType")
    public Page<Roba> findAllByType(@RequestParam String type , Pageable pageable)
    {
        return robaService.findAllByType(type,pageable);
    }



}
