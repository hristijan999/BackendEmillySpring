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
    public void save(@RequestBody Roba roba)
    {
        System.out.println("vleze vo grub /save");
        System.out.println(roba);
        robaService.save(roba);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam Long id)
    {
        System.out.println("vleze vo grub delete"+ id);
//        robaService.deleteById(id);
    }

    @PutMapping("/update")
    public void update(@RequestBody Roba roba)
    {
        System.out.println("vleze vo grub update");
        System.out.println(roba);
        robaService.update(roba);
    }

}
