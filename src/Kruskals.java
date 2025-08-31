import java.lang.reflect.Array;
import java.util.*;
class Edge implements Comparable<Edge>{ //creating a class which defines the attributes and method of each edge/arc
    int source, dest, weight;
    public Edge(int nodeSource, int nodeDest, int nodeWeight){ //constructor for edge class
        source = nodeSource - 65;
        dest = nodeDest - 65;
        weight = nodeWeight;
    }
    public int compareTo(Edge otherEdge){ //method to compare the weight of two different edges
        return weight - otherEdge.weight;
    }
}
public class Kruskals{
    public ArrayList<Edge> edges;
    private int numVertices;

    public Kruskals(int vertices){ //constructor for Kruskal's algorithm class
        numVertices = vertices;
        edges = new ArrayList<>();
    }
    public void addEdge(int source, int dest, int weight){ //method to add edge from graph to arraylist
        edges.add(new Edge(source, dest, weight));
    }
    private int findComponents(int vertex, ArrayList<Integer> components){ //finds the component of the graph that a vertex belongs to
        return components.get(vertex);
    }
    public ArrayList<Edge> kruskalMST(){
        Collections.sort(edges); //sorts the arraylist of edges into ascending order
        ArrayList<Integer> components = new ArrayList<>(Collections.nCopies(numVertices, 0));
        for(int i = 0; i < numVertices; i++){
            components.set(i, i);
        }
        ArrayList<Edge> result = new ArrayList<>(); //creates a new arraylist to store the edges of the MST in
        for (Edge edge : edges) { //iterating through each edge in the arraylist of all edges from original graph

            int s = edge.source;
            int d = edge.dest;

            int compS = findComponents(s, components);
            int compD = findComponents(d, components);
            if (compS != compD) { //comparing the edges and adding the edge to the result arraylist if the addition of the edge doesn't form a cycle
                result.add(edge);
                for (int i = 0; i < numVertices; i++) {
                    if (components.get(i) == compD) {
                        components.set(i, compS);
                    }
                }
            }
        }
        System.out.println("Edges included in the minimum spanning tree: "); //outputs the final edges in the MST
        int total = 0;
        for(Edge edge: result){
            total += edge.weight;
            System.out.println("Edge: " + (char)(edge.source + 65) + "" + (char)(edge.dest + 65) + ", Weight: " + edge.weight);
        }
        System.out.println("Total Weight of MST is: " + total);
        return result;
    }
    public static void main(String[] args){
    }
}