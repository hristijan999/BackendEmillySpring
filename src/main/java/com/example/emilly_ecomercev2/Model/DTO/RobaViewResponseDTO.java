package com.example.emilly_ecomercev2.Model.DTO;

import java.util.List;
import java.util.Set;

public record RobaViewResponseDTO(
    Long id,
    String type,
    int price,
    String opis,
    String detalenOpis,
    List<String> lista_Sliki,
    Set<String> lista_Size,
    Boolean popust,
    int cenaSoPopust
)
{}
