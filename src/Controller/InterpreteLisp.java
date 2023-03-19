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



    public static void setq(String variable, String value) {
        variables.put(variable, value);
    }

    public static void quote(String variable) {
        if (variables.containsKey(variable)){
            System.out.println(variable);
        }else {
            System.out.println("ERROR NO SE ENCONTRO VARIABLE");
        }

    }



    public static void evaluarExpresion(ArrayList<String> tokens) {



        if (isDFunction(tokens.get(0))) {
            System.out.println("es una funcion uwu");
            List<String> functionData = functionMap.get(tokens.get(0));
            System.out.println("FUNCTION DATA: "+ functionData);
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
            System.out.println("Body Index "+bodyStartIndex);


            List<String> parameters = functionData.subList(0, bodyStartIndex);
            System.out.println("PARAMETROS "+ parameters);
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
            System.out.println("TOKENS REMPLAZADOS" + replacedTokens);

            // Actualizar tokens con los argumentos reemplazados y llamar a evaluarExpresion
            tokens.clear();
            System.out.println("TOKENS" + tokens);
            System.out.println("REPLACEDTOKENS" + replacedTokens);
            tokens.addAll(replacedTokens);
            evaluarExpresion(tokens);
            return;
        }

        for (int i = 0; i < tokens.size(); i++) {
            if (variables.containsKey(tokens.get(i))) {
                tokens.set(i, variables.get(tokens.get(i)));
                System.out.println(variables.get(tokens.get(i)));
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
            return;
        }

        if (tokens.get(0).equals("defun")){
            saveDefun(tokens);
            return;
        }

        if (tokens.get(0).equals("setq") && tokens.size() == 3){
            setq(tokens.get(1),tokens.get(2));
            return;
        }

        if (tokens.get(0).equals("quote") && tokens.size() == 2){
            quote(tokens.get(1));
            return;
        }



        if(tokens.get(0).equals("<") || tokens.get(0).equals(">")  || tokens.get(0).equals("=") || tokens.get(0).equals("ATOM")|| tokens.get(0).equals("LIST")){

            System.out.println(condicionales(tokens));
            return;
        }




        Stack<Integer> stack = new Stack<>();
        for (int i = tokens.size() - 1; i >= 0; i--) {
            System.out.println(tokens);
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
        System.out.println(result);
    }



    private static boolean isNumber(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

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

    private static int precedence(String token) {
        if (token.equals("+") || token.equals("-")) {
            return 1;
        } else if (token.equals("*") || token.equals("/")) {
            return 2;
        } else {
            return 0;
        }
    }



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

    public static ArrayList<String> groupExpressions(ArrayList<String> prefix) {
        ArrayList<String> groups = new ArrayList<>();
        Stack<ArrayList<String>> stack = new Stack<>();

        for (int i = prefix.size() - 1; i >= 0; i--) {
            String token = prefix.get(i);

            if (isOperator(token)) {
                ArrayList<String> group = new ArrayList<>();
                group.add(token);
                group.addAll(stack.pop());
                group.addAll(stack.pop());
                stack.push(group);
            } else {
                ArrayList<String> group = new ArrayList<>();
                group.add(token);
                stack.push(group);
            }
        }

        groups.addAll(stack.pop());
        return groups;
    }


}
