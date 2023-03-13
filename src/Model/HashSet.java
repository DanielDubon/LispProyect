package Model;

import java.util.*;
import java.util.function.Function;

public class HashSet {
    private Map<String, Function> funciones;

    public HashSet() {
        funciones = new HashMap<String, Function>();
    }

    public void agregarFuncion(String nombre, Function funcion) {
        funciones.put(nombre, funcion);
    }

    public Function obtenerFuncion(String nombre) {
        return funciones.get(nombre);
    }


}

