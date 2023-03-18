package Controller;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expresion {

    public static ArrayList<String> parse(String expression) {
        ArrayList<String> tokens = new ArrayList<>();

        // Reemplaza los paréntesis con un espacio en blanco alrededor para facilitar la tokenización
        expression = expression.replace("(", " ( ").replace(")", " ) ").replace(","," ");

        String[] parts = expression.split("\\s+");

        for (String part : parts) {
            if (!part.isBlank()) {
                tokens.add(part);
            }
        }

        return tokens;


    }

}
