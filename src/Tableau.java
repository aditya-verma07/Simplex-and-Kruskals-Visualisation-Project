import javax.swing.BorderFactory;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.*;
import java.util.Random;

public class Tableau extends menuGUI {
    private static double[][] table;
    private static int rows;
    private static int cols;
    private static double pivotVal;
    private static ArrayList<Double> thetaValues = new ArrayList<>();
    private static ArrayList<ArrayList<String>> rowOperationList = new ArrayList<>();
    public Tableau(Simplex problem) { //constructor to create an empty array for an initial tableau
        int numConstraints = problem.getConstraints().length; // the number of constraints is the length of the constraints array
        int numVar = problem.getObjective().length; //the number of variables is the same as the length of the objective array

        rows = numConstraints + 1;
        cols = numVar + numConstraints + 1;
        table = new double[rows][cols];

        initialiseTableau(problem);
    }

    public static double[][] makeTable(Tableau copyTableau) {
        double[][] copyTable = new double[rows][cols]; //creates a new 2d array to store the deep copy of the table
        double[][] existingTable = copyTableau.getTable(); //assigns the parameter to a 2d array
        for (int i = 0; i < existingTable.length; i++) {
            for (int j = 0; j < existingTable[i].length; j++) {
                copyTable[i][j] = existingTable[i][j]; //copies the array
            }
        }
        return copyTable;
    }

    private void initialiseTableau(Simplex problem) {
        double[][] constraints = problem.getConstraints();
        double[] objective = problem.getObjective();
        int numVar = objective.length;

        for (int i = 0; i < constraints.length; i++) {
            for (int j = 0; j < constraints[i].length - 1; j++) {
                table[i][j] = constraints[i][j]; //adding the coefficients from constraints[][] to table[][]
            }
            table[i][numVar + i] = 1; //adding slack variables in respective indices
            table[i][cols - 1] = constraints[i][constraints[i].length - 1]; // adding the values in the value column
        }
        for (int j = 0; j < objective.length; j++) {
            table[rows - 1][j] = -1 * objective[j];//adding the values of the objective function in the bottom row * -1
        }

    }

    public void pivot(int pivotRow, int pivotCol) {
        double pivotValue = table[pivotRow][pivotCol]; //using the pivot row and pivot column index, we can set a pivot value
        for (int j = 0; j < cols; j++) {
            table[pivotRow][j] /= pivotValue; //goes through the pivot row dividing every value by the pivot value
        }
        for (int i = 0; i < rows; i++) {
            if (i != pivotRow) {
                double factor = table[i][pivotCol]; //goes through every other row subtracting the pivot column value from the current index value
                for (int j = 0; j < cols; j++) {
                    table[i][j] -= factor * table[pivotRow][j];
                }
            }
        }
    }

    public boolean isOptimal() {
        for (int c = 0; c < cols - 1; c++) { // iterating through the objective row
            if (table[rows - 1][c] < 0) { //if there are still negative values then we return false
                return false;
            }
        }
        return true;
    }
    public static ArrayList<Double> getThetaValues(double[][] tableau) {
        thetaValues.clear();
        int pivotC = getCurrentPivotCol(tableau); //get the pivot column index

        for (int i = 0; i < tableau.length; i++) {
            if (tableau[i][pivotC] > 0) { //ensure we are not dividing by zero
                double first = tableau[i][cols - 1];
                double second = tableau[i][pivotC];
                System.out.println(first);
                System.out.println(second);
                System.out.println(" ");
                double theta = tableau[i][cols - 1] / tableau[i][pivotC]; //the value column is at cols - 1
                thetaValues.add(theta); //add the calculated theta value to the list
            } else {
                thetaValues.add(Double.POSITIVE_INFINITY); //handles cases where division by zero would occur
            }
        }
        return thetaValues;
    }
    public static int getPivotCol() {
        int pivotCol = 0;
        double minValue = table[rows - 1][0]; //sets the minimum value as the bottom left value of the table
        for (int i = 0; i < cols - 1; i++) { //iterates through objective row checking if next value smaller (more negative) than current minValue
            if (table[rows - 1][i] < minValue) {
                minValue = table[rows - 1][i]; // if true, sets minValue as this new value
                pivotCol = i; //sets index of pivot column
            }
        }
        return pivotCol;
    }

