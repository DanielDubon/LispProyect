package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Defun {

    public  static Map<String, List<String>> functionMap = new HashMap<>();

    public static void saveDefun(ArrayList<String> tokens) {
        if (tokens.size() < 3) {
            throw new IllegalArgumentException("Error: defun debe tener al menos tres tokens.");
        }

        String functionName = tokens.get(1);
        System.out.println(functionName);
        List<String> functionBody = tokens.subList(2, tokens.size());
        System.out.println(functionBody);
        functionMap.put(functionName, functionBody);



        }



    public static List<String> getFunction(String functionName) {
        return functionMap.get(functionName);
    }

    public static boolean isDFunction(String functionName){
        if (functionMap.containsKey(functionName)){
            return true;
        }
        return false;
    }


}
