import java.awt.event.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.lang.reflect.Array;
import java.util.*;

public class menuGUI extends JFrame{
    // the following public attributes are declared with meaningful names to be used throughout the constructor and other methods
    JMenuBar menubar;
    JMenu file, simplex, kruskals, prims;
    JMenuItem exit, home, runSimplex, runPrims, runKruskals, learnSimplex, learnPrims, learnKruskals;
    JLabel prompt, numV, sourceLabel, destinationLabel, weightLabel, xLabel, yLabel, zLabel, x1Label, x2Label, x3Label, x4Label, valueLabel, numberInequality, reminder, objLabel, xObj, yObj, zObj, x1Obj, x2Obj,x3Obj, x4Obj, plusObj, errorMessageLabel, kruskalsError;
    JButton introSimplex, introPrims, introKruskals, addEdge, removeEdge, runAlgorithm, addButton, twoVar,threeVar, fourVar, runSimplexButton, removeEdgeButton, resetGraph;
    JPanel promptPanel, buttonPanel, graphButtonPanel, introPanel, mainPanel, kruskalAdd, simPanel, simButtonPanel, inequalityPanel, simplexPanel, tableauPanel, tableauToAdd, combPanel, fillPanel;
    JTextArea sourceTA, destTA, weightTA, vertices, xTA, yTA, zTA, x1TA, x2TA, x3TA, x4TA, valueTA, xObjTA, yObjTA, zObjTA,x1ObjTA, x2ObjTA, x3ObjTA, x4ObjTA;
    JComboBox numInequality;
    double[][] constraints;
    double[] obj;
    int numVAR, counter;
    public static String[] rowHeaders, colHeaders;
    ArrayList<Integer> toAdd = new ArrayList<>();
    DisplayPanel displayPanel = new DisplayPanel();
    static int selectedTab;
    static ArrayList<Edge> mstEdges;

