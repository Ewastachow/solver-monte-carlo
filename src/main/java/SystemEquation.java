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
    int start = 2000;
    int iterations = 100;


    public SystemEquation() {
        this.variableSymbolList = new ArrayList<>();
        this.equationList = new ArrayList<>();
    }

    public Solution getSolution(){

        variableValueMap = new HashMap<>();

        for(String s : variableSymbolList)
            variableValueMap.put(s, new Double(start));

        variableValueMap = iterationSolve(variableValueMap, start);

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
        else bestAmong = 2*start;
        Map<String, Double> bestValues = new HashMap<>();
        for(Map.Entry<Map<String, Double>, Double> m : map.entrySet())
            if(minOrMax == 1){
                if(m.getValue() > bestAmong){
                    bestValues = m.getKey();
                    bestAmong = m.getValue();
                }
            } else {
                if(m.getValue() < bestAmong){
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
        double step = radious/iterations;

        List<Map<String, Double>> result = new ArrayList<>();

        Map<String, List<Double>> tmp = new HashMap<>();

        for(Map.Entry<String, Double> m : pointInside.entrySet()){
            List<Double> list = new ArrayList<>();
            list.add(m.getValue());
            for(int i = 1; i < iterations; i++){
                list.add(m.getValue()+i*step);
                list.add(m.getValue()-i*step);
            }
            tmp.put(m.getKey(), list);
        }

        if(variableAmong==1){
            String variable = tmp.entrySet().iterator().next().getKey();
            for(Double d : tmp.entrySet().iterator().next().getValue()){
                Map<String, Double> tmp2 = new HashMap<>();
                tmp2.put(variable, d);
                result.add(tmp2);
            }
        }else if(variableAmong==2){
            Iterator<Map.Entry<String, List<Double>>> it = tmp.entrySet().iterator();
            Map.Entry<String, List<Double>> entry1 = it.next();
            Map.Entry<String, List<Double>> entry2 = it.next();
            String variable1 = entry1.getKey();
            String variable2 = entry2.getKey();
            for(Double d1 : entry1.getValue())
                for(Double d2 : entry2.getValue()){
                    Map<String, Double> tmp21 = new HashMap<>();
                    tmp21.put(variable1, d1);
                    tmp21.put(variable2, d2);
                    result.add(tmp21);
                }

        }else if(variableAmong==3){
            Iterator<Map.Entry<String, List<Double>>> it = tmp.entrySet().iterator();
            Map.Entry<String, List<Double>> entry1 = it.next();
            Map.Entry<String, List<Double>> entry2 = it.next();
            Map.Entry<String, List<Double>> entry3 = it.next();
            String variable1 = entry1.getKey();
            String variable2 = entry2.getKey();
            String variable3 = entry3.getKey();
            for(Double d1 : entry1.getValue())
                for(Double d2 : entry2.getValue())
                    for(Double d3 : entry3.getValue()){
                        Map<String, Double> tmp21 = new HashMap<>();
                        tmp21.put(variable1, d1);
                        tmp21.put(variable2, d2);
                        tmp21.put(variable3, d3);
                        result.add(tmp21);
                    }
        }else if(variableAmong==4){
            Iterator<Map.Entry<String, List<Double>>> it = tmp.entrySet().iterator();
            Map.Entry<String, List<Double>> entry1 = it.next();
            Map.Entry<String, List<Double>> entry2 = it.next();
            Map.Entry<String, List<Double>> entry3 = it.next();
            Map.Entry<String, List<Double>> entry4 = it.next();
            String variable1 = entry1.getKey();
            String variable2 = entry2.getKey();
            String variable3 = entry3.getKey();
            String variable4 = entry4.getKey();
            for(Double d1 : entry1.getValue())
                for(Double d2 : entry2.getValue())
                    for(Double d3 : entry3.getValue())
                        for(Double d4 : entry4.getValue()){
                            Map<String, Double> tmp21 = new HashMap<>();
                            tmp21.put(variable1, d1);
                            tmp21.put(variable2, d2);
                            tmp21.put(variable3, d3);
                            tmp21.put(variable4, d4);
                            result.add(tmp21);
                        }
        }

//        for(Map.Entry<String, List<Double>> m : tmp.entrySet()){
//            for(Double d : m.getValue()){
//
//            }
//        }

        // todo jak zrobić coś takiego zeby dla kazdego stringa były 100 roznych wartsci

        return result;
    }


}
