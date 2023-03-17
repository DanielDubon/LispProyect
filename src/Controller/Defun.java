package Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Defun {

    private Map<String, List<String>> functionMap = new HashMap<>();

    public void addFunction(String functionName, List<String> parameters, List<String> expressions) {
        functionMap.put(functionName, parameters);
        functionMap.put(functionName, expressions);
    }

    public List<String> getParameters(String functionName) {
        return functionMap.get(functionName + "-params");
    }

    public List<String> getExpressions(String functionName) {
        return functionMap.get(functionName + "-expressions");
    }
}
