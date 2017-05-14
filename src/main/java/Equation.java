import net.objecthunter.exp4j.Expression;

public class Equation {
    String equation;
    String[] tokens;
    Expression[] expressionsTab;
    String compareSymbol;

    public Equation(String ograniczenie, String[] tokens, Expression[] wyrazenia, String compareSymbol) {
        this.equation = ograniczenie;
        this.tokens = tokens;
        this.expressionsTab = wyrazenia;
        this.compareSymbol = compareSymbol;
    }
    
    @Override
    public String toString() {
        String s = "";
        for(int i = 0; i < tokens.length; ++i) {
            if(tokens[i] != null)
                s += tokens[i] + "\n";
        }
        return s;
    }
    
}
