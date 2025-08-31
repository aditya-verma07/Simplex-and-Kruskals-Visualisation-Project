import java.lang.reflect.Array;
import java.util.*;
public class Iterate {
    public static ArrayList<double[][]> tableaux = new ArrayList<>();
    public static ArrayList<String[]> rowHeadersList = new ArrayList<>();
    public static void solve(Simplex problem){
        tableaux.clear(); // Clear the previous tableaux
        rowHeadersList.clear();
        Tableau table = new Tableau(problem); //creating a table object taking a parameter of a simplex problem object
        tableaux.add(Tableau.makeTable(table)); //adding initial tableau to list of tableaux
        rowHeadersList.add(menuGUI.getRowHeaders()); //adding initial row headers to list of row headers
        while (!table.isOptimal()) {
            int pivotCol = Tableau.getPivotCol(); //getting pivot column and pivot row to perform a pivot
            int pivotRow = Tableau.getPivotRow(pivotCol);
            table.pivot(pivotRow, pivotCol);
            String[] newHeader = rowHeadersList.get(rowHeadersList.size() - 1).clone(); //creating new copy of row headers
            newHeader[pivotRow] = menuGUI.getColHeaders()[pivotCol]; //changing row headers of pivot row
            rowHeadersList.add(newHeader);
            tableaux.add(Tableau.makeTable(table));

        }
        double[] solution = table.getSolution();
        int numDecisionVariables = problem.getObjective().length; // the number of decision variables is the number of objective function coefficients
        int numSlackVariables = solution.length - numDecisionVariables; // the number of slack variables is the total number of variables - number of decision variables

        System.out.println("The optimal solution is: ");
        for(int i = 0; i < numDecisionVariables; i++){
            System.out.println("x" + (i+1) + " = " + Math.round(solution[i] *1000000.0) / 1000000.0); //printing from start of solution array to index numDecisionVariables
        }
        for(int i = 0; i < numSlackVariables; i++){
            System.out.println("s" + (i+1) + " = " + Math.round(solution[numDecisionVariables + i] * 1000000.0)/1000000.0); //printing from numDecisionVariables + 1 to end of array
        }
        System.out.println("Optimal value: " + Math.round(table.getOptimalValue() * 1000000.0) / 1000000.0); //using getOptimalValue() method from Tableau class to output optimal value
    }
    public static ArrayList<double[][]> getTableaux(){
        return tableaux;
    }
    public static ArrayList<String[]> getRowHeadersList(){
        return rowHeadersList;
    }

}
