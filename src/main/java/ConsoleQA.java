import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.lang.*;
import java.lang.System;
import java.util.Scanner;

/**
 * Created by yevv on 14.05.17.
 */
public class ConsoleQA {

    Scanner scanner;

    public ConsoleQA() {
        this.scanner = new Scanner(System.in);
    }

    int getInt() {
        while(true) {
            try {
                return Integer.valueOf(scanner.nextLine());
            }catch(Exception ex) {
                System.out.println("Błędna wartość. Spróbuj ponownie.");
            }
        }
    }

    String getVariableChar() {
        while(true) {
            try {
                String symbol = scanner.nextLine();
                if(symbol.isEmpty()) {
                    throw new Exception("Pusty string");
                }
                char[] charArray = symbol.toCharArray();
                if(Character.isLetter(charArray[0]) || charArray[0] == '_') {
                    for(int i = 1; i < charArray.length; ++i) {
                        if(!Character.isLetter(charArray[i]) && !Character.isDigit(charArray[i]) && charArray[i] != '_') {
                            throw new Exception("Złe znaki.");
                        }
                    }
                } else {
                    throw new Exception("Zły symbol.");
                }
                return symbol;
            } catch (Exception ex) {
                System.out.println("Zły symbol.");
            }
        }
    }

    Equation getEquation(SystemEquation systemEquation) {
        while(true) {
            try {
                String ograniczenie = scanner.nextLine();
                if(ograniczenie.isEmpty()) {
                    throw new Exception("Pusty string");
                }
                if(!ograniczenie.contains("<=") && !ograniczenie.contains(">=") && !ograniczenie.contains("=") && !ograniczenie.contains(">") && !ograniczenie.contains("<")) {
                    throw new Exception("Złe ograniczenie");
                }
                String[] tokens;
                String znak;
                Expression[] wyrazenia;
                if(ograniczenie.contains("<=")) {
                    tokens = ograniczenie.split("<=");
                    znak = "<=";
                }
                else if(ograniczenie.contains(">="))
                {
                    tokens = ograniczenie.split(">=");
                    znak = ">=";
                }
                else if(ograniczenie.contains("="))
                {
                    tokens = ograniczenie.split("=");
                    znak = "=";
                }
                else if(ograniczenie.contains(">"))
                {
                    tokens = ograniczenie.split(">");
                    znak = ">";
                }
                else
                {
                    tokens = ograniczenie.split("<");
                    znak = "<";
                }
                if(tokens.length > 3)
                    throw new Exception("Złe ograniczenie");

                wyrazenia = new Expression[3];
                for(int i = 0; i < tokens.length; ++i) {
                    Expression e = new ExpressionBuilder(tokens[i])
                            .variables(systemEquation.variableSymbolTab)
                            .build();
                    wyrazenia[i] = e;
                }

                return new Equation(ograniczenie, tokens, wyrazenia, znak);
            } catch (Exception ex) {
                System.out.println("Złe ograniczenie.");
            }
        }
    }

    Expression getGoalFunction(SystemEquation systemEquation) {
        while(true) {
            try {
                String goalFunction = scanner.nextLine();
                if(goalFunction.isEmpty())
                    throw new Exception("Pusty string");

                Expression e = new ExpressionBuilder(goalFunction)
                        .variables(systemEquation.variableSymbolTab)
                        .build();

                return e;

            } catch (Exception ex) {
                System.out.println("Błędna funkcja.");
            }
        }
    }

    int getIfMinOrMax() {
        while(true) {
            try {
                int tmp = Integer.valueOf(scanner.nextLine());
                if(tmp != 1 && tmp != 2)
                    throw new Exception("Błędna wartość");
                return tmp;
            }catch(Exception ex) {
                System.out.println("Błędna wartość.");
            }
        }
    }

    public void asking(SystemEquation systemEquation) {
        System.out.println("Podaj liczbe zmiennych decyzyjnych: ");
        systemEquation.variableAmong = getInt();

        System.out.println("Podaj liczbe ograniczen: ");
        systemEquation.equationAmong = getInt();

        System.out.println("\n1 - max\n2 - min");
        systemEquation.minOrMax = getIfMinOrMax();

        System.out.println("Podaj symbole zmiennych decyzyjnych: ");
        for(int i = 0; i < systemEquation.variableAmong; ++i)
            systemEquation.variableSymbolList.add(getVariableChar());

        systemEquation.variableSymbolTab = new String[systemEquation.variableAmong];
        for(int i = 0; i < systemEquation.variableAmong; ++i) {
            systemEquation.variableSymbolTab[i] = systemEquation.variableSymbolList.get(i);
        }

        System.out.println("Podaj ograniczenia: ");
        for(int i = 0; i < systemEquation.equationAmong; ++i){
            systemEquation.equationList.add(getEquation(systemEquation));
        }

        System.out.println("Podaj funkcje celu: ");
        systemEquation.goalFunction = getGoalFunction(systemEquation);

    }
}
