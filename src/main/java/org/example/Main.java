package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.SyndFeedOutput;
import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {
    static String nombrePag;
    static String descPag;
    static ArrayList<Libro> libros = new ArrayList<>();
    static ArrayList<Personaje> personajes = new ArrayList<>();
    static JsonNode librosNode;
    static JsonNode personajesNode;

    public static void main(String[] args) {
        try {
            if (validateSchema()){
                getINI();
                fillArrays();
                generateList();
                generateInfo();
                generateRSS();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static boolean validateSchema() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        //Herramienta para leer el schema
        JsonSchemaFactory factory =
                JsonSchemaFactory
                        .builder(JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012))
                        .objectMapper(mapper)
                        .build();

        JsonSchema schema = factory.getSchema(Libro.class.getResourceAsStream("/json/schema.json"));
        JsonNode json = mapper.readTree(Libro.class.getResourceAsStream("/json/Libros.json"));

        //Guarda los errores de validar el schema en un set
        Set<ValidationMessage> errores = schema.validate(json);

        //Si esta vacio es que no hay errores, si no lo esta muestra los errores
        if (!errores.isEmpty()) {
            System.out.println("Errores de validación JSON:");
            for (ValidationMessage error : errores) {
                System.out.println(error.getMessage());
            }
            return false;
        } else {
            return true;
        }
    }
    static void getINI() throws IOException, ConfigurationException {
        //Leer .ini
        INIConfiguration ini = new INIConfiguration();
        FileReader reader = new FileReader("src/main/resources/config.ini");
        ini.read(reader);
        //Coger info de .ini
        nombrePag=ini.getSection("info").getProperty("NomLlocWeb").toString();
        descPag=ini.getSection("info").getProperty("TematicaLlocWeb").toString();
    }
    static void fillArrays() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/json/Libros.json");
        JsonNode rootNode = objectMapper.readTree(file);


        librosNode = rootNode.path("libros");
        for (JsonNode libroNode : librosNode) {
            Libro libro = objectMapper.treeToValue(libroNode, Libro.class);
            libros.add(libro);
        }

        personajesNode = rootNode.path("personajes");
        for (JsonNode personajeNode : personajesNode) {
            Personaje personaje = objectMapper.treeToValue(personajeNode, Personaje.class);
            personajes.add(personaje);
        }
    }
    static void generateRSS() {
        ObjectMapper mapper = new ObjectMapper();
        SyndFeed feed= new SyndFeedImpl();
        feed.setFeedType("rss_2.0");
        feed.setTitle(nombrePag);
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
                entry.setComments("Id: "+libroXML.getId()+"\n"+
                        "Personajes: "+libroXML.getPersonajes()+"\n");
                entry.setLink(libroXML.getUrl());

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

                            entries.add(entryPersonaje);
                        }
                    }
                }

                feed.setEntries(entries);
                SyndFeedOutput output = new SyndFeedOutput();

                FileWriter writer = new FileWriter("src/main/resources/outputs/index.rss");
                output.output(feed,writer);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    static void generateList(){
        // Configuración del Resolver de les plantillas(la ruta y la extension del archio)
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("htmlTemplates/");
        templateResolver.setSuffix(".html");

        // Configuración del motor de plantillas
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        // Creación del context con los datos
        Context context = new Context();

        //utilizamos las listas de Libros y las variables del ini
        context.setVariable("libros", libros);
        context.setVariable("nomPag", nombrePag);
        context.setVariable("descPag",descPag);

        // Processament de la plantilla
        String contingutHTML = templateEngine.process("templateLista", context);

        // Imprimir el contingut generat
        System.out.println(contingutHTML);
        writeHTML(contingutHTML,"src/main/resources/outputs/index.html");
    }
    static void generateInfo(){
        for (Libro libro:libros) {
            // Configuración del Resolver de les plantillas
            ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
            templateResolver.setPrefix("htmlTemplates/");
            templateResolver.setSuffix(".html");

            // Configuración del motor de plantillas
            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(templateResolver);

            // Creación del context con los datos
            Context context = new Context();

            //utilizamos las listas de Libros y Personaje y las variables
            context.setVariable("libro", libro);
            context.setVariable("personajes", personajes);
            context.setVariable("nomPag", nombrePag);
            context.setVariable("descPag",descPag);

            // Processament de la plantilla
            String contingutHTML = templateEngine.process("templateInfo", context);

            // Imprimir el contingut generat
            writeHTML(contingutHTML,"src/main/resources/outputs/libros/libro_"+libro.id+".html");
        }
    }
    static void writeHTML(String contingutHTML, String nomFitxer){
        try  {
            BufferedWriter writer = new BufferedWriter(new FileWriter(nomFitxer));
            // Escriure el contingut al fitxer
            writer.write(contingutHTML);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}