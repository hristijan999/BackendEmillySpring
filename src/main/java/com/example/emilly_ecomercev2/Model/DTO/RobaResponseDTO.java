package com.example.emilly_ecomercev2.Model.DTO;

import java.util.List;

public record RobaResponseDTO(
    Long id,
    String pol,
    String type,
    int price,
    String opis,
    String detalenOpis,
    List<String> lista_Sliki,
    String sizePicked,
    Boolean popust,
    int cenaSoPopust
){}
