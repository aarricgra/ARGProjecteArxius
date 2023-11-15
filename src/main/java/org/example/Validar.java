package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import java.io.IOException;
import java.util.Set;

public class Validar {
    public static void main(String[] args) throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonSchemaFactory factory = JsonSchemaFactory.builder(JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012))
                    .objectMapper(mapper)
                    .build();

            JsonSchema schema = factory.getSchema(Libro.class.getResourceAsStream("/schema.json"));
            JsonNode json = mapper.readTree(Libro.class.getResourceAsStream("/LibrosMal.json"));

            Set<ValidationMessage> errores = schema.validate(json);

            verificatonDisplay(errores);
        }catch (Exception e){
e.printStackTrace();
        }
    }

    static void verificatonDisplay(Set<ValidationMessage> errores){
        if (!errores.isEmpty()) {
            System.out.println("Errores de validación JSON:");
            for (ValidationMessage error : errores) {
                System.out.println(error.getMessage());
            }
        } else {
            System.out.println("El JSON es válido según el esquema.");
        }
    }
}
