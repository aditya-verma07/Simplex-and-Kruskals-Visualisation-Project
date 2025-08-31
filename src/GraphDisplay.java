
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.swing.*;

class DisplayPanel extends JPanel {
    public ArrayList<String> edges; // list of edges in the graph
    public ArrayList<Character> nodeLabels = new ArrayList<>(); // list of labels in the graph
    public ArrayList<Point> nodePositions = new ArrayList<>(); // list of locations on the panel for the vertices
    public ArrayList<Integer> edgeWeights = new ArrayList<>(); // list of weights for the edges
    private int diameter = 30; // diameter of the vertices
    private Point dragStart = null; // starting point for dragging
    private int dragIndex = -1; // index of the node being dragged
    private String highlightedEdge = null;
    private Color edgeColour;
    public ArrayList<Color> edgeColourList;
    private ArrayList<ArrayList<Color>> colourStates;

    public DisplayPanel() { // constructor
        edges = new ArrayList<>();
        edgeColourList = new ArrayList<>();
        setMouseListeners();
    }
    public void highlightEdge(String edge, Color colour){ //method to colour a specific edge in the graph
        int index = edges.indexOf(edge);
        if(index != -1){
            edgeColourList.set(index, colour);
        }
        //need to create method that does all the colours automatically at the start
        //highlightedEdge = edge; //the edge to be highlighted passed as a parameter
        //edgeColour = colour; //the colour that this edge should be based on whether it is in the minimum spanning tree or not
        revalidate(); //refreshing the GUI
        repaint();
    }

    public void addEdge(String edge, int weight) {
        edges.add(edge); // adding the edge to the final list
        edgeWeights.add(weight); // adding the weight to the list
        edgeColourList.add(Color.BLACK); //adding default colour black to list
        calculatePositions(); // calling the method to calculate the position of the vertex
        revalidate(); // refreshing the GUI
        repaint();
    }

    public void calculatePositions() {
        for (String s : edges) {
            char startNode = s.charAt(0); // Separating the edge into start node and end node
            char destNode = s.charAt(1);

            if (!nodeLabels.contains(startNode)) { // Check if the start node is already in the graph
                nodeLabels.add(startNode); // If not already in graph then add to the labels
                nodePositions.add(new Point(0, 0)); // Create a temporary position in the list of positions
            }
            if (!nodeLabels.contains(destNode)) { // Check if the destination node is already in the graph
                nodeLabels.add(destNode); // If not already in graph then add to the labels
                nodePositions.add(new Point(0, 0)); // Create a temporary position in the list of positions
            }
        }
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int centerX = panelWidth / 2; // Define the x-coordinate of the center of the panel
        int centerY = panelHeight / 2; // Define the y-coordinate of the center of the panel
        int radius = Math.min(panelWidth, panelHeight) / 3; // Make the radius of the graph fit inside the panel

        for (int i = 0; i < nodeLabels.size(); i++) {
            double angle = 2 * Math.PI * i / nodeLabels.size(); // Split the nodes into equal angles in a 360-degree circle
            int x = centerX + (int) (radius * Math.cos(angle)) - diameter / 2; // X-coordinate on the circle
            int y = centerY + (int) (radius * Math.sin(angle)) - diameter / 2; // Y-coordinate on the circle
            nodePositions.set(i, new Point(x, y)); // Set the position of each node going around in a circle
        }
    }

    private void setMouseListeners() {
        // add mouse listener to handle mouse press events
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point clickPoint = e.getPoint(); // get the position where the user clicked
                for (int i = 0; i < nodePositions.size(); i++) {
                    Point nodePosition = nodePositions.get(i);
                    int centerX = nodePosition.x + diameter / 2; // find the center of the node
                    int centerY = nodePosition.y + diameter / 2;
                    double distance = clickPoint.distance(centerX, centerY); // measure the distance from the click to the center of the node
                    if (distance <= diameter / 2) { // check if the click is within the node
                        dragStart = clickPoint; // set the start point for dragging
                        dragIndex = i; // store the index of the node being dragged
                        break; // exit the loop once a node is found
                    }
                }
            }

