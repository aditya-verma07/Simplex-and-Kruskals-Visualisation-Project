import java.util.*;

public class Simplex {
    private double [][] constraints;
    private double[] objective;

    public Simplex(double[][] constraintList, double[] objectiveFunction){ //constructor for a Simplex problem object
        constraints = constraintList;
        objective = objectiveFunction;
    }

    public double[][] getConstraints(){ //getter for constraints

        return constraints;
    }
    public double[] getObjective(){
        return objective;
    } //getter for objective
}
