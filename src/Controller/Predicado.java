package Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Predicado {

    // Predicado ATOM: devuelve verdadero si el término es una cadena de caracteres
    public static boolean ATOM(Object term) {
        return term instanceof String;
    }


    public static boolean condicionales(ArrayList<String> tokens){
        String condicion = tokens.get(0);

        if (condicion.equals("ATOM")){
            System.out.println(tokens.size());
           return tokens.size() <= 2;
        }

        try {
            double numero1 = Double.parseDouble(tokens.get(1));
            double numero2 = Double.parseDouble(tokens.get(2));

            if (condicion.equals("<")) {
                return numero1 < numero2;
            } else if (condicion.equals(">")){
                return numero1 > numero2;
            }else {
                return numero1 == numero2;
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Entrada no válida", e);
        }

    }




}