package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Personaje {
    @JsonProperty(value = "id")
    int id;
    @JsonProperty(value = "nombre")
    String nombre;
    @JsonProperty(value = "descripcion")
    String descripcion;

    public Personaje() {
    }

    public Personaje(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Personaje{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
