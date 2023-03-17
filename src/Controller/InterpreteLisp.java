package Controller;

import java.util.*;

import static Controller.Expresion.parse;



public class InterpreteLisp {

    private static Set<ArrayList<String>> evaluatedExpressions = new HashSet<>();
    public static void main(String[] args) {
        String LISP = "(+ 5 (* 2 3) )";
        ArrayList<String> components = parse(LISP);
        ArrayList<String> prefix = toPrefix(components);
        System.out.println(prefix);
        ArrayList<String> groups = groupExpressions(prefix);
        System.out.println(groups);
        System.out.println(evaluarExpresion(groups));

    }


    public static int evaluarExpresion(ArrayList<String> tokens) {
        if (evaluatedExpressions.contains(tokens)) {
            // Si la expresión ya ha sido evaluada, obtenemos el resultado del HashSet
            return evaluatedExpressions.hashCode();
        }

        Stack<Integer> stack = new Stack<>();
        for (int i = tokens.size() - 1; i >= 0; i--) {
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



    public static ArrayList<String> toPrefix(ArrayList<String> infix) {
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
