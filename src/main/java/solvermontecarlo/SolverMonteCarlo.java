///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package solvermontecarlo;
//
//import java.util.Scanner;
//import net.objecthunter.exp4j.*;
//import java.util.*;
//import net.objecthunter.exp4j.operator.Operator;
//import java.util.concurrent.ThreadLocalRandom;
//
///**
// *
// * @author Un1kalny
// */
//public class SolverMonteCarlo {
//
//    /**
//     * @param args the command line arguments
//     */
//
//    int liczbaZmiennychDecyzyjnych;
//    int liczbaOgraniczen;
//    List<String> listaSymboliZmiennychDecyzyjnych = new ArrayList<>();
//    String[] tablicaSymboliZmiennychDecyzyjnych;
//    List<Ograniczenie> ograniczenia = new ArrayList<>();
//    Expression funkcjaCelu;
//    int maxCzyMin; // max = 1, min = 2
//
//    static int wczytajZKonsoliInta(Scanner scanner) {
//        while(true) {
//            try {
//                return Integer.valueOf(scanner.nextLine());
//            }catch(Exception ex) {
//                System.out.println("Błędna wartość. Spróbuj ponownie.");
//            }
//        }
//    }
//
//    static double wczytajZKonsoliDouble(Scanner scanner) {
//        while(true) {
//            try {
//                return Double.valueOf(scanner.nextLine());
//            }catch(Exception ex) {
//                System.out.println("Błędna wartość. Spróbuj ponownie.");
//            }
//        }
//    }
//
//    static String wczytajZKonsoliSymbolZmiennejDecyzyjnej(Scanner scanner) {
//        while(true) {
//            try {
//                String symbol = scanner.nextLine();
//                if(symbol.isEmpty()) {
//                    throw new Exception("Pusty string");
//                }
//                char[] charArray = symbol.toCharArray();
//                if(Character.isLetter(charArray[0]) || charArray[0] == '_') {
//                    for(int i = 1; i < charArray.length; ++i) {
//                        if(!Character.isLetter(charArray[i]) && !Character.isDigit(charArray[i]) && charArray[i] != '_') {
//                            throw new Exception("Złe dalsze znaki symbolu.");
//                        }
//                    }
//                } else {
//                    throw new Exception("Zły pierwszy znak symbolu.");
//                }
//                return symbol;
//            } catch (Exception ex) {
//                System.out.println("Zły symbol. Spróbuj inny.");
//            }
//        }
//    }
//
//    Ograniczenie wczytajZKonsoliOgraniczenie(Scanner scanner) {
//        while(true) {
//            try {
//                String ograniczenie = scanner.nextLine();
//                if(ograniczenie.isEmpty()) {
//                    throw new Exception("Pusty string");
//                }
//                if(!ograniczenie.contains("<=") && !ograniczenie.contains(">=") && !ograniczenie.contains("=") && !ograniczenie.contains(">") && !ograniczenie.contains("<")) {
//                    throw new Exception("Złe ograniczenie");
//                }
//                String[] tokens;
//                String znak;
//                Expression[] wyrazenia;
//                if(ograniczenie.contains("<=")) {
//                    tokens = ograniczenie.split("<=");
//                    znak = "<=";
//                }
//                else if(ograniczenie.contains(">="))
//                {
//                    tokens = ograniczenie.split(">=");
//                    znak = ">=";
//                }
//                else if(ograniczenie.contains("="))
//                {
//                    tokens = ograniczenie.split("=");
//                    znak = "=";
//                }
//                else if(ograniczenie.contains(">"))
//                {
//                    tokens = ograniczenie.split(">");
//                    znak = ">";
//                }
//                else
//                {
//                    tokens = ograniczenie.split("<");
//                    znak = "<";
//                }
//                if(tokens.length > 3)
//                    throw new Exception("Złe ograniczenie");
//
//                wyrazenia = new Expression[3];
//                for(int i = 0; i < tokens.length; ++i) {
//                    Expression e = new ExpressionBuilder(tokens[i])
//                    .variables(tablicaSymboliZmiennychDecyzyjnych)
//                    .build();
//                    wyrazenia[i] = e;
//                }
//
//                return new Ograniczenie(ograniczenie, tokens, wyrazenia, znak);
//            } catch (Exception ex) {
//                System.out.println("Złe ograniczenie. Spróbuj ponownie.");
//            }
//        }
//    }
//
//    Expression wczytajFunkcjeCelu(Scanner scanner) {
//        while(true) {
//            try {
//                String fCelu = scanner.nextLine();
//                if(fCelu.isEmpty())
//                    throw new Exception("Pusty string");
//
//                Expression e = new ExpressionBuilder(fCelu)
//                    .variables(tablicaSymboliZmiennychDecyzyjnych)
//                    .build();
//
//                return e;
//
//            } catch (Exception ex) {
//                System.out.println("Błędna funkcja celu. Spróbuj ponownie.");
//            }
//        }
//    }
//
//    static int wczytajCzyMaxCzyMin(Scanner scanner) {
//        while(true) {
//            try {
//                int tmp = Integer.valueOf(scanner.nextLine());
//                if(tmp != 1 && tmp != 2)
//                    throw new Exception("Błędna wartość");
//                return tmp;
//            }catch(Exception ex) {
//                System.out.println("Błędna wartość. Spróbuj ponownie.");
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        SolverMonteCarlo SMC = new SolverMonteCarlo();
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Podaj liczbe zmiennych decyzyjnych: ");
//        SMC.liczbaZmiennychDecyzyjnych = wczytajZKonsoliInta(scanner);
//
//        System.out.println("Podaj symbole zmiennych decyzyjnych (po kazdym nacisnij ENTER): ");
//        for(int i = 0; i < SMC.liczbaZmiennychDecyzyjnych; ++i)
//            SMC.listaSymboliZmiennychDecyzyjnych.add(wczytajZKonsoliSymbolZmiennejDecyzyjnej(scanner));
//
//        SMC.tablicaSymboliZmiennychDecyzyjnych = new String[SMC.liczbaZmiennychDecyzyjnych];
//        for(int i = 0; i < SMC.liczbaZmiennychDecyzyjnych; ++i) {
//            SMC.tablicaSymboliZmiennychDecyzyjnych[i] = SMC.listaSymboliZmiennychDecyzyjnych.get(i);
//        }
//
//        System.out.println("Podaj liczbe ograniczen: ");
//        SMC.liczbaOgraniczen = wczytajZKonsoliInta(scanner);
//
//        for(int i = 0; i < SMC.liczbaOgraniczen; ++i){
//            SMC.ograniczenia.add(SMC.wczytajZKonsoliOgraniczenie(scanner));
//        }
//
//        System.out.println("Podaj funkcje celu: ");
//        SMC.funkcjaCelu = SMC.wczytajFunkcjeCelu(scanner);
//
//        System.out.println("Max czy min?\n1 - max\n2 - min");
//        SMC.maxCzyMin = wczytajCzyMaxCzyMin(scanner);
//
//
//        double x, y;
//        List<Double> listX = new ArrayList<>();
//        List<Double> listY = new ArrayList<>();
//        double minLubMax = 0;
//        Random rand = new Random();
//        for(int i = 0; i < 1000; ++i) {
//            x = ThreadLocalRandom.current().nextInt((int)(SMC.ograniczenia.get(SMC.ograniczenia.size() - 2).wyrazenia[0]).evaluate(), ((int)(SMC.ograniczenia.get(SMC.ograniczenia.size() - 2).wyrazenia[2]).evaluate() + 1));
//            //y = ThreadLocalRandom.current().nextInt((int)(SMC.ograniczenia.get(SMC.ograniczenia.size() - 1).wyrazenia[0]).evaluate(), ((int)(SMC.ograniczenia.get(SMC.ograniczenia.size() - 1).wyrazenia[2]).evaluate() + 1));
//            y = ((double)2/3)*x;
//            boolean czySpelnia = true;
//            for(int j = 0; j < SMC.ograniczenia.size() - SMC.liczbaZmiennychDecyzyjnych;  ++j) {
//
//                System.out.println("" + SMC.ograniczenia.get(j).wyrazenia[0].setVariable("x", x).setVariable("y", y).evaluate() + SMC.ograniczenia.get(j).znak + SMC.ograniczenia.get(j).wyrazenia[1].setVariable("x", x).setVariable("y", y).evaluate());
//
//                if(SMC.ograniczenia.get(j).znak.equalsIgnoreCase("<=")) {
//                    if(!(SMC.ograniczenia.get(j).wyrazenia[0].setVariable("x", x).setVariable("y", y).evaluate() <= SMC.ograniczenia.get(j).wyrazenia[1].setVariable("x", x).setVariable("y", y).evaluate())) {
//                        czySpelnia = false;
//                        break;
//                    }
//                } else if(SMC.ograniczenia.get(j).znak.equalsIgnoreCase(">=")) {
//                    if(!(SMC.ograniczenia.get(j).wyrazenia[0].setVariable("x", x).setVariable("y", y).evaluate() >= SMC.ograniczenia.get(j).wyrazenia[1].setVariable("x", x).setVariable("y", y).evaluate())) {
//                        czySpelnia = false;
//                        break;
//                    }
//                } /*else if(SMC.ograniczenia.get(j).znak.equalsIgnoreCase("=")) {
//                    if(!(SMC.ograniczenia.get(j).wyrazenia[0].setVariable("x", x).setVariable("y", y).evaluate() == SMC.ograniczenia.get(j).wyrazenia[1].setVariable("x", x).setVariable("y", y).evaluate())) {
//                        czySpelnia = false;
//                        break;
//                    }
//                }*/ else if(SMC.ograniczenia.get(j).znak.equalsIgnoreCase(">")) {
//                    if(!(SMC.ograniczenia.get(j).wyrazenia[0].setVariable("x", x).setVariable("y", y).evaluate() > SMC.ograniczenia.get(j).wyrazenia[1].setVariable("x", x).setVariable("y", y).evaluate())) {
//                        czySpelnia = false;
//                        break;
//                    }
//                } else if(SMC.ograniczenia.get(j).znak.equalsIgnoreCase("<")) {
//                    if(!(SMC.ograniczenia.get(j).wyrazenia[0].setVariable("x", x).setVariable("y", y).evaluate() < SMC.ograniczenia.get(j).wyrazenia[1].setVariable("x", x).setVariable("y", y).evaluate())) {
//                        czySpelnia = false;
//                        break;
//                    }
//                }
//            }
//            if(czySpelnia) {
//                System.out.println("x = " + x + "; y = " + y);
//                listX.add(x);
//                listY.add(y);
//                double wartoscFunkcjiCelu = SMC.funkcjaCelu.setVariable("x", x).setVariable("y", y).evaluate();
//                if(listX.size() == 1) {
//                    minLubMax = wartoscFunkcjiCelu;
//                } else {
//                    if(SMC.maxCzyMin == 1) {
//                        if(wartoscFunkcjiCelu > minLubMax) {
//                            minLubMax = wartoscFunkcjiCelu;
//                        }
//                    } else if(SMC.maxCzyMin == 2) {
//                        if(wartoscFunkcjiCelu < minLubMax) {
//                            minLubMax = wartoscFunkcjiCelu;
//                        }
//                    }
//                }
//            }
//            czySpelnia = true;
//        }
//
//        System.out.println(minLubMax);
//
//        /*for(int i = 0; i < SMC.ograniczenia.size(); ++i) {
//            SystemEquation.out.println(SMC.ograniczenia.get(i));
//        }*/
//
//    }
//
//}
