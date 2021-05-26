package EsauWilliamsAlgorithm;


import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
//        graph.getVertices();
        try {
            graph.getVertices();
        } catch (IOException e) {
            e.printStackTrace();
        }
        graph.getEdges();
        graph.setWeight();
        EsauWilliamsAlgorithm ewa = new EsauWilliamsAlgorithm(graph);
        ewa.esauWilliamsAlgorithm();
    }
}
