package Controller;


import java.util.ArrayList;
import java.util.List;

public class COND {

    public static boolean conditionevaluation(ArrayList<String> tokens){
        String condicion = tokens.get(1);
        try {
            double numero1 = Double.parseDouble(tokens.get(2));
            double numero2 = Double.parseDouble(tokens.get(3));

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
