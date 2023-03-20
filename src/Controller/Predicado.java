package Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Predicado {

    // Predicado ATOM: devuelve verdadero si es un "atomo"

    /**

     Verifica si se cumple una condición dada en la expresión, como ATOM, LIST, <, > o =.

     @param tokens lista de tokens que representan la expresión a evaluar.

     @return true si se cumple la condición, false si no se cumple.

     @throws IllegalArgumentException si la entrada no es válida.
     */

    public static boolean condicionales(ArrayList<String> tokens){
        String condicion = tokens.get(0);

        if (condicion.equals("ATOM")){
            System.out.println(tokens.size());
           return tokens.size() <= 2;
        }

        if (condicion.equals("LIST")){
            System.out.println(tokens.size());
            return tokens.size() >= 3;
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