    public static int getPivotRow(int pivotCol) {
        int pivotRow = 0;
        double minRatio = Double.MAX_VALUE; //sets value of new variable equal to the max value offered by java
        for (int i = 0; i < rows - 1; i++) {
            double ratio = table[i][cols - 1] / table[i][pivotCol]; //divides value column by pivot column
            if (ratio > 0 && ratio < minRatio) { // if result > 0 and < current minRatio then sets minRatio to current value
                minRatio = ratio;
                pivotRow = i; //sets index of pivot row
            }
        }
        pivotVal = table[pivotRow][pivotCol];
        return pivotRow;
    }

    public void printTable() {
        for (double[] row : table) {
            for (double value : row) {
                System.out.printf("%10.2f", value); //leaves spacing of 10 and outputs with 2 decimal places
            }
            System.out.println();

        }
    }

    public static ArrayList<String> getRowOperations(double[][] tableau) {
        ArrayList<String> rowOps = new ArrayList<>(); //create a new list for each call
        int pivotC = getCurrentPivotCol(tableau); //getting the pivot row and pivot column of the tableau
        int pivotR = getCurrentPivotRow(pivotC, tableau);
        for(int i = 0; i < tableau.length; i++){
            double val = tableau[i][pivotC];
            if(i != pivotR){
                if(val > 0) { //checking if the value in the pivot column is negative
                    if(val % 1 == 0){ //checking if value in pivot column is integer
                        String formatting = Double.toString(Math.abs(val)); //new string which takes the positive version of the
                        formatting.replace(".0", ""); //if integer then it removes the .0 from the end of the double
                        rowOps.add("R" + (i + 1) + " - " + formatting + "R" + pivotR); //it is negative, so it has been converted to positive then the string '-' is put in front of it
                    }else{
                        rowOps.add("R" + (i + 1) + " - " + String.format("%.1f", (Math.abs(val))) + "R" + pivotR); //if it's not an integer, then round the absolute value and format the row operation
                    }

                } else{ //if positive then repeat the same process but add a '+' sign instead
                    if(val % 1 == 0){
                        String formatting = Double.toString(Math.abs(val)); //if integer then remove the .0 and add to rowOps
                        formatting.replace(".0", "");
                        rowOps.add("R" + (i + 1) + " + " +  formatting+ "R" + pivotR);
                    } else{
                        rowOps.add("R" + (i + 1) + " + " + String.format("%.1f", (Math.abs(val))) + "R" + pivotR);
                    }
                }
            } else{ //this case is for if the value is in the pivot row
                if(val % 1 == 0) {
                    String formatting = Double.toString(Math.abs(val));
                    formatting.replace(".0", ""); //remove the .0 if the value is an integer
                    rowOps.add("R" + (pivotR + 1) + " / " + formatting); //format the row operation for the pivot row
                } else{
                    rowOps.add("R" + (pivotR + 1) + " / " + String.format("%.1f", (Math.abs(val)))); //no need to handle negative cases as the pivot value can never be negative
                }
            }
        }
        return rowOps; //return the new list
    }


