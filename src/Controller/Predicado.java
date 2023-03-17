package Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Predicado {

    // Predicado ATOM: devuelve verdadero si el término es una cadena de caracteres
    public static boolean ATOM(Object term) {
        return term instanceof String;
    }

    // Predicado LIST: devuelve verdadero si el término es una lista
    public static boolean LIST(Object term) {
        if (term instanceof List) {
            List<?> list = (List<?>) term;
            for (Object element : list) {
                if (!(element instanceof Object)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // Predicado EQUAL: devuelve verdadero si los términos son iguales
    public static boolean EQUAL(Object term1, Object term2) {
        if (term1 instanceof List && term2 instanceof List) {
            List<?> list1 = (List<?>) term1;
            List<?> list2 = (List<?>) term2;
            if (list1.size() != list2.size()) {
                return false;
            }
            for (int i = 0; i < list1.size(); i++) {
                if (!EQUAL(list1.get(i), list2.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return term1.equals(term2);
    }

    // Predicado <: devuelve verdadero si el primer término es menor que el segundo término
    public static boolean MENORQUE(Object term1, Object term2) {
        if (term1 instanceof Integer && term2 instanceof Integer) {
            return ((Integer) term1).compareTo((Integer) term2) < 0;
        } else if (term1 instanceof String && term2 instanceof String) {
            return ((String) term1).compareTo((String) term2) < 0;
        } else {
            throw new IllegalArgumentException("Los términos deben ser de tipo Integer o String");
        }
    }

    // Predicado >: devuelve verdadero si el primer término es mayor que el segundo término
    public static boolean MAYORQUE(Object term1, Object term2) {
        if (term1 instanceof Integer && term2 instanceof Integer) {
            return ((Integer) term1).compareTo((Integer) term2) > 0;
        } else if (term1 instanceof String && term2 instanceof String) {
            return ((String) term1).compareTo((String) term2) > 0;
        } else {
            throw new IllegalArgumentException("Los términos deben ser de tipo Integer o String");
        }
    }

}