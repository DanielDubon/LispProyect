package UI;

import java.util.ArrayList;
import java.util.Scanner;

import static Controller.Expresion.parse;
import static Controller.InterpreteLisp.*;

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
            ArrayList<String> prefix = toPrefix(components);
            ArrayList<String> groupedPrefix = groupExpressions(prefix);
            Object result = evaluarExpresion(groupedPrefix);

            System.out.println(result);
        }
    }
}