            // reset drag state when mouse is released
            @Override
            public void mouseReleased(MouseEvent e) {
                dragStart = null; // clear the drag start point
                dragIndex = -1; // reset the drag index
            }
        });

        // add mouse motion listener to handle dragging of the node
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragIndex != -1 && dragStart != null) { // check if a node is being dragged
                    Point currentPoint = e.getPoint(); // get the current mouse position
                    Point nodePosition = nodePositions.get(dragIndex); // get the position of the node being dragged
                    int horizontalMovement = currentPoint.x - dragStart.x; // calculate the horizontal movement
                    int verticalMovement = currentPoint.y - dragStart.y; // calculate the vertical movement

                    int newX = nodePosition.x + horizontalMovement; // calculate new x position for the node
                    int newY = nodePosition.y + verticalMovement; // calculate new y position for the node

                    int panelWidth = getWidth(); // get the width of the panel
                    int panelHeight = getHeight(); // get the height of the panel

                    int minX = 0; // minimum x position (left side of panel)
                    int minY = 0; // minimum y position (top side of panel)
                    int maxX = panelWidth - diameter; // maximum x position (right side of panel)
                    int maxY = panelHeight - diameter; // maximum y position (bottom side of panel)

                    // keep the new position so the node stays within the panel bounds
                    newX = Math.max(minX, Math.min(newX, maxX));
                    newY = Math.max(minY, Math.min(newY, maxY));

                    nodePosition.setLocation(newX, newY); // update the node's position
                    dragStart = currentPoint; // update the drag start point for the next movement
                    repaint(); // refresh the GUI to reflect the new node position
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g; // create a new 2D graphics object
        g2d.setColor(Color.BLACK); // set the color for drawing to black
        int tab = menuGUI.getSelectedTab();
        for (int i = 0; i < edges.size(); i++) {
            String edge = edges.get(i);
            char sourceNode = edge.charAt(0); //get the source node from the edge string
            char destinationNode = edge.charAt(1); //get the destination node from the edge string

            int sourceIndex = nodeLabels.indexOf(sourceNode); //find the label for the source node
            int destIndex = nodeLabels.indexOf(destinationNode); //find the label for the destination node

            if (sourceIndex != -1 && destIndex != -1) { //checking that there is a position for the source and destination node in the list of positions
                Point p1 = nodePositions.get(sourceIndex); //getting the position of the start node
                Point p2 = nodePositions.get(destIndex); //getting the position of the end node

                int x1 = p1.x + diameter / 2; //getting the x-coordinate of the center of the source node
                int y1 = p1.y + diameter / 2; //getting the y-coordinate of the center of the source node
                int x2 = p2.x + diameter / 2; //getting the x-coordinate of the center of the destination node
                int y2 = p2.y + diameter / 2; //getting the y-coordinate of the center of the destination node

                g2d.setColor(edgeColourList.get(i));
                g2d.setStroke(new BasicStroke(3.0f)); //making the graphics object have a thicker stroke to make the edge line thicker
                g2d.drawLine(x1, y1, x2, y2); //drawing the line between the centers of the points

                int weight = edgeWeights.get(i); //getting the weight of the arc from the list of edge weights
                int weightX = (x1 + x2) / 2; //setting the x-coordinate of the weight to be added in the middle, between the two nodes
                int weightY = (y1 + y2) / 2; //setting the y-coordinate of the weight to be added in the middle, between the two nodes
                g2d.setFont(new Font("Arial", Font.BOLD, 17)); //making the graphics object have a bold and slightly larger font so the weight is more prominent
                g2d.drawString(String.valueOf(weight), weightX, weightY - 7); //drawing the weight onto the panel
            }
        }

        for (int i = 0; i < nodeLabels.size(); i++) {
            Point position = nodePositions.get(i); //getting the position of each node
            char label = nodeLabels.get(i); //getting the label for each node

            g2d.setColor(Color.BLACK); //drawing the node
            g2d.fillOval(position.x, position.y, diameter, diameter); //drawing the node in the retrieved position
            g2d.setColor(Color.WHITE); //changing the colour to white for the text
            g2d.drawString(String.valueOf(label), position.x + diameter / 3, position.y + 2 * diameter / 3); //adding the text in the node

        }
    }
    public ArrayList<String> getEdges(){
        return edges;
    }
    public ArrayList<Integer> getEdgeWeights() {
        return edgeWeights;
    }

    public ArrayList<Color> getEdgeColourList() {
        return edgeColourList;
    }

    public ArrayList<Character> getNodeLabels() {
        return nodeLabels;
    }
    public ArrayList<Point> getNodePositions(){
        return nodePositions;
    }

}
