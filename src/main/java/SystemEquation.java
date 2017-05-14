import net.objecthunter.exp4j.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yevv on 14.05.17.
 */
public class SystemEquation {

    int variableAmong;
    int equationAmong;
    List<String> variableSymbolList;
    String[] variableSymbolTab;
    List<Equation> equationList;
    Expression goalFunction;
    int minOrMax; // max = 1, min = 2

    public SystemEquation() {
        this.variableSymbolList = new ArrayList<>();
        this.equationList = new ArrayList<>();
    }
}
