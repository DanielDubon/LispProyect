package Tests;
import Controller.InterpreteLisp;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static Controller.Expresion.parse;
import static Controller.InterpreteLisp.evaluarExpresion;
import static Controller.InterpreteLisp.toPostfix;
import static Controller.Predicado.condicionales;
import static org.junit.jupiter.api.Assertions.*;

public class JUnitTests {

    @Test
    void testSuma() {

        ArrayList<String> components = parse("(+ 3 4)");
        ArrayList<String> ORDTKNS = toPostfix(components);
        int resultado =    evaluarExpresion(ORDTKNS);
        assertEquals(7, resultado);
    }
    @Test
    void testResta() {

        ArrayList<String> components = parse("(- 5 2)");
        ArrayList<String> ORDTKNS = toPostfix(components);
        int resultado = evaluarExpresion(ORDTKNS);
        assertEquals(3, resultado);
    }


    @Test
    void testDefunAndFunctionCall() {

        evaluarExpresion(toPostfix(parse("(defun suma (x y) (+ x y))")));

        ArrayList<String> components =parse("(suma 8 4)");
        ArrayList<String> ORDTKNS = toPostfix(components);
        System.out.println(ORDTKNS);
        int resultado = evaluarExpresion(ORDTKNS);
        assertEquals(12, resultado);
    }


    @Test
    void testmayorq() {



        ArrayList<String> components =parse("(> 10 4)");
        ArrayList<String> ORDTKNS = toPostfix(components);

        boolean resultado = condicionales(ORDTKNS);
        assertTrue(resultado);
    }




}
