import java.util.Queue;

/**
 * Created by yevv on 14.05.17.
 */
public class Main {

    public static void main(String[] args){
        SystemEquation systemEquation = new SystemEquation();
        ConsoleQA consoleQA = new ConsoleQA();
        consoleQA.asking(systemEquation);
        Solution solution = systemEquation.getSolution();
        System.out.print(solution);
    }

}
