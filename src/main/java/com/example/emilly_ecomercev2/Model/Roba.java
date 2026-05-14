package com.example.emilly_ecomercev2.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Roba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String pol;
    String type;

    int price;


    @ElementCollection
    List<String> material=new ArrayList<>();
    String opis;

    String detalenOpis;

    @ElementCollection
    List<String> lista_Sliki=new ArrayList<>();

    @ElementCollection
    List<String> lista_Size=new ArrayList<>();

    String sizePicked;


    Boolean popust;

    int cenaSoPopust=0;






}
