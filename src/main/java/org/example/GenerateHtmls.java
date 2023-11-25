package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.SyndFeedOutput;
import org.apache.commons.configuration2.INIConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GenerateHtmls {
    public static void main(String[] args) {
        try {
            //Leer .ini
            INIConfiguration ini = new INIConfiguration();
            FileReader reader = new FileReader("src/main/resources/config.ini");
            ini.read(reader);
            //Coger info de .ini
            String nombrePag=ini.getSection("info").getProperty("NomLlocWeb").toString();
            String descPag=ini.getSection("info").getProperty("TematicaLlocWeb").toString();

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

            generateList(libros,nombrePag,descPag);
            generateInfo(libros,personajes,nombrePag,descPag);
            generateRSS(librosNode,personajesNode,nombrePag,descPag);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generateRSS(JsonNode librosNode, JsonNode personajesNode,String nomPag,String descPag) {
        ObjectMapper mapper = new ObjectMapper();
        SyndFeed feed= new SyndFeedImpl();
        feed.setFeedType("rss_2.0");
        feed.setTitle(nomPag);
        feed.setLink("http://localhost:63342/ARGProjecteArxius/ARGProjecteArxius.main/outputs/index.html?_ijt=lti4ilad4n7ci99mefgj6lukjj&_ij_reload=RELOAD_ON_SAVE");
        feed.setDescription(descPag);

        List<SyndEntry> entries = new ArrayList<>();
        try {
            for (JsonNode libro:librosNode){
                LibroXML libroXML = mapper.treeToValue(libro,LibroXML.class);
                SyndEntry entry = new SyndEntryImpl();

                entry.setTitle(libroXML.getTitulo());
                SyndContent desc = new SyndContentImpl();
                desc.setType("text/plain");
                desc.setValue(libroXML.getDescripcion());
                entry.setDescription(desc);
                entry.setComments("Id: "+libroXML.getId());
                entry.setComments("Personajes: "+libroXML.getPersonajes());
                entry.setComments("Url: "+libroXML.getUrl());

                entries.add(entry);

                for (int id:libroXML.getPersonajes()){
                    for (JsonNode personaje:personajesNode){
                        PersonajeXML personajeXML = mapper.treeToValue(personaje,PersonajeXML.class);
                        if(personajeXML.getId()==id){
                            SyndEntry entryPersonaje = new SyndEntryImpl();

                            entryPersonaje.setTitle(personajeXML.getNombre());
                            SyndContent descPersonaje = new SyndContentImpl();
                            descPersonaje.setType("text/plain");
                            descPersonaje.setValue(personajeXML.getDescripcion());
                            entryPersonaje.setDescription(desc);
                            entryPersonaje.setComments("Id: "+personajeXML.getId());
                            entryPersonaje.setLink("http://localhost:63342/ARGProjecteArxius/ARGProjecteArxius.main/outputs/libros/libro_"+id+".html?_ijt=65b5ugnttrg9pj1ne2cft2bgdg&_ij_reload=RELOAD_ON_SAVE");

                            entries.add(entry);
                        }
                    }
                }

                feed.setEntries(entries);
                SyndFeedOutput output = new SyndFeedOutput();

                FileWriter writer = new FileWriter("src/main/resources/outputs/index.rss");
                output.output(feed,writer);
            }
        }catch (Exception e){

        }

    }


    static void generateList(ArrayList<Libro> libros,String nomPag,String descPag){
        // Configuración del Resolver de les plantillas
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("html/");
        templateResolver.setSuffix(".html");

        // Configuración del motor de plantillas
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        // Creación del context con los datos
        Context context = new Context();

        //utilizamos las listas de Libros y las variables del ini
        context.setVariable("libros", libros);
        context.setVariable("nomPag",nomPag);
        context.setVariable("descPag",descPag);

        // Processament de la plantilla
        String contingutHTML = templateEngine.process("templateLista", context);

        // Imprimir el contingut generat
        System.out.println(contingutHTML);
        escriuHTML(contingutHTML,"src/main/resources/outputs/index.html");
    }

    static void generateInfo(ArrayList<Libro> libros,ArrayList<Personaje> personajes,String nomPag,String descPag){
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

            //utilizamos las listas de Libros y Personaje y las variables
            context.setVariable("libro", libro);
            context.setVariable("personajes", personajes);
            context.setVariable("nomPag",nomPag);
            context.setVariable("descPag",descPag);

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