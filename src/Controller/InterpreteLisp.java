package Controller;

import java.util.*;

import static Controller.Defun.*;
import static Controller.Defun.saveDefun;
import static Controller.COND.*;
import static Controller.Expresion.parse;
import static Controller.Predicado.condicionales;


import Controller.Defun;



public class InterpreteLisp {

    private static Map<String, String> variables = new HashMap<>();


    /**
     * Asigna el valor especificado a una variable en el mapa de variables.
     *
     * @param variable El nombre de la variable a la que se le quiere asignar un valor.
     * @param value El valor que se le quiere asignar a la variable.
     */
    public static void setq(String variable, String value) {
        variables.put(variable, value);
    }


    /**
     * Imprime la variable de una variable en el mapa de variables.
     *
     * @param variable El nombre de la variable de la variable que se quiere imprimir.
     *
     * @throws IllegalArgumentException si la variable especificada no existe en el mapa de variables.
     */
    public static void quote(String variable) {
        if (variables.containsKey(variable)){
            System.out.println(variable);
        }else {
            System.out.println("ERROR NO SE ENCONTRO VARIABLE");
        }

    }

    /**

     Evalúa una expresión en lenguaje Lisp.

     @param tokens la lista de tokens que representan la expresión a evaluar.

     @throws IllegalArgumentException si hay un token inválido o una función no definida.
     */





    public static int evaluarExpresion(ArrayList<String> tokens) {



        if (isDFunction(tokens.get(0))) {
            //System.out.println("es una funcion");
            List<String> functionData = functionMap.get(tokens.get(0));
            // System.out.println("FUNCTION DATA: "+ functionData);
            if (functionData == null) {
                throw new IllegalArgumentException("Error: función no definida: " + tokens.get(0));
            }

            int bodyStartIndex = 0;
            for (int i = 1; i < functionData.size(); i++) {
                if (isOperator(functionData.get(i))) {
                    bodyStartIndex = i;
                    break;
                }
            }
            //System.out.println("Body Index "+bodyStartIndex);


            List<String> parameters = functionData.subList(0, bodyStartIndex);
           // System.out.println("PARAMETROS "+ parameters);
            List<String> functionBody = functionData.subList(bodyStartIndex, functionData.size());
            ArrayList<String> replacedTokens = new ArrayList<>(functionBody);

            // Eliminar el nombre de la función y guardar los argumentos
            tokens.remove(0);
            List<String> arguments = new ArrayList<>(tokens.subList(0, parameters.size()));

            if (parameters.size() != arguments.size()) {
                throw new IllegalArgumentException("Error: no hay suficientes argumentos para la función " + tokens.get(0));
            }

            for (int i = 0; i < replacedTokens.size(); i++) {
                String token = replacedTokens.get(i);
                for (int j = 0; j < parameters.size(); j++) {
                    if (token.equals(parameters.get(j))) {
                        replacedTokens.set(i, arguments.get(j));
                        break;
                    }
                }
            }

            // Recursividad
           // System.out.println("TOKENS REMPLAZADOS" + replacedTokens);

            // Actualizar tokens con los argumentos reemplazados y llamar a evaluarExpresion
            tokens.clear();
           // System.out.println("TOKENS" + tokens);
            // System.out.println("REPLACEDTOKENS" + replacedTokens);
            tokens.addAll(replacedTokens);
            System.out.println(evaluarExpresion(tokens));

        }

        for (int i = 0; i < tokens.size(); i++) {
            if (variables.containsKey(tokens.get(i))) {
                tokens.set(i, variables.get(tokens.get(i)));
                //  System.out.println(variables.get(tokens.get(i)));
            }
        }


        if (tokens.get(0).equals("cond")){
            if (conditionevaluation(tokens)){
                System.out.println("se cumplio la condicion");
                tokens.remove(0);
                tokens.remove(0);
                tokens.remove(0);
                tokens.remove(0);
                evaluarExpresion(tokens);
            }
            return 0;
        }

        if (tokens.get(0).equals("defun")){
            saveDefun(tokens);
            return 0;
        }

        if (tokens.get(0).equals("setq") && tokens.size() == 3){
            setq(tokens.get(1),tokens.get(2));
            return 0;
        }

        if (tokens.get(0).equals("quote") && tokens.size() == 2){
            quote(tokens.get(1));
            return 0;
        }



        if(tokens.get(0).equals("<") || tokens.get(0).equals(">")  || tokens.get(0).equals("=") || tokens.get(0).equals("ATOM")|| tokens.get(0).equals("LIST")){

            System.out.println(condicionales(tokens));
            return 0;
        }




        Stack<Integer> stack = new Stack<>();
        for (int i = tokens.size() - 1; i >= 0; i--) {
            //System.out.println(tokens);
            String token = tokens.get(i);
            if (isNumber(token)) {
                stack.push(Integer.parseInt(token));
            } else if (isOperator(token)) {
                int operand1 = stack.pop();
                int operand2 = stack.pop();
                int result = applyOperator(token, operand1, operand2);
                stack.push(result);
            } else {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }
        int result = stack.pop();

        return result;
    }

    /**

     Comprueba si una cadena dada es un número entero válido.
     @param token La cadena a comprobar.
     @return true si la cadena es un número entero válido, false en caso contrario.
     */

    private static boolean isNumber(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**

     Comprueba si el token dado es un operador válido.
     @param token El token a comprobar.
     @return true si el token es un operador, false en caso contrario.
     */
    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }


    /**
     * Aplica la operación indicada en los operandos proporcionados.
     *
     * @param operator  El operador que se va a aplicar.
     * @param operand1  El primer operando.
     * @param operand2  El segundo operando.
     * @return El resultado de aplicar la operación a los operandos.
     * @throws IllegalArgumentException si el operador no es válido.
     */

    private static int applyOperator(String operator, int operand1, int operand2) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    /**

     Devuelve la precedencia de un operador.
     @param token El operador a evaluar.
     @return La precedencia del operador. 1 para '+' y '-', 2 para '*' y '/', 0 para cualquier otro operador.
     */

    private static int precedence(String token) {
        if (token.equals("+") || token.equals("-")) {
            return 1;
        } else if (token.equals("*") || token.equals("/")) {
            return 2;
        } else {
            return 0;
        }
    }

    /**

     Convierte una expresión en notación infija a notación postfija.

     @param infix lista de tokens que representan la expresión en notación infija.

     @return lista de tokens que representan la expresión en notación postfija.
     */

    public static ArrayList<String> toPostfix(ArrayList<String> infix) {
        ArrayList<String> prefix = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        for (int i = infix.size() - 1; i >= 0; i--) {
            String token = infix.get(i);

            if (isOperator(token)) {
                while (!stack.isEmpty() && precedence(token) < precedence(stack.peek())) {
                    prefix.add(stack.pop());
                }
                stack.push(token);
            } else if (token.equals(")")) {
                stack.push(token);
            } else if (token.equals("(")) {
                while (!stack.peek().equals(")")) {
                    prefix.add(stack.pop());
                }
                stack.pop();
            } else {
                prefix.add(token);
            }
        }

        while (!stack.isEmpty()) {
            prefix.add(stack.pop());
        }

        Collections.reverse(prefix);
        return prefix;
    }




}
