package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Desserealizar {
    public static void main(String[] args) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("src/main/resources/Libros.json");
            JsonNode rootNode = objectMapper.readTree(file);

            JsonNode librosNode = rootNode.path("libros");
            ArrayList<Libro> libros= new ArrayList<>();
            for (JsonNode libroNode : librosNode) {
                Libro libro = objectMapper.treeToValue(libroNode, Libro.class);
                libros.add(libro);
            }

            JsonNode personajesNode = rootNode.path("personajes");
            ArrayList<Personaje> personajes= new ArrayList<>();
            for (JsonNode personajeNode : personajesNode) {
                Personaje personaje = objectMapper.treeToValue(personajeNode, Personaje.class);
                personajes.add(personaje);
            }

            for (Libro a: libros) {
                System.out.println(a.toString());
            }

            for (Personaje a: personajes) {
                System.out.println(a.toString());
            }

            System.out.println(personajes.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}