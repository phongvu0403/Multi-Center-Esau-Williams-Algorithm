package EsauWilliamsAlgorithm;


public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.getVertices();
        graph.getEdges();
        graph.setWeight();
        EsauWilliamsAlgorithm ewa = new EsauWilliamsAlgorithm(graph);
        ewa.esauWilliamsAlgorithm();
    }
}
