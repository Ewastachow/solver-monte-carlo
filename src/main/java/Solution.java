import java.util.Map;

/**
 * Created by yevv on 14.05.17.
 */
public class Solution {
    double solution;
    Map<String, Double>  parametrsValueMap;

    public Solution(double solution, Map<String, Double> parametrsValueMap) {
        this.solution = solution;
        this.parametrsValueMap = parametrsValueMap;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "solution=" + solution +
                ", parametrsValueMap=" + parametrsValueMap +
                '}';
    }
}
