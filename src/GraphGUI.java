import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class GraphGUI extends JFrame{
    // the following public attributes are declared with meaningful names to be used throughout the constructor and other methods
    JMenuBar menubar;
    JMenu file, simplex, kruskals, prims;
    JMenuItem exit, home, runSimplex, runPrims, runKruskals, learnSimplex, learnPrims, learnKruskals;
    JLabel prompt;
    JButton introSimplex, introPrims, introKruskals, addNode, removeNode, addWeight;
    JPanel promptPanel, buttonPanel, graphButtonPanel, introPanel;
    public GraphGUI(){
        setLayout(new BorderLayout()); //sets the window layout as a border layout

        //creating the menu bar
        menubar = new JMenuBar();
        setJMenuBar(menubar);

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

        addNode = new JButton("Add Node");
        removeNode = new JButton("Remove Node");
        addWeight = new JButton("Add Weight");

        originalWindow(); //calls the originalWindow method to add the buttons and label to the window
        add(promptPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        //adding an event action listener to each menu item within each menu bar option
        event e = new event();
        runKruskals.addActionListener(e);
        learnSimplex.addActionListener(e);
        learnPrims.addActionListener(e);
        learnKruskals.addActionListener(e);
        introSimplex.addActionListener(e);
        introKruskals.addActionListener(e);
        introPrims.addActionListener(e);
        home.addActionListener(e);
        exit.addActionListener(e);
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
        ImageIcon kruskalsIcon = new ImageIcon("C:\\Users\\averm\\Pictures\\kruskalicon.png");
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
        ImageIcon primsIcon = new ImageIcon("C:\\Users\\averm\\Pictures\\kruskalicon.png");
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
        getContentPane().removeAll();
        promptPanel.removeAll();
        // Setting up the graphButtonPanel to hold addNode, removeNode, and addWeight
        graphButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        graphButtonPanel.add(addNode);
        graphButtonPanel.add(removeNode);
        graphButtonPanel.add(addWeight);

        // Add components to frame
        add(promptPanel, BorderLayout.NORTH);
        add(graphButtonPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
    public void kruskalsIntro() {
        getContentPane().removeAll();
        promptPanel.removeAll();
    }

    public class event implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if (e.getSource() == exit) { //checks if source of event being called is 'exit' option
                System.exit(0); //closes the window

            } else if (e.getSource() == home) { //checks if source of event is 'home' option
                restoreHome(); //restores the window to its original state
            } else if (e.getSource() == runKruskals){
                getContentPane().removeAll();
                graphSettings();
            }
            else{
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