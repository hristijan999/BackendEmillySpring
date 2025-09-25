package com.example.emilly_ecomercev2.RestControler.Admin;

import com.example.emilly_ecomercev2.Model.Roba;
import com.example.emilly_ecomercev2.Service.RobaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Admin/eshopGRUD")
public class EshopGRUD {

    public final RobaService robaService;

    public EshopGRUD(RobaService robaService) {
        this.robaService = robaService;
    }

    @PostMapping("/save")
    public void save(Roba roba)
    {
        System.out.println("vleze vo grub /save");
//        robaService.save(roba);
    }

    @DeleteMapping("/delete")
    public void delete(Long id)
    {
        robaService.deleteById(id);
    }

    @PutMapping("update")
    public void update(Roba roba)
    {
        robaService.update(roba);
    }

}
