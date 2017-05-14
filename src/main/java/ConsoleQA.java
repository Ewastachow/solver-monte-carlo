import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.lang.*;
import java.lang.System;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by yevv on 14.05.17.
 */
public class ConsoleQA {

    Scanner scanner;

    public ConsoleQA() {
        this.scanner = new Scanner(System.in);
    }

    int wczytajZKonsoliInta() {
        while(true) {
            try {
                return Integer.valueOf(scanner.nextLine());
            }catch(Exception ex) {
                System.out.println("Błędna wartość. Spróbuj ponownie.");
            }
        }
    }

    double wczytajZKonsoliDouble() {
        while(true) {
            try {
                return Double.valueOf(scanner.nextLine());
            }catch(Exception ex) {
                System.out.println("Błędna wartość. Spróbuj ponownie.");
            }
        }
    }

    String wczytajZKonsoliSymbolZmiennejDecyzyjnej() {
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
                            throw new Exception("Złe dalsze znaki symbolu.");
                        }
                    }
                } else {
                    throw new Exception("Zły pierwszy compareSymbol symbolu.");
                }
                return symbol;
            } catch (Exception ex) {
                System.out.println("Zły symbol. Spróbuj inny.");
            }
        }
    }

    Equation wczytajZKonsoliOgraniczenie(SystemEquation systemEquation) {
        while(true) {
            try {
                String ograniczenie = scanner.nextLine();
                if(ograniczenie.isEmpty()) {
                    throw new Exception("Pusty string");
                }
                if(!ograniczenie.contains("<=") && !ograniczenie.contains(">=") && !ograniczenie.contains("=") && !ograniczenie.contains(">") && !ograniczenie.contains("<")) {
                    throw new Exception("Złe equation");
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
                    throw new Exception("Złe equation");

                wyrazenia = new Expression[3];
                for(int i = 0; i < tokens.length; ++i) {
                    Expression e = new ExpressionBuilder(tokens[i])
                            .variables(systemEquation.variableSymbolTab)
                            .build();
                    wyrazenia[i] = e;
                }

                return new Equation(ograniczenie, tokens, wyrazenia, znak);
            } catch (Exception ex) {
                System.out.println("Złe equation. Spróbuj ponownie.");
            }
        }
    }

    Expression wczytajFunkcjeCelu(SystemEquation systemEquation) {
        while(true) {
            try {
                String fCelu = scanner.nextLine();
                if(fCelu.isEmpty())
                    throw new Exception("Pusty string");

                Expression e = new ExpressionBuilder(fCelu)
                        .variables(systemEquation.variableSymbolTab)
                        .build();

                return e;

            } catch (Exception ex) {
                System.out.println("Błędna funkcja celu. Spróbuj ponownie.");
            }
        }
    }

    int wczytajCzyMaxCzyMin() {
        while(true) {
            try {
                int tmp = Integer.valueOf(scanner.nextLine());
                if(tmp != 1 && tmp != 2)
                    throw new Exception("Błędna wartość");
                return tmp;
            }catch(Exception ex) {
                System.out.println("Błędna wartość. Spróbuj ponownie.");
            }
        }
    }

    public void asking(SystemEquation systemEquation) {
        System.out.println("Podaj liczbe zmiennych decyzyjnych: ");
        systemEquation.variableAmong = wczytajZKonsoliInta();

        System.out.println("Podaj symbole zmiennych decyzyjnych (po kazdym nacisnij ENTER): ");
        for(int i = 0; i < systemEquation.variableAmong; ++i)
            systemEquation.variableSymbolList.add(wczytajZKonsoliSymbolZmiennejDecyzyjnej());

        systemEquation.variableSymbolTab = new String[systemEquation.variableAmong];
        for(int i = 0; i < systemEquation.variableAmong; ++i) {
            systemEquation.variableSymbolTab[i] = systemEquation.variableSymbolList.get(i);
        }

        System.out.println("Podaj liczbe ograniczen: ");
        systemEquation.equationAmong = wczytajZKonsoliInta();

        for(int i = 0; i < systemEquation.equationAmong; ++i){
            systemEquation.equationList.add(wczytajZKonsoliOgraniczenie(systemEquation));
        }

        System.out.println("Podaj funkcje celu: ");
        systemEquation.goalFunction = wczytajFunkcjeCelu(systemEquation);

        System.out.println("Max czy min?\n1 - max\n2 - min");
        systemEquation.minOrMax = wczytajCzyMaxCzyMin();


        double x, y;
        List<Double> listX = new ArrayList<>();
        List<Double> listY = new ArrayList<>();
        double minLubMax = 0;
        Random rand = new Random();
        for(int i = 0; i < 1000; ++i) {
            x = ThreadLocalRandom.current().nextInt((int)(systemEquation.equationList.get(systemEquation.equationList.size() - 2).expressionsTab[0]).evaluate(), ((int)(systemEquation.equationList.get(systemEquation.equationList.size() - 2).expressionsTab[2]).evaluate() + 1));
            //y = ThreadLocalRandom.current().nextInt((int)(SMC.equationList.get(SMC.equationList.size() - 1).expressionsTab[0]).evaluate(), ((int)(SMC.equationList.get(SMC.equationList.size() - 1).expressionsTab[2]).evaluate() + 1));
            y = ((double)2/3)*x;
            boolean czySpelnia = true;
            for(int j = 0; j < systemEquation.equationList.size() - systemEquation.variableAmong; ++j) {

                System.out.println("" + systemEquation.equationList.get(j).expressionsTab[0].setVariable("x", x).setVariable("y", y).evaluate() + systemEquation.equationList.get(j).compareSymbol + systemEquation.equationList.get(j).expressionsTab[1].setVariable("x", x).setVariable("y", y).evaluate());

                if(systemEquation.equationList.get(j).compareSymbol.equalsIgnoreCase("<=")) {
                    if(!(systemEquation.equationList.get(j).expressionsTab[0].setVariable("x", x).setVariable("y", y).evaluate() <= systemEquation.equationList.get(j).expressionsTab[1].setVariable("x", x).setVariable("y", y).evaluate())) {
                        czySpelnia = false;
                        break;
                    }
                } else if(systemEquation.equationList.get(j).compareSymbol.equalsIgnoreCase(">=")) {
                    if(!(systemEquation.equationList.get(j).expressionsTab[0].setVariable("x", x).setVariable("y", y).evaluate() >= systemEquation.equationList.get(j).expressionsTab[1].setVariable("x", x).setVariable("y", y).evaluate())) {
                        czySpelnia = false;
                        break;
                    }
                } /*else if(SMC.equationList.get(j).compareSymbol.equalsIgnoreCase("=")) {
                    if(!(SMC.equationList.get(j).expressionsTab[0].setVariable("x", x).setVariable("y", y).evaluate() == SMC.equationList.get(j).expressionsTab[1].setVariable("x", x).setVariable("y", y).evaluate())) {
                        czySpelnia = false;
                        break;
                    }
                }*/ else if(systemEquation.equationList.get(j).compareSymbol.equalsIgnoreCase(">")) {
                    if(!(systemEquation.equationList.get(j).expressionsTab[0].setVariable("x", x).setVariable("y", y).evaluate() > systemEquation.equationList.get(j).expressionsTab[1].setVariable("x", x).setVariable("y", y).evaluate())) {
                        czySpelnia = false;
                        break;
                    }
                } else if(systemEquation.equationList.get(j).compareSymbol.equalsIgnoreCase("<")) {
                    if(!(systemEquation.equationList.get(j).expressionsTab[0].setVariable("x", x).setVariable("y", y).evaluate() < systemEquation.equationList.get(j).expressionsTab[1].setVariable("x", x).setVariable("y", y).evaluate())) {
                        czySpelnia = false;
                        break;
                    }
                }
            }
            if(czySpelnia) {
                System.out.println("x = " + x + "; y = " + y);
                listX.add(x);
                listY.add(y);
                double wartoscFunkcjiCelu = systemEquation.goalFunction.setVariable("x", x).setVariable("y", y).evaluate();
                if(listX.size() == 1) {
                    minLubMax = wartoscFunkcjiCelu;
                } else {
                    if(systemEquation.minOrMax == 1) {
                        if(wartoscFunkcjiCelu > minLubMax) {
                            minLubMax = wartoscFunkcjiCelu;
                        }
                    } else if(systemEquation.minOrMax == 2) {
                        if(wartoscFunkcjiCelu < minLubMax) {
                            minLubMax = wartoscFunkcjiCelu;
                        }
                    }
                }
            }
            czySpelnia = true;
        }

        System.out.println(minLubMax);

        /*for(int i = 0; i < SMC.equationList.size(); ++i) {
            SystemEquation.out.println(SMC.equationList.get(i));
        }*/

    }
}
