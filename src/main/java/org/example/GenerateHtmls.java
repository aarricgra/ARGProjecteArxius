package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

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

            JsonNode personajesNode = rootNode.path("personajes");
            ArrayList<Personaje> personajes= new ArrayList<>();
            for (JsonNode personajeNode : personajesNode) {
                Personaje personaje = objectMapper.treeToValue(personajeNode, Personaje.class);
                personajes.add(personaje);
            }

            generateList(libros);
            generateInfo(libros,personajes);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void generateList(ArrayList<Libro> libros){
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

        // Processament de la plantilla
        String contingutHTML = templateEngine.process("templateLista", context);

        // Imprimir el contingut generat
        System.out.println(contingutHTML);
        escriuHTML(contingutHTML,"src/main/resources/outputs/index.html");
    }

    static void generateInfo(ArrayList<Libro> libros,ArrayList<Personaje> personajes){
        for (Libro libro:libros) {
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
            context.setVariable("libro", libro);
            context.setVariable("personajes", personajes);

            // Processament de la plantilla
            String contingutHTML = templateEngine.process("templateInfo", context);

            // Imprimir el contingut generat
            System.out.println(contingutHTML);
            escriuHTML(contingutHTML,"src/main/resources/outputs/libros/libro_"+libro.id+".html");
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