package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.*;
import java.util.ArrayList;

public class GenerateHtmlGeneral {
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

            JsonNode personajesNode = rootNode.path("personajes");
            ArrayList<Personaje> personajes= new ArrayList<>();
            for (JsonNode personajeNode : personajesNode) {
                Personaje personaje = objectMapper.treeToValue(personajeNode, Personaje.class);
                personajes.add(personaje);
            }

            // Configuración del Resolver de les plantillas
            ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
            templateResolver.setPrefix("html/");
            templateResolver.setSuffix(".html");

            // Configuración del motor de plantillas
            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(templateResolver);

            // Creación del context con los datos
            Context context = new Context();

            //utilizamos las listas de Libros y Personaje
            context.setVariable("libros", libros);
            context.setVariable("personajes", personajes);

            // Processament de la plantilla
            String contingutHTML = templateEngine.process("templateGeneral", context);

            // Imprimir el contingut generat
            System.out.println(contingutHTML);
            escriuHTML(contingutHTML,"src/main/resources/outputs/index.html");

            System.out.println(personajes.toString());
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