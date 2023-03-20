package UI;

import java.util.ArrayList;
import java.util.Scanner;

import static Controller.Expresion.parse;
import static Controller.InterpreteLisp.*;

/**

 Clase principal que ejecuta el programa del intérprete de LISP. Se encarga de leer el input del usuario,

 parsearlo y evaluar las expresiones ingresadas en notación polaca inversa.

 */

public class DriverProgram {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.print("Codigo en LISP: ");
            String input = scanner.nextLine();

            if (input.equals("SALIR")) {
                break;
            }

            ArrayList<String> components = parse(input);
            ArrayList<String> ORDTKNS = toPostfix(components);
            //System.out.println(ORDTKNS);
            evaluarExpresion(ORDTKNS);


        }
    }
}
