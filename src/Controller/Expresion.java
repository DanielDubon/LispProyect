package Controller;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expresion {

    /**
     * Convierte una expresión de Lisp en una lista de tokens.
     *
     * @param expression una cadena que representa la expresión a analizar
     * @return una lista de tokens que representa la expresión
     */
    public static ArrayList<String> parse(String expression) {
        ArrayList<String> tokens = new ArrayList<>();

        // Reemplaza los paréntesis con un espacio en blanco alrededor para facilitar la tokenización
        expression = expression.replace("(", " ( ").replace(")", " ) ").replace(","," ");


        // Separa la expresión en partes y agrega cada parte como un token
        String[] parts = expression.split("\\s+");

        for (String part : parts) {
            if (!part.isBlank()) {
                tokens.add(part);
            }
        }

        return tokens;


    }

}
