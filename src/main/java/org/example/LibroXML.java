package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;

public class LibroXML {
    @JacksonXmlProperty(localName = "id")
    int id;
    @JacksonXmlProperty(localName = "titulo")
    String titulo;
    @JacksonXmlProperty(localName = "descripcion")
    String descripcion;
    @JacksonXmlProperty(localName = "autor")
    String autor;
    @JacksonXmlProperty(localName = "personajes")
    ArrayList<Integer> personajes;
    @JacksonXmlProperty(localName = "url")
    String url;

    public LibroXML() {
    }

    public LibroXML(int id, String titulo, String descripcion, String autor, ArrayList<Integer> personajes, String url) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.autor = autor;
        this.personajes = personajes;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public ArrayList<Integer> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(ArrayList<Integer> personajes) {
        this.personajes = personajes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", autor='" + autor + '\'' +
                ", personajes=" + personajes +
                ", url='" + url + '\'' +
                '}';
    }
}
