package Controller;

import java.util.ArrayList;

public class Expresion {

    public static ArrayList<String> parse(String expression) {
        ArrayList<String> components = new ArrayList<>();
        int i = 0;
        while (i < expression.length()) {
            if (expression.charAt(i) == '(' || expression.charAt(i) == ')') {
                components.add(Character.toString(expression.charAt(i)));
                i++;
            } else if (expression.charAt(i) == ' ') {
                i++;
            } else {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && expression.charAt(i) != ' ' && expression.charAt(i) != '(' && expression.charAt(i) != ')') {
                    sb.append(expression.charAt(i));
                    i++;
                }
                components.add(sb.toString());
            }
        }
        return components;
    }


}
