package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Defun {

    public  static Map<String, List<String>> functionMap = new HashMap<>();


    /**
     * Guarda una definición de función en un mapa de funciones.
     *
     * @param tokens una lista de tokens que representa la definición de la función
     * @throws IllegalArgumentException si la definición de la función no tiene al menos tres tokens
     */


    public static void saveDefun(ArrayList<String> tokens) {
        if (tokens.size() < 3) {
            throw new IllegalArgumentException("Error: defun debe tener al menos tres tokens.");
        }

        String functionName = tokens.get(1);
        //System.out.println(functionName);
        List<String> functionBody = tokens.subList(2, tokens.size());
        //System.out.println(functionBody);
        functionMap.put(functionName, functionBody);



        }



    /**
     * Devuelve el cuerpo de una función dada su nombre.
     *
     * @param functionName el nombre de la función
     * @return una lista de tokens que representa el cuerpo de la función
     */
    public static List<String> getFunction(String functionName) {
        return functionMap.get(functionName);
    }

    /**
     * Comprueba si una función con el nombre dado está definida.
     *
     * @param functionName el nombre de la función
     * @return true si la función está definida, false de lo contrario
     */
    public static boolean isDFunction(String functionName){
        if (functionMap.containsKey(functionName)){
            return true;
        }
        return false;
    }


}
