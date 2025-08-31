import java.util.*;

public class Solve {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int numVar;
        int numConstraints;
        while (true){
            System.out.println("Enter number of variables: ");
            if (scanner.hasNextInt()) {
                numVar = scanner.nextInt();
                if (numVar > 0){ // Ensure a positive number of variables entered
                    break;
                }else{
                    System.out.println("Please enter a positive number.");
                }
            }else{
                System.out.println("Invalid input. Please enter only numbers.");
                scanner.next(); // Clear the invalid input
            }
        }
        while (true){
            System.out.println("Enter number of constraints: ");
            if (scanner.hasNextInt()){
                numConstraints = scanner.nextInt();
                if (numConstraints > 0){// Ensure that a positive number of constraints are entered
                    break;
                }else{
                    System.out.println("Please enter a positive number.");
                }
            }else{
                System.out.println("Invalid input. Please enter only numbers.");
                scanner.next(); // Clear the invalid input
            }
        }
        scanner.nextLine();

        double[][] constraints = new double[numConstraints][numVar + 1]; //creating a 2d array to stores the coefficients of constraints
        System.out.println("Enter the coefficients of the constraints and include the value: ");
        for (int i = 0; i < numConstraints; i++){
            while (true){
                String line = scanner.nextLine();
                String[] parts = line.trim().split("\\s+");
                if(parts.length == numVar + 1) { // checks if the number of inputs matches numVar + 1
                    for (int j = 0; j < numVar + 1; j++) {
                        try {
                            constraints[i][j] = Double.parseDouble(parts[j]); // adds entered coefficients of constraints to 2D array
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter only numbers.");
                            break;
                        }
                    }
                    break; //exit the loop if valid input
                } else {
                    System.out.println("Invalid number of coefficients entered. Please enter again starting from the beginning.");
                }
            }

        }
        double[] objective = new double[numVar];

        System.out.println("Enter coefficients of the objective function:");
        while (true) {
            String line = scanner.nextLine();
            String[] parts = line.trim().split("\\s+");

            if (parts.length == numVar) { // check if the number of inputs matches numVar
                for (int i = 0; i < numVar; i++) {
                    try {
                        objective[i] = Double.parseDouble(parts[i]);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter only numbers.");
                        break;
                    }
                }
                break;
            } else {
                System.out.println("Invalid number of coefficients.");
            }
        }
        Simplex problem = new Simplex(constraints, objective); //creating a new object of type Simplex

    }
}
