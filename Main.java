package EsauWilliams;


public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.getVertices();
        graph.getEdges();
        graph.setWeight(3);
        EsauWilliamsAlgorithm ewa = new EsauWilliamsAlgorithm(graph);
        ewa.esauWilliamsAlgorithm();
    }
}
