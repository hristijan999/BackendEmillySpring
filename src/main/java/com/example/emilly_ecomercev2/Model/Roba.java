package com.example.emilly_ecomercev2.Model;

import jakarta.persistence.*;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Roba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String pol;
    String type;
    String materijal;
    int price;


    String opis;

    String detalenOpis;

    @ElementCollection
    List<String> lista_Sliki=new ArrayList<>();

    @ElementCollection
    List<String> lista_Size=new ArrayList<>();

    String sizePicked;

    @Column(nullable = false)
    Boolean popust=false;

    int cenaSoPopust=0;


    public Roba()
    {
        cenaSoPopust=1;
    }

    public Roba(String opis,String pol,String detalenOpis,String type, int price, List<String> lista_Sliki, List<String> lista_Size,int cenaSoPopust,Boolean popust,String materijal) {
        this.materijal=materijal;
        this.type = type;
        this.pol=pol;
        this.price = price;
        this.detalenOpis=detalenOpis;
        this.lista_Sliki = lista_Sliki;
        this.lista_Size = lista_Size;
        this.cenaSoPopust=cenaSoPopust;
        this.opis=opis;
        this.popust=popust;
    }



}