    public static JPanel displayTableau(String[] rowHeaders, String[] colHeaders, int rows, int cols) {
        ArrayList<double[][]> toOutput = Iterate.getTableaux(); // creates a new arraylist of 2d arrays which gets all the tableaux from the iterations of the algorithm
        ArrayList<String[]> rowHeaderArray = Iterate.getRowHeadersList(); //gets the list of rowheaders from the Iterate class and assigns them to an arraylist of string arrays
        JTabbedPane tabbedPane = new JTabbedPane(); //creating the tabbed pane to display the tableaux
        tabbedPane.setPreferredSize(new Dimension(1000, 300)); //dimensions to ensure no overlap between the tabbed pane and the inequality panel
        JPanel fullPanel = new JPanel();  //creating the panel that will store the tabbed pane and will be the main panel added to the gui
        fullPanel.setLayout(new FlowLayout());
        int tableauCount = 0;
        for(int k = 0; k < toOutput.size() -1; k++){ //getting the row operations for each tableau in the list above
            rowOperationList.add(getRowOperations(toOutput.get(k)));
        }
        for (double[][] outputTable : toOutput) {
            ArrayList<Double> thetaList = getThetaValues(outputTable); //getting the theta values for each tableau in the toOutput list
            JPanel thetaPanel = new JPanel(); //creating a new panel to store the theta values and setting the layout as grid layout with 1 column to give the table format
            thetaPanel.setLayout(new GridLayout(0, 1));thetaPanel.setPreferredSize(new Dimension(5, thetaPanel.getPreferredSize().height));
            JLabel theta = new JLabel("θ"); //label which indicates the theta column
            theta.setFont(new Font("Arial", Font.BOLD, 15));
            theta.setBorder(BorderFactory.createLineBorder(Color.BLACK)); //sets the font, border and alignment of the theta label
            theta.setHorizontalAlignment(SwingConstants.CENTER);
            theta.setHorizontalTextPosition(SwingConstants.CENTER);
            thetaPanel.add(theta); //adds the label to the panel
            JPanel rowOpPanel= new JPanel(); //creates a new panel to store the row operations
            rowOpPanel.setLayout(new GridLayout(0, 1)); //setting the layout of the panel in a similar way to the theta panel
            rowOpPanel.setPreferredSize(new Dimension(5, thetaPanel.getPreferredSize().height)); //fixing the size of the panel
            JLabel rowOp = new JLabel("Row Ops."); //setting the text to be added to the top of the rowOpPanel
            rowOp.setHorizontalAlignment(SwingConstants.CENTER); //setting the font, border and alignment of the text label
            rowOp.setHorizontalTextPosition(SwingConstants.CENTER);
            rowOp.setFont(new Font("Arial", Font.BOLD, 15));
            rowOp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            rowOpPanel.add(rowOp); //adds label to the panel
            for(Double value: thetaList){
                if (value == Double.POSITIVE_INFINITY){ //checks if theta value is infinity (dividing by 0)
                    JLabel thetaLabel = new JLabel("--"); //sets it to -- as it need not be considered for the pivot row
                    thetaLabel.setFont(new Font("Arial",Font.PLAIN, 15)); //sets the font, border and alignment of the theta label
                    thetaLabel.setHorizontalTextPosition(SwingConstants.CENTER);
                    thetaLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    thetaLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    thetaPanel.add(thetaLabel); //adding to thetaPanel
                } else {
                    if(value % 1 == 0){ //checks if the value is an integer
                        String integerConversion = (Double.toString(value)).replace(".0", "");//converting value to a string and removing the .0
                        JLabel thetaLabel = new JLabel(integerConversion);  //setting the theta label as the string created above
                        thetaLabel.setFont(new Font("Arial", Font.PLAIN, 15)); //setting the font, border and alignment of the label
                        thetaLabel.setHorizontalTextPosition(SwingConstants.CENTER);
                        thetaLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        thetaLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        thetaPanel.add(thetaLabel); //adding label to thetaPanel
                    } else {
                        JLabel thetaLabel = new JLabel(String.format("%.2f",value)); //rounds the value to 2dp and adds it
                        thetaLabel.setFont(new Font("Arial", Font.PLAIN, 15)); //setting font, border and alignment of label
                        thetaLabel.setHorizontalTextPosition(SwingConstants.CENTER);
                        thetaLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        thetaLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        thetaPanel.add(thetaLabel); //adding label to thetaPanel
                    }
                }
            }

            if(tableauCount >= 1){ // checking to make sure not iterating on the first tableau
                ArrayList<String> rowOpList = rowOperationList.get(tableauCount-1); //if any tableau that isn't the initial tableau, then add row operations
                for(int b = 0; b < rowOpList.size(); b++) {
                    System.out.println(rowOpList.get(b));
                    JLabel rowOpLabel = new JLabel(rowOpList.get(b)); //sets a label with the text of the row operation from rowOpList
                    rowOpLabel.setFont(new Font("Arial", Font.PLAIN, 15)); //sets font, border and alignment of the label
                    rowOpLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    rowOpLabel.setHorizontalTextPosition(SwingConstants.CENTER);
                    rowOpLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    rowOpPanel.add(rowOpLabel); //adds the label to the rowOpPanel
                }
            }
            JPanel gridPanel = new JPanel(new GridBagLayout()); //new panel with gridbag layout for better control over spacing of components
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0; //column 0
            gbc.gridy = 0; //row 0
            gbc.gridwidth = 1; //span 1 column
            gbc.weightx = 0.5; //take up 75% of the panel's width
            gbc.fill = GridBagConstraints.BOTH; //expand both horizontally and vertically

            JPanel tableauPanel = createTableauPanel(outputTable, rowHeaderArray.get(tableauCount), colHeaders, rows, cols, tableauCount,toOutput.size()-1); //creates a formatted tableau based on the current table

            gridPanel.add(tableauPanel, gbc);
            if(tableauCount >= 1) {//checks if not on initial tableau before adding row operations to the gui
                gbc.gridx = 1;  //adds to the right of the tableau
                gbc.gridy = 0;
                gbc.weightx = 0.3;
                gridPanel.add(rowOpPanel, gbc); //adds the row operation panel to the grid panel
            }
            gbc.gridx = 2; //adds the theta panel to the gui to the right of the row operations
            gbc.gridy = 0;
            gbc.weightx = 0.1;
            gridPanel.add(thetaPanel, gbc); //adds the theta panel to the grid panel

            JPanel explainPanel = explainTableau(outputTable, tableauCount, toOutput.size() - 1); //creates a new panel which contains the explanation of the tableau
            gbc.gridx = 0;
            gbc.gridy = 1; //adds underneath the tableau
            gbc.gridwidth = 3; // makes the panel span the entire width of the gridPanel
            gbc.fill = GridBagConstraints.BOTH;
            gridPanel.add(explainPanel, gbc); //adds the explanation to the grid panel

            tabbedPane.add("Iteration " + (tabbedPane.getTabCount() + 1), gridPanel); //sets the title of each tab in the tabbed pane as the iteration of the algorithm
            tableauCount++; //increases counter of which iteration we are on
        }
        fullPanel.add(tabbedPane); //adds the tabbed pane to the main panel


        return fullPanel; //returns fullPanel as the main panel to be added to the GUI
    }