    public menuGUI(){
        setLayout(new BorderLayout()); //sets the window layout as a border layout
        //creating the menu bar
        menubar = new JMenuBar();
        setJMenuBar(menubar);

        errorMessageLabel = new JLabel(""); // initialise the error message label
        errorMessageLabel.setForeground(Color.RED); //set the error message colour to red
        numVAR = 0; //initialise the number of variables to 0 for input validation
        kruskalsError = new JLabel("");
        kruskalsError.setForeground(Color.RED);

        kruskalAdd = new JPanel(new FlowLayout()); //new panel underneath the button panel
        combPanel = new JPanel(new GridLayout(0,2)); //creating a new panel with 2 columns to make 2 halves which store the graph and the table
        resetGraph = new JButton("Reset");

        //adding 'file' menu option to the menu bar
        file = new JMenu("File");
        menubar.add(file);
        home = new JMenuItem("Home");
        file.add(home);
        exit = new JMenuItem("Exit");
        file.add(exit);

        //creating menu options for each algorithm
        simplex = new JMenu("Simplex Algorithm");
        menubar.add(simplex);

        kruskals = new JMenu("Kruskal's Algorithm");
        menubar.add(kruskals);

        prims = new JMenu("Prim's Algorithm");
        menubar.add(prims);

        //creating and adding the sub-options for each algorithm to the menubar
        runSimplex = new JMenuItem("Run Algorithm");
        runPrims = new JMenuItem("Run Algorithm");
        runKruskals = new JMenuItem("Run Algorithm");


        simplex.add(runSimplex);
        kruskals.add(runKruskals);
        prims.add(runPrims);


        learnSimplex = new JMenuItem("Learn Algorithm");
        learnKruskals = new JMenuItem("Learn Algorithm");
        learnPrims = new JMenuItem("Learn Algorithm");

        simplex.add(learnSimplex);
        kruskals.add(learnKruskals);
        prims.add(learnPrims);

        addEdge = new JButton("Add Edge");
        removeEdge = new JButton("Remove Edge");
        runAlgorithm = new JButton("Run Algorithm");

        twoVar = new JButton("2 Variables");
        threeVar = new JButton("3 Variables");
        fourVar = new JButton("4 Variables");

        runSimplexButton = new JButton("Run");

        addButton = new JButton("Add");

        removeEdgeButton = new JButton("Remove");

        originalWindow(); //calls the originalWindow method to add the buttons and label to the window
        add(promptPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        //adding an event action listener to each menu item within each menu bar option
        event e = new event();
        runKruskals.addActionListener(e);
        runSimplex.addActionListener(e);
        learnSimplex.addActionListener(e);
        learnPrims.addActionListener(e);
        learnKruskals.addActionListener(e);
        introSimplex.addActionListener(e);
        introKruskals.addActionListener(e);
        introPrims.addActionListener(e);
        runPrims.addActionListener(e);

        home.addActionListener(e);
        exit.addActionListener(e);
        addEdge.addActionListener(e);
        removeEdge.addActionListener(e);
        addButton.addActionListener(e);
        runAlgorithm.addActionListener(e);
        twoVar.addActionListener(e);
        threeVar.addActionListener(e);
        fourVar.addActionListener(e);
        runSimplexButton.addActionListener(e);

        removeEdgeButton.addActionListener(e);
        resetGraph.addActionListener(e);


    }

    private void originalWindow(){ // procedure called in the constructor to generate initial panels
        promptPanel = new JPanel(new BorderLayout());
        prompt = new JLabel("Select an option from the menu", SwingConstants.CENTER);
        promptPanel.add(prompt, BorderLayout.CENTER);
        promptPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); //defining the border size for the panel in the north section


        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 20, 50)); //splitting the center panel into 3 sections for 3 buttons
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 60, 20)); //defining the border size for the button panel in the center section

        //creating the button for kruskal's algorithm introduction, with image, label and text positioning defined
        ImageIcon kruskalsIcon = new ImageIcon("C:\\Users\\averm\\Pictures\\kruskalsimage.png");
        introKruskals = new JButton();
        introKruskals.setText("Kruskal's Algorithm Introduction");
        introKruskals.setHorizontalTextPosition(JButton.CENTER);
        introKruskals.setVerticalTextPosition(JButton.TOP);
        introKruskals.setFocusable(false);
        introKruskals.setIcon(kruskalsIcon);
        buttonPanel.add(introKruskals);

        //creating the button for the simplex algorithm introduction, with image, label and text positioning defined
        ImageIcon simplexIcon = new ImageIcon("C:\\Users\\averm\\Pictures\\simplex.png");
        introSimplex = new JButton();
        introSimplex.setText("Simplex Algorithm Introduction");
        introSimplex.setHorizontalTextPosition(JButton.CENTER);
        introSimplex.setVerticalTextPosition(JButton.TOP);
        introSimplex.setFocusable(false);
        introSimplex.setIcon(simplexIcon);
        buttonPanel.add(introSimplex);

        //creating the button for prim's algorithm introduction, with image, label and text positioning defined
        ImageIcon primsIcon = new ImageIcon("C:\\Users\\averm\\Pictures\\kruskalsimage.png");
        introPrims = new JButton();
        introPrims.setText("Prim's Algorithm Introduction");
        introPrims.setHorizontalTextPosition(JButton.CENTER);
        introPrims.setVerticalTextPosition(JButton.TOP);
        introPrims.setFocusable(false);
        introPrims.setIcon(primsIcon);
        buttonPanel.add(introPrims);
    }
    public void restoreHome(){ //procedure which  adds the initial components back to the window returning to the original state
        getContentPane().removeAll();
        add(promptPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        revalidate(); // ensuring all layout changes have been validated
        repaint(); //updating the window to display the changes to the window
    }
    public void graphSettings() {
        getContentPane().removeAll(); //removes all current components from the window
        promptPanel.removeAll();
        mainPanel = new JPanel(new BorderLayout()); //creates a new panel with a border layout
        graphButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // a new panel for the three buttons
        graphButtonPanel.add(resetGraph); //adding the row of buttons with the reset graph button first
        graphButtonPanel.add(addEdge);
        graphButtonPanel.add(removeEdge);
        graphButtonPanel.add(runAlgorithm);
        graphButtonPanel.add(kruskalsError);


        mainPanel.add(graphButtonPanel, BorderLayout.NORTH);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL); //this creates a separator line underneath the buttons
        mainPanel.add(separator, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.NORTH);
        revalidate();
        repaint();
    }

    public void kruskalsIntro() {
        getContentPane().removeAll();
        promptPanel.removeAll();

        introPanel = new JPanel(new BorderLayout ());
        JLabel kruskalsTitle = new JLabel("Kruskal's Algorithm", SwingConstants.CENTER);
        kruskalsTitle.setFont(new Font("Arial", Font.BOLD, 36));
        kruskalsTitle.setBorder(new EmptyBorder(20, 0, 10, 0));
        introPanel.add(kruskalsTitle, BorderLayout.NORTH);
        add(introPanel, BorderLayout.CENTER);
        revalidate(); //refreshing the GUI
        repaint();
    }
    public void resetGraphDisplay(){
        displayPanel.getEdges().clear(); //clear the arraylists storing information about the edges
        displayPanel.getEdgeWeights().clear();
        displayPanel.getEdgeColourList().clear();
        displayPanel.getNodeLabels().clear(); // clear the nodes and their positions in the DisplayPanel
        displayPanel.getNodePositions().clear();

        toAdd.clear(); // clear the edges stored in `toAdd`

        displayPanel.revalidate(); // refresh the GUI to remove the displayed graph
        displayPanel.repaint();

        kruskalsError.setText(""); // clear any error messages
    }
    public void edge(){
        kruskalAdd.removeAll();
        combPanel.removeAll();

        sourceTA = new JTextArea(2, 5); //adding the text areas to get user input for source, destination and weight
        destTA = new JTextArea(2, 5);
        weightTA = new JTextArea(2, 5);
        sourceLabel = new JLabel("Source:"); //adding labels to prompt the user for which input
        destinationLabel = new JLabel("Destination:");
        weightLabel = new JLabel("Weight:");
        vertices = new JTextArea(2, 5); // adds the text area for user to enter number of vertices
        numV = new JLabel("Number of Vertices:");



        kruskalAdd.add(sourceLabel); //the following section of code adds each of these labels and text areas to the panel
        kruskalAdd.add(sourceTA);
        kruskalAdd.add(Box.createVerticalStrut(10)); // 10 pixels of vertical spacing
        kruskalAdd.add(destinationLabel);
        kruskalAdd.add(destTA);
        kruskalAdd.add(Box.createVerticalStrut(10)); // 10 pixels of vertical spacing
        kruskalAdd.add(weightLabel);
        kruskalAdd.add(weightTA);
        kruskalAdd.add(Box.createHorizontalStrut(20)); //adding space between the weight text area and the add button
        kruskalAdd.add(addButton);
        kruskalAdd.add(numV);
        kruskalAdd.add(vertices);


        mainPanel.add(kruskalAdd, BorderLayout.CENTER); //adding the new panel to the main panel
        displayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); //setting a black border around displayPanel
        fillPanel = new JPanel(new GridLayout(2,1));
        fillPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //combPanel = new JPanel(new GridLayout(0,2)); //creating a new panel with 2 columns to make 2 halves which store the graph and the table
        combPanel.add(displayPanel); //add the graph to the left half of the combination panel
        combPanel.add(fillPanel); //add an empty panel to the right half of the combination panel
        combPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); //create a border around the combination panel
        add(combPanel, BorderLayout.CENTER); //add the combination panel to the main panel
        revalidate(); //refresh the GUI
        repaint();
    }
    public void addToGraph() {
        try {
            String source = sourceTA.getText(); //extracting the inputted text from the text areas
            if (!source.matches("[A-Z]")) { //checking source node is a single capital letter
                throw new IllegalArgumentException("Please enter source node as a single capital letter."); //error message to indicate mistake to user
            }
            kruskalsError.setText("");//error message set to empty string if input is valid
            char sourceCH = source.charAt(0); //converting the string inputs to characters

            String dest = destTA.getText();
            if (!dest.matches("[A-Z]")) { //checking destination node is a singel capital letter
                throw new IllegalArgumentException("Please enter destination node as a single capital letter."); //error message to indicate mistake to user
            }
            kruskalsError.setText(""); //error message set to empty string if input is valid
            char destCH = dest.charAt(0);

        String weight = weightTA.getText();
        int weightInt;
        try {
            weightInt = Integer.parseInt(weight);
            kruskalsError.setText(""); //error message is set to empty string if valid input entered
        } catch (NumberFormatException e) { //throws exception if input is not a number
            throw new IllegalArgumentException("Weight must be a valid number."); //error message text is changed to indicate mistake to user
        }

        String pair = source + dest; //formats the source and destination as one string
        String reversePair = dest + source; //making the reverse of the edge e.g. 'AB' becomes 'BA'
        if (displayPanel.edges.contains(pair) || displayPanel.edges.contains(reversePair)) { //checking if the user has already entered the inputted edge into the graph
            throw new IllegalArgumentException("This edge already exists in the graph."); //setting the error message text saying that they have already entered the edge
        }
        toAdd.add((int) sourceCH); //adds the source, destination and weight to an arraylist of information about edges to add
        toAdd.add((int) destCH);
        toAdd.add(weightInt);
        displayPanel.addEdge(pair, weightInt); //adds the edge to the panel
        revalidate(); //refreshing the GUI
        repaint();
        }catch(IllegalArgumentException e){
            kruskalsError.setText(e.getMessage());
        }
        revalidate();
        repaint();

    }
    public void removeFromGraph() {
        kruskalAdd.removeAll();
        JTextArea removeSourceTA = new JTextArea(2,5); //adding in source text area for edge to be removed
        JTextArea removeDestTA = new JTextArea(2,5); //adding in destination text area for edge to be removed
        JLabel removeSourceLabel = new JLabel("Source: "); //label to indicate purpose of removeSourceTA
        JLabel removeDestLabel = new JLabel("Destination: "); //label to indicate purpose of removeDestTA

        kruskalAdd.add(removeSourceLabel); //adding the various components to the kruskalAdd panel
        kruskalAdd.add(removeSourceTA);
        kruskalAdd.add(Box.createVerticalStrut(10)); // 10 pixels of vertical spacing
        kruskalAdd.add(removeDestLabel);
        kruskalAdd.add(removeDestTA);
        kruskalAdd.add(removeEdgeButton); //add the remove edge button
        revalidate(); //refresh the GUI
        repaint();

    }
    public void remove() {
        try {
            String source = sourceTA.getText().trim(); // get the source node from the text area
            if (!source.matches("[A-Z]")) { //checking that input is single capital letter
                throw new IllegalArgumentException("Please enter a source node as a single capital letter.");
            }

            String dest = destTA.getText().trim(); // get the destination node from the text area
            if (!dest.matches("[A-Z]")) { //checking that input is single capital letter
                throw new IllegalArgumentException("Please enter a destination node as a single capital letter.");
            }

            String pair = source + dest; // construct the edge string
            if (!displayPanel.edges.contains(pair)) { //checking if inputted edge is in the graph
                throw new IllegalArgumentException("The specified edge does not exist in the graph.");
            }

            int index = displayPanel.edges.indexOf(pair); // get the edge index
            displayPanel.edges.remove(index); // remove the edge
            displayPanel.edgeWeights.remove(index); // remove the corresponding weight
            displayPanel.edgeColourList.remove(index); // remove the color

            displayPanel.revalidate();//update the graph
            displayPanel.repaint();


            char sourceChar = source.charAt(0); //remove the edge from the `toAdd` list
            char destChar = dest.charAt(0);

            for (int i = 0; i < toAdd.size(); i += 3) {
                if ((toAdd.get(i) == (int) sourceChar && toAdd.get(i + 1) == (int) destChar) || (toAdd.get(i) == (int) destChar && toAdd.get(i + 1) == (int) sourceChar)) {
                    toAdd.remove(i + 2); //remove weight
                    toAdd.remove(i + 1); //remove destination
                    toAdd.remove(i); //remove source
                    break; //exit loop after removing the edge
                }
            }
            boolean isValidSourceNode = false;
            for(int i = 0; i < toAdd.size(); i ++){ //checking that the source node entered exists in the graph
                if(toAdd.get(i) == sourceChar){ //if the current node is the source node then it sets the boolean to true
                    isValidSourceNode = true;
                }
            }
            if (!isValidSourceNode) {
                int nodeIndex = displayPanel.getNodeLabels().indexOf(sourceChar); // find the index of the source node in the nodeLabels list

                if (nodeIndex != -1) {
                    displayPanel.getNodeLabels().remove(nodeIndex); //remove the node label and its corresponding position
                    displayPanel.getNodePositions().remove(nodeIndex);

                    displayPanel.revalidate(); // refresh the GUI
                    displayPanel.repaint();
                }
            }
            boolean isValidDestNode = false;
            for(int i = 0; i < toAdd.size(); i ++){ //checking that the destination node entered exists in the graph
                if(toAdd.get(i) == destChar){ //if the current node is the destination node then it sets the boolean to true
                    isValidDestNode = true;
                }
            }
            if (!isValidDestNode) {
                int nodeIndex = displayPanel.getNodeLabels().indexOf(destChar); //find the index of the destination node in the nodeLabels list

                if (nodeIndex != -1) {
                    displayPanel.getNodeLabels().remove(nodeIndex); // remove the node label and its corresponding position
                    displayPanel.getNodePositions().remove(nodeIndex);

                    displayPanel.revalidate(); // refresh the GUI to reflect the changes
                    displayPanel.repaint();
                }
            }

            kruskalsError.setText(""); // clear any previous error messages
        } catch (IllegalArgumentException e) {
            kruskalsError.setText(e.getMessage()); // display an error message to the user
        }
    }



    public JPanel createMSTPanel(ArrayList<Edge> mstEdges, ArrayList<Edge> allEdges) {
        JPanel mstPanel = new JPanel(new GridLayout(0, 2)); // Create a panel with 2 columns

        JLabel edgeLabel = new JLabel("Edge"); //adding label to left column
        JLabel actionLabel = new JLabel("Add/Reject"); //adding label to right column
        edgeLabel.setFont(new Font("Arial",Font.PLAIN, 15 )); //setting font, size and style of text in label
        edgeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); //adding border around label
        actionLabel.setFont(new Font("Arial",Font.PLAIN, 15 ));//setting font, size and style of text in label
        actionLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); //adding border around label
        mstPanel.add(edgeLabel); //add the two labels to the panel
        mstPanel.add(actionLabel);

        //sort all edges by weight
        Collections.sort(allEdges);

        //iterate through all edges and check if they are in the MST
        for (Edge edge : allEdges) {
            JLabel edgeDisplay = new JLabel((char)(edge.source + 65)+ "" + (char)(edge.dest + 65)); // label for each edge
            String action = "Reject";
            if(mstEdges.contains(edge)){
                action = "Add";
            }
            JLabel actionDisplay = new JLabel(action); // display add or reject
            edgeDisplay.setFont(new Font("Arial",Font.PLAIN, 15 ));//setting font, size and style of text in label
            edgeDisplay.setBorder(BorderFactory.createLineBorder(Color.BLACK));//adding border around label
            actionDisplay.setFont(new Font("Arial",Font.PLAIN, 15 ));//setting font, size and style of text in label
            actionDisplay.setBorder(BorderFactory.createLineBorder(Color.BLACK));//adding border around label
            mstPanel.add(edgeDisplay); //add the labels to the panel
            mstPanel.add(actionDisplay);
        }

        return mstPanel; // return the constructed panel
    }
    public void runKruskalsAlg(){
        String graphV = vertices.getText(); //extracts the text for the number of vertices from the text area
        char numVertices = graphV.charAt(0); //converts the string input to a character

        int graphSize = numVertices; //converts the character to an integer to be used as a parameter
        Kruskals graph = new Kruskals(graphSize); //creating a new object of type Kruskals
        for(int i = 0; i + 2 < toAdd.size(); i+=3){ //incrementing in 3 to ensure all 3 pieces of information for a single node are added correctly
            graph.addEdge(toAdd.get(i), toAdd.get(i+1), toAdd.get(i+2)); //adding the source destination and weight as an edge
        }
        if (!isConnected(graph, graphSize)) { //checking if the graph is connected or not
            kruskalsError.setText("Please ensure the graph is connected."); //setting the error message text to indicate to the user
            return; // exit the method if the graph is not connected
        }
        ArrayList<Edge> edgeList = graph.edges; //list of edges in the graph
        mstEdges = graph.kruskalMST(); // list of edges in the minimum spanning tree
        JPanel mstPanel = createMSTPanel(mstEdges, graph.edges); //panel containing the table of added/rejected arcs


        // create a container panel to hold both the graph and MST panels
        combPanel.add(fillPanel); //add the empty fill panel to the right column of the combination panel
        JTabbedPane explainPane = new JTabbedPane(); //creating the tabbed pane to contain the edges
        JPanel kruskalsPromptPanel = new JPanel(new BorderLayout()); //creating a new panel to store the intro information for the tabbed pane
        JLabel kruskalsPrompt = new JLabel("Click through the edge tabs to see how the minimum spanning tree builds up."); //prompting the user to click through the edges
        kruskalsPrompt.setFont(new Font("Arial", Font.BOLD, 15)); //setting font size and style for text
        JLabel colourLabel = new JLabel("Green = Add. Red = Reject"); //informing the user about the use of colours in the highlighted edges
        colourLabel.setFont(new Font("Arial", Font.BOLD, 15)); //setting font size and style for text
        kruskalsPromptPanel.add(kruskalsPrompt, BorderLayout.NORTH); //adding prompt to panel
        kruskalsPromptPanel.add(colourLabel, BorderLayout.CENTER); //adding colour information to panel
        explainPane.add(kruskalsPromptPanel, "Intro"); //adding the panel to the tabbed pane with the title 'Intro'
        Collections.sort(edgeList); //sorting the list of edges, so they can be added to the tabbed pane in sorted order
        for(Edge edge: edgeList){
            JPanel edgePanel = new JPanel(); //creating a new tab for each edge in the graph
            JTextPane explanation = explainEdge(mstEdges, edgeList, edge); //adding the text pane to contain the explanation of each edge being added/rejected
            edgePanel.add(explanation); //add the text pane to the panel for the tabbed pane
            String title = (char)(edge.source + 65) + "" + (char)(edge.dest+65); //make the edge the title of the tab e.g. 'AB'
            explainPane.add(edgePanel, title); //add the panel to the tabbedpane with the edge title
        }
        explainPane.addChangeListener(new ChangeListener() { //adding a change listener to the tabbed pane to detect when the user selects a new tab
            @Override
            public void stateChanged(ChangeEvent e) {
                JTabbedPane sourcePane = (JTabbedPane) e.getSource(); //initialising a tabbed pane as the item that was updated/changed
                selectedTab = sourcePane.getSelectedIndex() - 1; //getting the index of the current selected tab

                for(String edge: displayPanel.getEdges()){ //iterating through list of edges
                    displayPanel.highlightEdge(edge, Color.BLACK); //setting all edges to be highlighted in black
                }
                for(int i = 0; i < selectedTab + 1; i++){ //iterating through the edges
                    Edge edge = graph.edges.get(i); //setting an edge object to the current edge
                    if(mstEdges.contains(edge)){ //checking if the current edge is in the minimum spanning tree
                        String edgeString = (char)(edge.source + 65) + "" + (char)(edge.dest + 65); //setting the string of the edge to the source + destination e.g. 'AB'
                        displayPanel.highlightEdge(edgeString, Color.GREEN); //sets the edge colour to green
                    } else{
                        String edgeString = (char)(edge.source + 65) + "" + (char)(edge.dest + 65); //setting the string of the edge to the source + destination e.g. 'AB'
                        displayPanel.highlightEdge(edgeString, Color.RED); //sets the edge colour to red if the edge is not in the MST
                    }
                }
            }
        });
        fillPanel.add(mstPanel); //adds the add/reject table to the top half of the right side of the panel
        fillPanel.add(explainPane); //adds the tabbed pane to the bottom half of the right side of the panel
        combPanel.add(fillPanel); //adds the updated fillPanel to the overall combination panel
        revalidate(); //refreshing the GUI
        repaint();
    }
    private boolean isConnected(Kruskals graph, int numVertices) {
        boolean[] visited = new boolean[numVertices];
        int startNode = Integer.MAX_VALUE; //assigns the largest value to start node
        for (Edge edge : graph.edges) {
            startNode = Math.min(startNode, Math.min(edge.source, edge.dest)); //getting the first node in the graph to begin the dfs
        }
        dfs(startNode, visited, graph); //doing a depth first search starting from the first node in the graph

        for (Edge edge : graph.edges) {
            if (!visited[edge.source] || !visited[edge.dest]) { //checking if the source and node of each edge has been visited
                return false; //if not then the graph is not connected
            }
        }

        return true;
    }

    private void dfs(int node, boolean[] visited, Kruskals graph){ //depth first search method
        visited[node] = true;
        for(Edge edge: graph.edges){ //going through each edge in the graph
            if(edge.source == node && !visited[edge.dest]){ //checking whether the source has been visited and the destination hasn't
                dfs(edge.dest, visited, graph); //recursively doing depth first search starting from the destination
            } else if(edge.dest == node && !visited[edge.source]){//checking whether the destination has been visited and the source hasn't
                dfs(edge.source, visited, graph);//recursively doing depth first search starting from the source
            }
        }
    }
    public static JTextPane explainEdge(ArrayList<Edge> mstEdges, ArrayList<Edge> allEdges, Edge edge) {
        JTextPane explanationPane = new JTextPane();
        int total = 0;
        for (Edge mstEdge : mstEdges) {
            total += mstEdge.weight; //summing the total weight of edges in the minimum spanning tree
        }
        if (mstEdges.contains(edge)) { //checking if edge is in minimum spanning tree
            if (edge == allEdges.get(0)) { //the first edge in the minimum spanning tree
                explanationPane.setText("The edges are first sorted into ascending weight order. This is the edge with the lowest weight.");
                explanationPane.setText(explanationPane.getText() + "\nNo cycles are formed by adding this edge to the minimum spanning tree, hence we add it.");
                explanationPane.setText(explanationPane.getText() + "\nOn the table of added and rejected edges, this edge is listed as 'Add'.");
                explanationPane.setText(explanationPane.getText() + "\n");//adding some spacing lines to make text more readable
                explanationPane.setText(explanationPane.getText() + "\n");
                explanationPane.setText(explanationPane.getText() + "Click the next tab to see the next edge!");
            } else if (edge == mstEdges.get(mstEdges.size() - 1)) { //the final edge in the minimum spanning tree
                explanationPane.setText("Adding this edge doesn't form any cycles. Looking at the minimum spanning tree,");
                explanationPane.setText(explanationPane.getText() + "\nall the nodes are connected by edges, so our minimum spanning tree is complete.");
                explanationPane.setText(explanationPane.getText() + "\n");//adding some spacing lines to make text more readable
                explanationPane.setText(explanationPane.getText() + "\n");
                explanationPane.setText(explanationPane.getText() + "Click the next tab to see the next edge!");
            }else if(edge == allEdges.get(allEdges.size()-1)){ //the final edge in the entire graph
                explanationPane.setText("This is the final edge added to the minimum spanning tree. Looking at the minimum spanning tree,");
                explanationPane.setText(explanationPane.getText() + "\nall the nodes in the graph are connected by edges, so our minimum spanning tree is complete.");
                explanationPane.setText(explanationPane.getText() + "\nIn the table of edges, this edge is listed as 'Add'");
                explanationPane.setText(explanationPane.getText() + "\n");//adding some spacing lines to make text more readable
                explanationPane.setText(explanationPane.getText() + "\n");
                explanationPane.setText(explanationPane.getText() + "The final weight of the minimum spanning tree is: " + total);
                explanationPane.setText(explanationPane.getText() + "\nEdges included in the minimum spanning tree:");
                for(int i = 0; i < mstEdges.size(); i++){ //outputting the list of edges added to the minimum spanning tree
                    explanationPane.setText(explanationPane.getText() + "\n" + (char)(mstEdges.get(i).source + 65) + "" + (char)(mstEdges.get(i).dest + 65));
                }
            }else { //other edges in the minimum spanning tree
                explanationPane.setText("We consider what happens to the minimum spanning tree if we add this edge.");
                explanationPane.setText(explanationPane.getText() + "\nNo cycles are formed by adding this edge, so we can add it.");
                explanationPane.setText(explanationPane.getText() + "\nLooking at the table of edges, we see this edge is given 'Add'.");
                explanationPane.setText(explanationPane.getText() + "\n");//adding some spacing lines to make text more readable
                explanationPane.setText(explanationPane.getText() + "\n");
                explanationPane.setText(explanationPane.getText() + "Click the next tab to see the next edge!");
            }
        }else if(edge == allEdges.get(allEdges.size()-1)){ //final edge in the graph that is not in the minimum spanning tree
            explanationPane.setText("This is the final edge we consider but the minimum spanning tree is complete, so we reject it.");
            explanationPane.setText(explanationPane.getText() + "\nLooking at the table of edges, this edge is listed as 'Reject'.");
            explanationPane.setText(explanationPane.getText() + "\nThe total weight is: " + total);
            explanationPane.setText(explanationPane.getText() + "\nEdges included in the minimum spanning tree:");
            for(int i = 0; i < mstEdges.size(); i++){ //outputting the list of edges added to the minimum spanning tree
                explanationPane.setText(explanationPane.getText() + "\n" + (char)(mstEdges.get(i).source + 65) + "" + (char)(mstEdges.get(i).dest + 65));
            }
        }else{ //other edges that are rejected from the minimum spanning tree
            explanationPane.setText("Adding this edge to the minimum spanning tree would form a cycle, therefore we reject it.");
            explanationPane.setText(explanationPane.getText() + "\nOn the table of added and rejected edges, we see that this edge is listed as 'Reject'.");
            explanationPane.setText(explanationPane.getText() + "\n"); //adding some spacing lines to make text more readable
            explanationPane.setText(explanationPane.getText() + "\n");
            explanationPane.setText(explanationPane.getText() + "Click the next tab to see the next edge!");
        }
        explanationPane.setFont(new Font("Arial", Font.PLAIN, 15)); //setting the font size and style for the explanation
        explanationPane.setEditable(false); //making it so the user cannot edit the text of the text pane
        return explanationPane; //return the text pane
    }
    public static int getSelectedTab(){
        return selectedTab;
    }
    public static ArrayList<Edge> getMSTEdges(){
        return mstEdges;
    }
    public void simplexSettings() {
        String[] inequalityOptions = {"--", "2", "3", "4"}; //setting the options for the drop-down menu
        numInequality = new JComboBox(inequalityOptions); //creating the drop-down menu and a label for it
        numberInequality = new JLabel("Select number of inequalities");
        getContentPane().removeAll(); //removes all current components from the window
        promptPanel.removeAll();
        simPanel = new JPanel(new BorderLayout()); //creates a new panel with a border layout
        simButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // a new panel for the three buttons
        simButtonPanel.add(numberInequality); //adding the components to the top panel
        simButtonPanel.add(numInequality);
        simButtonPanel.add(twoVar);
        simButtonPanel.add(threeVar);
        simButtonPanel.add(fourVar);
        simButtonPanel.add(runSimplexButton);
        simButtonPanel.add(errorMessageLabel);


        simPanel.add(simButtonPanel, BorderLayout.NORTH); //adding this to the main panel

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL); //this creates a separator line underneath the buttons
        simPanel.add(separator, BorderLayout.CENTER);

        add(simPanel, BorderLayout.NORTH);
        revalidate(); //updating the GUI
        repaint();
    }

    public void setSimplex(JButton button){
        simplexPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,5, 1)); //new panel which goes below the buttons
        inequalityPanel = new JPanel(); //panel to contain various row panels, using a box layout to stack them on top of each other
        inequalityPanel.setLayout(new BoxLayout(inequalityPanel, BoxLayout.Y_AXIS));

        counter = Integer.parseInt(numInequality.getSelectedItem().toString()); //initialises a counter variable
        if (button == twoVar && (numInequality.getSelectedItem().toString()) != "--"){ //checks which button pressed and ensures a value was selected from the drop-down
            numVAR = 2;
            rowHeaders = new String[counter + 1]; //creating row headers array
            for(int a = 0; a < counter; a ++){
                rowHeaders[a] = Character.toString(a+114); //adding slack variable letters starting from r, depending on number of inequalities (counter)
            }
            rowHeaders[counter] = "P"; //setting final index as P, this is always the row header of the bottom row
            colHeaders = new String[counter + 3]; //creating list of column headers
            colHeaders[0] = "x"; //for 2 variables, the first 2 column headers are the 2 variables, x and y.
            colHeaders[1] = "y";
            for(int a = 0; a < counter; a ++){ //adding column headers of slack variables in a similar way to row headers using ASCII
                colHeaders[a+2] = Character.toString(a+114);
            }
            colHeaders[counter + 2] = "Value"; //the row header of the final column is always Value
            for (int i = 0; i < counter; i++) { //for each row of inequalities
                JPanel rowPanel = new JPanel(); //new panel is created
                rowPanel.setLayout(new FlowLayout());
                rowPanel.setMaximumSize(new Dimension(650, 150)); //bring the rows closer together

                JLabel plus1 = new JLabel("+"); //initialising all the components that need to be added in each row
                plus1.setBorder(new EmptyBorder(0, 10, 0, 10)); //ensuring there is spacing between the components
                xTA = new JTextArea(2, 5);
                yTA = new JTextArea(2, 5);
                xLabel = new JLabel("x");
                xLabel.setFont(new Font("Arial", Font.PLAIN, 15));
                yLabel = new JLabel("y");
                yLabel.setFont(new Font("Arial", Font.PLAIN, 15));
                xLabel.setBorder(new EmptyBorder(0,0, 5, 0));
                yLabel.setBorder(new EmptyBorder(0,0, 5, 0));
                valueLabel = new JLabel("=");
                valueLabel.setFont(new Font("Arial", Font.PLAIN, 15));
                valueLabel.setBorder(new EmptyBorder(0, 20, 0, 20));
                valueTA = new JTextArea(2, 5);

                xTA.setBorder(new EmptyBorder(0,0, 10, 0));
                yTA.setBorder(new EmptyBorder(0,0, 10, 0));

                rowPanel.add(xTA); //adding each of the components to the row panel
                rowPanel.add(xLabel);
                rowPanel.add(plus1);
                rowPanel.add(yTA);
                rowPanel.add(yLabel);
                rowPanel.add(valueLabel);
                rowPanel.add(valueTA);

                inequalityPanel.add(rowPanel); //adding the row of components to the inequality panel
            }
            JPanel objectivePanel = new JPanel(); //creating a new objective panel which will go at the bottom of the inequality panel
            objectivePanel.setLayout(new FlowLayout());
            objectivePanel.setMaximumSize(new Dimension(650, 150)); //once again limiting the size it can be
            objLabel = new JLabel("P ="); //defining all the components with adequate spacing
            objLabel.setBorder(new EmptyBorder(0, 0, 0, 20));
            xObjTA = new JTextArea(2, 5);
            yObjTA = new JTextArea(2, 5);
            xObj = new JLabel("x");
            xObj.setFont(new Font("Arial", Font.PLAIN, 15));
            yObj = new JLabel("y");
            yObj.setFont(new Font("Arial", Font.PLAIN, 15));
            xObj.setBorder(new EmptyBorder(0,0, 5, 0));
            yObj.setBorder(new EmptyBorder(0,0, 5, 0));
            plusObj = new JLabel("+");
            plusObj.setBorder(new EmptyBorder(0, 10, 0, 10));

            objectivePanel.add(objLabel); //adding the components to the objective panel
            objectivePanel.add(xObjTA);
            objectivePanel.add(xObj);
            objectivePanel.add(plusObj);
            objectivePanel.add(yObjTA);
            objectivePanel.add(yObj);
            inequalityPanel.add(objectivePanel); //adding the objective panel to the inequality panel

        } else if (button == threeVar && (numInequality.getSelectedItem().toString()) != "--") { //checking input button
            numVAR = 3;
            rowHeaders = new String[counter + 1]; //creating list of row headers
            for(int a = 0; a < counter; a ++){ //adding each slack variable beginning from r depending on number of inequalities
                rowHeaders[a] = Character.toString(a+114);
            }
            rowHeaders[counter] = "P"; //setting final index as P, this is always the row header of the bottom row
            colHeaders = new String[counter + 4]; //creating list of column headers
            colHeaders[0] = "x"; //adding first few items to column headers'
            colHeaders[1] = "y";
            colHeaders[2] = "z";
            for(int a = 0; a < counter; a ++){
                colHeaders[a+3] = Character.toString(a+114); //adding the slack variables to the column headers in a similar manner to above
            }
            colHeaders[counter + 3] = "Value"; //adding the final column header as 'Value', which is always the same
            for (int i = 0; i < counter; i++) {
                JPanel rowPanel = new JPanel(); //creating a new panel for each row
                rowPanel.setLayout(new FlowLayout());
                rowPanel.setMaximumSize(new Dimension(650, 150));

                JLabel plus1 = new JLabel("+"); //creating the labels to go between the text areas
                plus1.setBorder(new EmptyBorder(0, 10, 0, 10));

                JLabel plus2 = new JLabel("+");
                plus2.setBorder(new EmptyBorder(0, 10, 0, 10));

                xTA = new JTextArea(2, 5); //defining the components for each row
                yTA = new JTextArea(2, 5);
                zTA = new JTextArea(2, 5);
                xLabel = new JLabel("x");
                xLabel.setFont(new Font("Arial", Font.PLAIN, 15));
                yLabel = new JLabel("y");
                yLabel.setFont(new Font("Arial", Font.PLAIN, 15));
                zLabel = new JLabel("z");
                zLabel.setFont(new Font("Arial", Font.PLAIN, 15));
                xLabel.setBorder(new EmptyBorder(0,0, 5, 0)); //adding adequate spacing between components
                yLabel.setBorder(new EmptyBorder(0,0, 5, 0));
                zLabel.setBorder(new EmptyBorder(0,0, 5, 0));
                valueLabel = new JLabel("=");
                valueLabel.setFont(new Font("Arial", Font.PLAIN, 15));
                valueLabel.setBorder(new EmptyBorder(0, 20, 0, 20));
                valueTA = new JTextArea(2, 5);

                xTA.setBorder(new EmptyBorder(0,0, 10, 0));
                yTA.setBorder(new EmptyBorder(0,0, 10, 0));
                zTA.setBorder(new EmptyBorder(0,0, 10, 0));

                rowPanel.add(xTA); //adding each of the components to the rowPanel in the order they appear due to flow layout
                rowPanel.add(xLabel);
                rowPanel.add(plus1);
                rowPanel.add(yTA);
                rowPanel.add(yLabel);
                rowPanel.add(plus2);
                rowPanel.add(zTA);
                rowPanel.add(zLabel);
                rowPanel.add(valueLabel);
                rowPanel.add(valueTA);

                inequalityPanel.add(rowPanel); //adding each row panel to the inequality panel
            }
            JPanel objectivePanel = new JPanel(); //creating an objective panel to go at the bottom of the inequality panel
            objectivePanel.setLayout(new FlowLayout()); //the panel will have a flow layout
            objectivePanel.setMaximumSize(new Dimension(650, 150)); //limiting the size of the panel
            objLabel = new JLabel("P ="); //the following section defines the components that go into the objective function
            objLabel.setBorder(new EmptyBorder(0, 0, 0, 20));
            xObjTA = new JTextArea(2, 5);
            yObjTA = new JTextArea(2, 5);
            zObjTA = new JTextArea(2,5);
            xObj = new JLabel("x");
            xObj.setFont(new Font("Arial", Font.PLAIN, 15));
            yObj = new JLabel("y");
            yObj.setFont(new Font("Arial", Font.PLAIN, 15));
            zObj = new JLabel("z");
            zObj.setFont(new Font("Arial", Font.PLAIN, 15));
            xObj.setBorder(new EmptyBorder(0,0, 5, 0)); //setting borders to ensure spacing between components
            yObj.setBorder(new EmptyBorder(0,0, 5, 0));
            zObj.setBorder(new EmptyBorder(0,0, 5, 0));
            plusObj = new JLabel("+");
            plusObj.setBorder(new EmptyBorder(0, 10, 0, 10));
            JLabel plus1Obj = new JLabel("+");
            plus1Obj.setBorder(new EmptyBorder(0, 10, 0, 10));

            objectivePanel.add(objLabel); //adding all the created components to the objective panel
            objectivePanel.add(xObjTA);
            objectivePanel.add(xObj);
            objectivePanel.add(plusObj);
            objectivePanel.add(yObjTA);
            objectivePanel.add(yObj);
            objectivePanel.add(plus1Obj);
            objectivePanel.add(zObjTA);
            objectivePanel.add(zObj);
            inequalityPanel.add(objectivePanel); //adding the panel to the inequality panel

        } else if (button == fourVar && (numInequality.getSelectedItem().toString()) != "--") {
            numVAR = 4;
            rowHeaders = new String[counter + 1]; //creating row header array
            for(int a = 0; a < counter; a ++){
                rowHeaders[a] = Character.toString(a+114); //adding slack variables starting from r, using ASCII
            }
            rowHeaders[counter] = "P"; //adding P to the final index of the row headers, this is always the same
            colHeaders = new String[counter + 5];//creating column headers array
            colHeaders[0] = "x1"; //adding 4 variables manually to the start of column headers
            colHeaders[1] = "x2";
            colHeaders[2] = "x3";
            colHeaders[3] = "x4";
            for(int a = 0; a < counter; a ++){
                colHeaders[a+4] = Character.toString(a+114); //adding slack variables starting from r, similar to above using ASCII
            }
            colHeaders[counter + 4] = "Value"; //adding 'Value' in the final column, which is the same for all tableaux
            for (int i = 0; i < counter; i++) {
                JPanel rowPanel = new JPanel();
                rowPanel.setLayout(new FlowLayout());
                rowPanel.setMaximumSize(new Dimension(650, 150));

                JLabel plus1 = new JLabel("+"); //this time adding 3 plus symbols as labels to the GUI
                plus1.setBorder(new EmptyBorder(0, 10, 0, 10));

                JLabel plus2 = new JLabel("+");
                plus2.setBorder(new EmptyBorder(0, 10, 0, 10));

                JLabel plus3 = new JLabel("+");
                plus3.setBorder(new EmptyBorder(0, 10, 0, 10));

                x1TA = new JTextArea(2, 5); //defining the text areas and components of the GUI
                x2TA = new JTextArea(2, 5);
                x3TA = new JTextArea(2, 5);
                x4TA = new JTextArea(2, 5);
                x1Label = new JLabel("x1");
                x1Label.setFont(new Font("Arial", Font.PLAIN, 15));
                x2Label = new JLabel("x2");
                x2Label.setFont(new Font("Arial", Font.PLAIN, 15));
                x3Label = new JLabel("x3");
                x3Label.setFont(new Font("Arial", Font.PLAIN, 15));
                x4Label = new JLabel("x4");
                x4Label.setFont(new Font("Arial", Font.PLAIN, 15));
                x1Label.setBorder(new EmptyBorder(0,0, 20, 0)); //creating border spacing around the components
                x2Label.setBorder(new EmptyBorder(0,0, 20, 0));
                x3Label.setBorder(new EmptyBorder(0,0, 20, 0));
                x4Label.setBorder(new EmptyBorder(0,0, 20, 0));
                valueLabel = new JLabel("=");
                valueLabel.setFont(new Font("Arial", Font.PLAIN, 15));
                valueLabel.setBorder(new EmptyBorder(0, 20, 10, 20));
                valueTA = new JTextArea(2, 5);

                x1TA.setBorder(new EmptyBorder(0,0, 10, 0));
                x2TA.setBorder(new EmptyBorder(0,0, 10, 0));
                x3TA.setBorder(new EmptyBorder(0,0, 10, 0));
                x4TA.setBorder(new EmptyBorder(0,0, 10, 0));

                rowPanel.add(x1TA); //adding each of the components to the row panel created at the start of the for loop
                rowPanel.add(x1Label);
                rowPanel.add(plus1);
                rowPanel.add(x2TA);
                rowPanel.add(x2Label);
                rowPanel.add(plus2);
                rowPanel.add(x3TA);
                rowPanel.add(x3Label);
                rowPanel.add(plus3);
                rowPanel.add(x4TA);
                rowPanel.add(x4Label);
                rowPanel.add(valueLabel);
                rowPanel.add(valueTA);

                inequalityPanel.add(rowPanel);
            }
            JPanel objectivePanel = new JPanel(); //creating a new panel for the objective function
            objectivePanel.setLayout(new FlowLayout());
            objectivePanel.setMaximumSize(new Dimension(650, 150));
            objLabel = new JLabel("P =");
            objLabel.setBorder(new EmptyBorder(0, 0, 0, 20));
            x1ObjTA = new JTextArea(2, 5); //defining each of the text areas that will appear
            x2ObjTA = new JTextArea(2, 5);
            x3ObjTA = new JTextArea(2,5);
            x4ObjTA = new JTextArea(2,5);
            x1Obj = new JLabel("x1");
            x1Obj.setFont(new Font("Arial", Font.PLAIN, 15)); //altering the font style and size to fit proportional to the text areas
            x2Obj = new JLabel("x2");
            x2Obj.setFont(new Font("Arial", Font.PLAIN, 15));
            x3Obj = new JLabel("x3");
            x3Obj.setFont(new Font("Arial", Font.PLAIN, 15));
            x4Obj = new JLabel("x4");
            x4Obj.setFont(new Font("Arial", Font.PLAIN, 15));
            x1Obj.setBorder(new EmptyBorder(0,0, 5, 0)); //creating spaces/borders between the elements
            x2Obj.setBorder(new EmptyBorder(0,0, 5, 0));
            x3Obj.setBorder(new EmptyBorder(0,0, 5, 0));
            x4Obj.setBorder(new EmptyBorder(0,0, 5, 0));
            plusObj = new JLabel("+");
            plusObj.setBorder(new EmptyBorder(0, 10, 0, 10));
            JLabel plus1Obj = new JLabel("+");
            JLabel plus2Obj = new JLabel("+");
            plus1Obj.setBorder(new EmptyBorder(0, 10, 0, 10));
            plus2Obj.setBorder(new EmptyBorder(0, 10, 0, 10));

            objectivePanel.add(objLabel); //adding all the created components to the objective panel
            objectivePanel.add(x1ObjTA);
            objectivePanel.add(x1Obj);
            objectivePanel.add(plusObj);
            objectivePanel.add(x2ObjTA);
            objectivePanel.add(x2Obj);
            objectivePanel.add(plus1Obj);
            objectivePanel.add(x3ObjTA);
            objectivePanel.add(x3Obj);
            objectivePanel.add(plus2Obj);
            objectivePanel.add(x4ObjTA);
            objectivePanel.add(x4Obj);

            inequalityPanel.add(objectivePanel); //adding the objective panel to the inequality panel
        }

        inequalityPanel.add(simplexPanel); // Add the panel to the frame and refresh the GUI
        add(inequalityPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    public void runSimplexAlg(int numInequality, int numVAR){
        constraints = new double[numInequality][numVAR + 1]; //2d array of constraints
        try {
            for (int i = 0; i < numInequality; i++) { //iterating through each set of inequalities
                JPanel parsePanel = (JPanel) inequalityPanel.getComponent(i); //creates a new panel object to extract components from
                int columnIndex = 0; //introducing the columnIndex counter
                for (int j = 0; j < parsePanel.getComponentCount(); j++) { //iterating through the panel
                    Component component = parsePanel.getComponent(j);
                    if (component instanceof JTextArea) { //checks if the current component is a text area
                        if (columnIndex < constraints[i].length) {
                            constraints[i][columnIndex] = Double.parseDouble(((JTextArea) parsePanel.getComponent(j)).getText()); //adds the value in the text area to the array
                            columnIndex++;
                        }
                    }
                }
            }
            JPanel objectivePanel = (JPanel) inequalityPanel.getComponent(numInequality); //creates a new objective panel
            ArrayList<Double> objective = new ArrayList<>(); //creating an arraylist to store the objective function
            for (int k = 0; k < objectivePanel.getComponentCount(); k++) {
                Component comp = objectivePanel.getComponent(k);
                if (comp instanceof JTextArea) { // Check if the current component is a JTextArea
                    String text = ((JTextArea) comp).getText();
                    objective.add(Double.parseDouble(text)); //converts the text to a double
                }
            }
            errorMessageLabel.setText("");
            for (int u = 0; u < objective.size(); u++) {
                System.out.println(objective.get(u));
            }
            obj = new double[numVAR];
            for (int l = 0; l < objective.size(); l++) {
                obj[l] = objective.get(l); //converting the arraylist into an array to be passed as a parameter
            }
            Simplex problem = new Simplex(constraints, obj); //creating a new object of type Simplex
            Iterate.solve(problem); //solving the created problem

            JPanel combinedPanel = new JPanel();
            combinedPanel.setLayout(new BorderLayout()); // Use BorderLayout for better control

            JPanel tableauWrapper = new JPanel();
            tableauWrapper.setLayout(new BorderLayout());
            tableauWrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 75, 50)); // Adjust top and left padding


            JPanel tableauPanel = Tableau.displayTableau(rowHeaders, colHeaders, numInequality + 1, numVAR + numInequality + 1);

            tableauWrapper.add(tableauPanel, BorderLayout.CENTER); // Add tableau to the wrapper
            combinedPanel.add(inequalityPanel, BorderLayout.CENTER); // Add the inequality panel
            combinedPanel.add(tableauWrapper, BorderLayout.SOUTH); // Add the tableau wrapper to the bottom
            add(combinedPanel, BorderLayout.CENTER); // Add the combined panel to the main frame

            revalidate();
            repaint();
        } catch(NumberFormatException e) {
            errorMessageLabel.setText("Please enter valid numbers in all the text areas.");
        }
    }
    public static String[] getRowHeaders(){
        return rowHeaders;
    }
    public static String[] getColHeaders(){
        return colHeaders;

    }


    public class event implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if (e.getSource() == exit) { //checks if source of event being called is 'exit' option
                System.exit(0); //closes the window

            } else if (e.getSource() == home) { //checks if source of event is 'home' option
                restoreHome(); //restores the window to its original state
            }else if (e.getSource() == runPrims){
                getContentPane().removeAll();
                graphSettings();
            } else if (e.getSource() == runKruskals){
                getContentPane().removeAll();
                graphSettings(); //displays the graph buttons on screen if the run kruskal's option is selected
            } else if (e.getSource() == introKruskals){
                kruskalsIntro();
            } else if (e.getSource() == addEdge){
                edge(); //displays the text areas for input if the add edge button is pressed
            } else if (e.getSource() == addButton){
                addToGraph(); //adds the edge to an arraylist of edges to be added if the add button is pressed
            } else if(e.getSource() == removeEdge) {
                removeFromGraph();
            }else if(e.getSource() == removeEdgeButton){
                remove();
            } else if(e.getSource() == resetGraph){
                resetGraphDisplay();
            } else if (e.getSource() == runAlgorithm){
                runKruskalsAlg(); //creates the graph, adding all the edges from the arraylist and runs the algorithm if run algorithm button is pressed
            } else if (e.getSource() == runSimplex){
                getContentPane().removeAll();
                simplexSettings();
            } else if (e.getSource() == twoVar) {
                if (numInequality.getSelectedItem().toString().equals("--")) {
                    errorMessageLabel.setText("Please select the number of inequalities first.");
                } else {
                    errorMessageLabel.setText(""); // clear the error message
                    setSimplex(twoVar);
                }
            } else if (e.getSource() == threeVar) {
                if (numInequality.getSelectedItem().toString().equals("--")) {
                    errorMessageLabel.setText("Please select the number of inequalities first.");
                } else {
                    errorMessageLabel.setText(""); // clear the error message
                    setSimplex(threeVar);
                }
            } else if (e.getSource() == fourVar) {
                if (numInequality.getSelectedItem().toString().equals("--")) {
                    errorMessageLabel.setText("Please select the number of inequalities first.");
                } else {
                    errorMessageLabel.setText(""); // clear the error message
                    setSimplex(fourVar);
                }
            } else if (e.getSource() == runSimplexButton) {
                // validate if the number of inequalities and variables are selected
                if (numInequality.getSelectedItem().toString().equals("--")) {
                    errorMessageLabel.setText("Please select the number of inequalities.");
                } else if (numVAR == 0) { //numVAR is set to 0 if no variable type is selected
                    errorMessageLabel.setText("Please select the number of variables.");
                } else {
                    int selectedInequality = Integer.parseInt(numInequality.getSelectedItem().toString());
                    runSimplexAlg(selectedInequality, numVAR);
                }
            } else{
                getContentPane().removeAll(); //clears the window and updates any changes made
                revalidate();
                repaint();
            }
        }


    }
    public static void main(String[] args){
        menuGUI gui = new menuGUI(); //creating an instance of the menuGUI class and setting the qualities of the window
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(1360, 768);
        gui.setVisible(true);
    }

}