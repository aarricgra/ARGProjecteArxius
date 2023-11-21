package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class GenerateHtmls {
    public static void main(String[] args) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("src/main/resources/json/Libros.json");
            JsonNode rootNode = objectMapper.readTree(file);

            JsonNode librosNode = rootNode.path("libros");
            ArrayList<Libro> libros= new ArrayList<>();
            for (JsonNode libroNode : librosNode) {
                Libro libro = objectMapper.treeToValue(libroNode, Libro.class);
                libros.add(libro);
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void escriuHTML(String contingutHTML, String nomFitxer){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomFitxer))) {
        // Escriure el contingut al fitxer
            writer.write(contingutHTML);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}