    public static JPanel explainTableau(double[][] tableau, int a, int numTableau){
        JPanel addPanel = new JPanel(); //new panel to contain explanation
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS)); //set box layout for this panel
        ArrayList<JTextPane> initialResponses = new ArrayList<>(); //list of possible explanation responses for initial tableau
        ArrayList<JTextPane> intermediateResponses = new ArrayList<>(); //list of possible explanation responses for intermediate tableau
        JTextPane option1 = new JTextPane(); //explaining the tableau, using line breaks for better flow of reading
        option1.setText("Looking through the objective row, the pivot column was selected as " + " '" + getColHeaders()[getCurrentPivotCol(tableau)] + "' " + ", highlighted in blue, as this has the most negative value in the objective row." + " By dividing each number in the Value column by the respective value in the pivot column and selecting the smallest positive result, the pivot row was selected as " + " '" + getRowHeaders()[getCurrentPivotRow(getCurrentPivotCol(tableau), tableau)] + "'.");
        option1.setText(option1.getText() + " This gives us the pivot value as '" + Math.round(tableau[getCurrentPivotRow(getCurrentPivotCol(tableau), tableau)][getCurrentPivotCol(tableau)] * 100.0) / 100.0 + "' ." );
        option1.setText(option1.getText() + "\nOur aim is to reduce the pivot value to 1 and everything else in the pivot column to 0, so our first row operation is [the values in the pivot row] / '" + Math.round(tableau[getCurrentPivotRow(getCurrentPivotCol(tableau), tableau)][getCurrentPivotCol(tableau)] * 100) / 100 + "'.");
        option1.setText(option1.getText() + " We now look at each value in the pivot column by turn. We must see how we can reduce the value to 0 using the pivot value, which is now 1.");
        option1.setText(option1.getText() + "\nWe take a value from the pivot column that is not the pivot value, we then subtract the value multiplied by the pivot row, so if the value is 5, we take 5 * 1 away from it, giving us 0. This process is repeated throughout the row, using the respective values in the pivot row. We then repeat which each non-pivot value in the pivot column.");
        option1.setText(option1.getText() + "\nWe repeat the process of applying row operations until we have completed the tableau.");
        option1.setFont(new Font("Arial", Font.PLAIN, 15)); //setting font size and style of the explanation text
        option1.setEditable(false); //ensure the user cannot edit the explanation

        JTextPane option2 = new JTextPane();
        option2.setText("We look through the objective row for the most negative value. This gives us the pivot column as " + " '" + getColHeaders()[getCurrentPivotCol(tableau)] + "' " + ", highlighted in blue. To find the theta values, we divide each number in the value column by its respective value in the pivot column.");
        option2.setText(option2.getText() + " We now select the smallest, positive theta value, and the row in which this is situated is our pivot row, highlighted in orange. The intersection between the pivot column and pivot row is our pivot value, '" + Math.round(tableau[getCurrentPivotRow(getCurrentPivotCol(tableau), tableau)][getCurrentPivotCol(tableau)] * 100.0) / 100.0 + "' ." );
        option2.setText(option2.getText() + "\nOur aim is to reduce the pivot value to 1 and everything else in the pivot column to 0, which gives us our first row operation as [the values in the pivot row] / our pivot value, '" + Math.round(tableau[getCurrentPivotRow(getCurrentPivotCol(tableau), tableau)][getCurrentPivotCol(tableau)] * 100.0) / 100.0 + "'." );
        option2.setText(option2.getText() + " We look at each value in the pivot column. We must see how we can reduce the value to 0 using the pivot value, which has now been reduced to 1.");
        option2.setText(option2.getText() + " We take a value from the pivot column that is not the pivot value and subtract it multiplied by the pivot row. This process is applied across the row, using the respective values in the pivot row, and is repeated for each non-pivot value in the pivot column.");
        option2.setFont(new Font("Arial", Font.PLAIN, 15)); //setting font size and style of the explanation text
        option2.setEditable(false); //ensure the user cannot edit the explanation

        JTextPane midOption1 = new JTextPane();
        midOption1.setText("Applying the row operations to the previous tableau gives us this tableau, with those row operations displayed next to it. We repeat the process from the previous tableau. Looking through the objective row, the pivot column is " + " '" + getColHeaders()[getCurrentPivotCol(tableau)] + "' " + ", highlighted in blue. This was chosen as this column has the most negative value in the objective row.");
        midOption1.setText(midOption1.getText() + "\nBy dividing each value in the Value column by its respective value in the pivot column, we get our theta values, shown right of the tableau. We select the smallest, positive theta value to give us our pivot row. The intersection between pivot row and pivot column, is our pivot value, '" + Math.round(tableau[getCurrentPivotRow(getCurrentPivotCol(tableau), tableau)][getCurrentPivotCol(tableau)] * 100.0) / 100.0 + "',  highlighted in purple.");
        midOption1.setText(midOption1.getText() + " We want to reduce the pivot value to 1 and everything else in the pivot column to 0. The first row operation we perform is on the pivot row and involves dividing each value in the pivot row by the pivot value to reduce our pivot value to 1. We now pick the first value in the pivot column that isn't in the pivot row, and reduce it to 0, using the values in the pivot row.");
        midOption1.setText(midOption1.getText() + " As the pivot value is 1, we just subtract whatever the value in the pivot column is * 1 from the value in the pivot column to reduce it to 0. We must perform the same row operation on each value in the row.");
        midOption1.setText(midOption1.getText() + "\n We repeat this process for each row, giving us our new tableau.");
        midOption1.setFont(new Font("Arial", Font.PLAIN, 15)); //setting font size and style of the explanation text
        midOption1.setEditable(false); //ensure the user cannot edit the explanation

        JTextPane midOption2 = new JTextPane();
        midOption2.setText("Performing the row operations on the previous tableau yields the following tableau, with the row operations shown alongside it. We repeat the steps from the previous tableau. Examining the objective row, the pivot column is identified as " + " '" + getColHeaders()[getCurrentPivotCol(tableau)] + "' " + ", highlighted in blue, as it corresponds to the most negative value in the objective row.");
        midOption2.setText(midOption2.getText() + " We then identify the smallest positive theta value, and the row containing this value is chosen as the pivot row, highlighted in orange. The intersection of the pivot column and pivot row marks the pivot value, '" + Math.round(tableau[getCurrentPivotRow(getCurrentPivotCol(tableau), tableau)][getCurrentPivotCol(tableau)] * 100.0) / 100.0 + "' .");
        midOption2.setText(midOption2.getText() + " Our goal is to reduce the pivot value to 1 and all other entries in the pivot column to 0, resulting in our first row operation as [the values in the pivot row] / our pivot value, '" + Math.round(tableau[getCurrentPivotRow(getCurrentPivotCol(tableau), tableau)][getCurrentPivotCol(tableau)] * 100.0) / 100.0 + "'.");
        midOption2.setText(midOption2.getText() + " Next, we examine each value in the pivot column and determine how to reduce it to 0 using the pivot value, which has already been reduced to 1.");
        midOption2.setText(midOption2.getText() + "\nFor each non-pivot value in the pivot column, we calculate the product of that value and the pivot row, then subtract it from the corresponding entry. This is repeated for all entries in the pivot column to reduce them to 0.");
        midOption2.setText(midOption2.getText() + "\nWe repeat this process for each row of the tableau, which will give us our next tableau.");
        midOption2.setFont(new Font("Arial", Font.PLAIN, 15)); //setting font size and style of the explanation text
        midOption2.setEditable(false); //ensure the user cannot edit the explanation

        JTextPane finalOption1 = new JTextPane();
        ArrayList<String[]> rowHeaderArr = Iterate.getRowHeadersList();
        String[] finalRowHeaders = rowHeaderArr.get(a);
        finalOption1.setText("Looking through the objective row, there are no more negative values, meaning this tableau is final, so we can read off the final values of our variables by reading along the row headers and their values.");
        for(int i = 0; i < finalRowHeaders.length; i ++){
            finalOption1.setText(finalOption1.getText() + "\n");
            finalOption1.setText(finalOption1.getText() + " " + finalRowHeaders[i] + " = " + String.format("%.2f", (tableau[i][tableau[i].length - 1])));
        }
        finalOption1.setText(finalOption1.getText() + "\n");
        finalOption1.setText(finalOption1.getText() + "All other variables have value 0.");
        finalOption1.setFont(new Font("Arial", Font.PLAIN, 15)); //setting font size and style of the explanation text
        finalOption1.setEditable(false); //ensure the user cannot edit the explanation

        initialResponses.add(option1);
        initialResponses.add(option2);
        intermediateResponses.add(midOption1);
        intermediateResponses.add(midOption2);

        if(a == 0){
            Random rand = new Random();
            int r = rand.nextInt(2);
            addPanel.add(initialResponses.get(r));
        } else if (a == numTableau){
            addPanel.add(finalOption1);
        } else{
            Random rand = new Random();
            int r = rand.nextInt(2);
            addPanel.add(intermediateResponses.get(r));
        }


        return addPanel; //returns this panel to be added to grid panel
    }

    public static int getCurrentPivotCol(double[][] tableau) {
        int pivotCol = 0;
        double minValue = tableau[rows - 1][0]; //sets the minimum value as the bottom left value of the table
        for (int i = 0; i < cols - 1; i++) { //iterates through objective row checking if next value smaller (more negative) than current minValue
            if (tableau[rows - 1][i] < minValue) {
                minValue = tableau[rows - 1][i]; // if true, sets minValue as this new value
                pivotCol = i; //sets index of pivot column
            }
        }
        return pivotCol;
    }

    public static int getCurrentPivotRow(int pivotCol, double[][] tableau) {
        int pivotRow = 0;
        double minRatio = Double.MAX_VALUE; //sets value of new variable equal to the max value offered by java
        for (int i = 0; i < rows - 1; i++) {
            double ratio = tableau[i][cols - 1] / tableau[i][pivotCol]; //divides value column by pivot column
            if (ratio > 0 && ratio < minRatio) { // if result > 0 and < current minRatio then sets minRatio to current value
                minRatio = ratio;
                pivotRow = i; //sets index of pivot row
            }
        }
        pivotVal = tableau[pivotRow][pivotCol];
        return pivotRow;
    }

    private static JPanel createTableauPanel(double[][] outputTable, String[] rowHeaders, String[] colHeaders, int rows, int cols, int tableauCount,int numberTableau) {
        JPanel tableauPanel = new JPanel(new GridLayout(rows + 1, cols + 1)); //new panel to add the tableau values, using grid layout to create effect of a table
        JLabel bvLabel = new JLabel("B.V."); //sets the basic variable header in the top left corner
        bvLabel.setFont(new Font("Arial", Font.BOLD, 15));
        bvLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tableauPanel.add(bvLabel);
        JLabel valueLabel = null;

        for (String header : colHeaders) {
            JLabel colHeaderLabel = new JLabel(header, SwingConstants.CENTER); //adds each column header to the top row of the panel
            colHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER); //setting font, size, border and alignment of label
            colHeaderLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            colHeaderLabel.setFont(new Font("Arial", Font.BOLD, 15));
            colHeaderLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            tableauPanel.add(colHeaderLabel); //adding the column header label to the panel
        }

        for (int i = 0; i < outputTable.length; i++) {
            JLabel rowHeaderLabel = new JLabel(rowHeaders[i], SwingConstants.CENTER); //adding the row headers in the first column of the table underneath the basic variable header
            rowHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER); //setting the font, size, border and alignment of label
            rowHeaderLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            rowHeaderLabel.setFont(new Font("Arial", Font.BOLD, 15));
            rowHeaderLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            tableauPanel.add(rowHeaderLabel); //adding the row header label to the panel

            for (int j = 0; j < outputTable[i].length; j++) {
                if(outputTable[i][j] % 1 == 0){
                    valueLabel = new JLabel(String.format("%.2f", (outputTable[i][j]), SwingConstants.CENTER)); //adding the value from the output table one by one to the tableau
                    valueLabel.setHorizontalAlignment(SwingConstants.CENTER); //setting the font, size, border and alignment of the label
                    valueLabel.setHorizontalTextPosition(SwingConstants.CENTER);
                    valueLabel.setFont(new Font("Arial", Font.PLAIN, 15));
                    valueLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    tableauPanel.add(valueLabel); //adds the value label to the tableau panel
                } else {
                    valueLabel = new JLabel(String.format("%.2f", (outputTable[i][j]), SwingConstants.CENTER)); //adding the value from the output table one by one to the tableau
                    valueLabel.setHorizontalAlignment(SwingConstants.CENTER); //setting the font, size, border and alignment of the label
                    valueLabel.setHorizontalTextPosition(SwingConstants.CENTER);
                    valueLabel.setFont(new Font("Arial", Font.PLAIN, 15));
                    valueLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    tableauPanel.add(valueLabel); //adds the value label to the tableau panel
                }
                if(tableauCount != numberTableau) {
                    if (i == getCurrentPivotRow(getCurrentPivotCol(outputTable), outputTable)) { //highlights the pivot row in orange for explanation
                        valueLabel.setOpaque(true);
                        valueLabel.setBackground(new Color(210, 126, 87));
                    }
                    if (j == getCurrentPivotCol(outputTable)) { //highlights the pivot column in blue for explanation
                        valueLabel.setOpaque(true);
                        valueLabel.setBackground(new Color(140, 204, 218));
                    }
                    if (i == getCurrentPivotRow(getCurrentPivotCol(outputTable), outputTable) && j == getCurrentPivotCol(outputTable)) { //highlights pivot value in a pink/purple for explanation
                        valueLabel.setOpaque(true);
                        valueLabel.setBackground(new Color(175, 95, 201));
                    }
                }
            }
        }

        return tableauPanel;
    }

    public double[][] getTable(){ //get method for the table
        return table;
    }
    public double getOptimalValue(){
        return table[rows-1][cols-1]; //gets value in bottom right index of array
    }
    public double[] getSolution(){
        double[] solution = new double[cols - 1]; //creates a new array to store the values of the decision and slack variables for a solution
        for(int i = 0; i < cols - 1; i++){
            boolean isBasic = false; //iterates through the table checking if a variable is basic
            for(int j = 0; j < rows; j++){
                if(table[j][i] == 1){
                    solution[i] = table[j][cols - 1]; //sets value in solution array equal to value in table
                    isBasic = true; //if basic variable then breaks from the for loop
                    break;
                }
            }
            if(!isBasic){
                solution[i] = 0;
            }
        }
        return solution;
    }
}
