package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Serializar {
    public static void main(String[] args) {
        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//            ArrayList<Artista> artistas = new ArrayList<>();
//            artistas.add(new Artista("Beyoncé", "Pop/R&B", new ArrayList<>(List.of("Halo", "Single Ladies", "Crazy in Love"))));
//            artistas.add(new Artista("Ed Sheeran", "Pop", new ArrayList<>(List.of("Shape of You", "Thinking Out Loud", "Perfect"))));
//            artistas.add(new Artista("Kendrick Lamar", "Rap", new ArrayList<>(List.of("HUMBLE.", "Alright", "DNA."))));
//            artistas.add(new Artista("Adele", "Pop", new ArrayList<>(List.of("Hello", "Someone Like You", "Rolling in the Deep"))));
//
//            ArrayList<Escenario> escenarios = new ArrayList<>();
//            escenarios.add(new Escenario("Escenario Principal", "20:00-21:30", new ArrayList<>(List.of("Beyoncé", "Ed Sheeran"))));
//            escenarios.add(new Escenario("Escenario Rap", "19:45-21:00", new ArrayList<>(List.of("Kendrick Lamar", "Eminem"))));
//            escenarios.add(new Escenario("Escenario Alternativo", "20:15-21:45", new ArrayList<>(List.of("Arctic Monkeys", "Tame Impala"))));
//            escenarios.add(new Escenario("Escenario Pop", "20:30-22:00", new ArrayList<>(List.of("Adele", "Taylor Swift"))));
//
//            Festival festival = new Festival("MusicFest 2024", "El festival de música más grande del año", "20-02-2024", "23-02-2024", artistas, escenarios);
//            File file = new File("src/main/resources/FestivalMusica2.json");
//            objectMapper.writeValue(file, festival);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
