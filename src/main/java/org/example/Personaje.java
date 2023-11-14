package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Personaje {
    @JsonProperty(value = "id")
    int id;
    @JsonProperty(value = "nombre")
    String nombre;
    @JsonProperty(value = "descripcion")
    String descripcion;
}
