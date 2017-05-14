import net.objecthunter.exp4j.Expression;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by yevv on 14.05.17.
 */
public class SystemEquation {

    int variableAmong;
    int equationAmong;
    List<String> variableSymbolList;
    Map<String, Double> variableValueMap;
    String[] variableSymbolTab;
    List<Equation> equationList;
    Expression goalFunction;
    int minOrMax; // max = 1, min = 2


    public SystemEquation() {
        this.variableSymbolList = new ArrayList<>();
        this.equationList = new ArrayList<>();
    }

    public Solution solve(){
        double x, y;
        List<Double> listX = new ArrayList<>();
        List<Double> listY = new ArrayList<>();
        double minLubMax = 0;
        Random rand = new Random();
        for(int i = 0; i < 1000; ++i) {
            x = ThreadLocalRandom.current().nextInt((int)(equationList.get(equationList.size() - 2).expressionsTab[0]).evaluate(),
                    ((int)(equationList.get(equationList.size() - 2).expressionsTab[2]).evaluate() + 1));
            //y = ThreadLocalRandom.current().nextInt((int)(SMC.equationList.get(SMC.equationList.size() - 1).expressionsTab[0]).evaluate(),
            // ((int)(SMC.equationList.get(SMC.equationList.size() - 1).expressionsTab[2]).evaluate() + 1));
            y = ((double)2/3)*x;
            boolean czySpelnia = true;
            for(int j = 0; j < equationList.size() - variableAmong; ++j) {

//                System.out.println("" + equationList.get(j).expressionsTab[0].setVariable("x", x).setVariable("y", y).evaluate() + equationList.get(j).compareSymbol + equationList.get(j).expressionsTab[1].setVariable("x", x).setVariable("y", y).evaluate());

                if(equationList.get(j).compareSymbol.equalsIgnoreCase("<=")) {
                    if(!(equationList.get(j).expressionsTab[0].setVariable("x", x).setVariable("y", y).evaluate() <= equationList.get(j).expressionsTab[1].setVariable("x", x).setVariable("y", y).evaluate())) {
                        czySpelnia = false;
                        break;
                    }
                } else if(equationList.get(j).compareSymbol.equalsIgnoreCase(">=")) {
                    if(!(equationList.get(j).expressionsTab[0].setVariable("x", x).setVariable("y", y).evaluate() >= equationList.get(j).expressionsTab[1].setVariable("x", x).setVariable("y", y).evaluate())) {
                        czySpelnia = false;
                        break;
                    }
                } /*else if(SMC.equationList.get(j).compareSymbol.equalsIgnoreCase("=")) {
                    if(!(SMC.equationList.get(j).expressionsTab[0].setVariable("x", x).setVariable("y", y).evaluate() == SMC.equationList.get(j).expressionsTab[1].setVariable("x", x).setVariable("y", y).evaluate())) {
                        czySpelnia = false;
                        break;
                    }
                }*/ else if(equationList.get(j).compareSymbol.equalsIgnoreCase(">")) {
                    if(!(equationList.get(j).expressionsTab[0].setVariable("x", x).setVariable("y", y).evaluate() > equationList.get(j).expressionsTab[1].setVariable("x", x).setVariable("y", y).evaluate())) {
                        czySpelnia = false;
                        break;
                    }
                } else if(equationList.get(j).compareSymbol.equalsIgnoreCase("<")) {
                    if(!(equationList.get(j).expressionsTab[0].setVariable("x", x).setVariable("y", y).evaluate() < equationList.get(j).expressionsTab[1].setVariable("x", x).setVariable("y", y).evaluate())) {
                        czySpelnia = false;
                        break;
                    }
                }
            }
            if(czySpelnia) {
                System.out.println("x = " + x + "; y = " + y);
                listX.add(x);
                listY.add(y);
                double wartoscFunkcjiCelu = goalFunction.setVariable("x", x).setVariable("y", y).evaluate();
                if(listX.size() == 1) {
                    minLubMax = wartoscFunkcjiCelu;
                } else {
                    if(minOrMax == 1) {
                        if(wartoscFunkcjiCelu > minLubMax) {
                            minLubMax = wartoscFunkcjiCelu;
                        }
                    } else if(minOrMax == 2) {
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
        return null;
    }

    public Solution getSolution(){

        for(String s : variableSymbolList)
            variableValueMap.put(s, new Double(300));

        variableValueMap = iterationSolve(variableValueMap, 300);

        return new Solution(goalFunction.setVariables(variableValueMap).evaluate(), variableValueMap);
    }

    Map<String, Double> iterationSolve(Map<String, Double> pointInside, double radious){
        return bestOption(
                setValuesInMap(
                        setListMapVariablesAgreeWithEquation(
                                generateExamplesList(
                                    pointInside, radious))));
    }

    Map<String, Double> bestOption(Map<Map<String, Double>, Double> map){
        double bestAmong;
        if(minOrMax == 1)
            bestAmong = 0;
        else bestAmong = 1000;
        Map<String, Double> bestValues = new HashMap<>();
        for(Map.Entry<Map<String, Double>, Double> m : map.entrySet())
            if(minOrMax == 1){
                if(map.get(m) > bestAmong){
                    bestValues = m.getKey();
                    bestAmong = m.getValue();
                }
            } else {
                if(map.get(m) < bestAmong){
                    bestValues = m.getKey();
                    bestAmong = m.getValue();
                }
            }
        return bestValues;
    }

    Map<Map<String, Double>, Double> setValues(Map<Map<String, Double>, Double> map){
        //todo niekoniecznie, bo mam dla listy;

        return map;
    }

    Map<Map<String, Double>, Double> setValuesInMap(List<Map<String, Double>> variableList){
        Map<Map<String, Double>, Double> map = new HashMap<>();
        for(Map<String, Double> m : variableList)
            map.put(m, goalFunction.setVariables(m).evaluate());
        return map;
    }

    List<Map<String, Double>> setListMapVariablesAgreeWithEquation(List<Map<String, Double>> variableList){
        List<Map<String, Double>> result = new ArrayList<>();
        for(Map<String, Double> m : variableList){
            boolean isRight = true;
            for(Equation q : equationList){
                double firstExpres = q.expressionsTab[0].setVariables(m).evaluate();
                double secondExpres = q.expressionsTab[1].setVariables(m).evaluate();
                if(q.compareSymbol.equalsIgnoreCase("<")){
                    if(firstExpres >= secondExpres) isRight = false;
                }else if(q.compareSymbol.equalsIgnoreCase(">")){
                    if(firstExpres <= secondExpres) isRight = false;
                }else if(q.compareSymbol.equalsIgnoreCase("=<") || q.compareSymbol.equalsIgnoreCase("<=")){
                    if(firstExpres > secondExpres) isRight = false;
                }else if(q.compareSymbol.equalsIgnoreCase("=>") || q.compareSymbol.equalsIgnoreCase(">=")){
                    if(firstExpres < secondExpres) isRight = false;
                }else{
                    if(!(new Double(firstExpres)).equals(secondExpres)) isRight = false;
                }
            }
            if(isRight)
                result.add(m);
        }
        return result;
    }

    List<Map<String, Double>> generateExamplesList(Map<String, Double> pointInside, double radious){
        double step = radious/100;
        List<Map<String, Double>> result = new ArrayList<>();
        Map<String, List<Double>> tmp = new HashMap<>();
        for(Map.Entry<String, Double> m : pointInside.entrySet()){
            tmp.get(m.getKey()).add(m.getValue());
            for(int i = 1; i < 100; i++){
                tmp.get(m.getKey()).add(m.getValue()+i*step);
                tmp.get(m.getKey()).add(m.getValue()-i*step);
            }
        }

        // todo jak zrobić coś takiego zeby dla kazdego stringa były 1000 roznych wartsci

        return result;
    }


}
