package MultiCenterEsauWilliamsAlgorithm;


import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.text.AttributedCharacterIterator;

public class Main extends JFrame {
    public static void main(String[] args) throws IOException {
        Graph graph = new Graph();
        graph.getVertices();

        graph.getEdges();
        graph.setWeight();
        MultiCenterEsauWilliamsAlgorithm ewa = new MultiCenterEsauWilliamsAlgorithm(graph);
        ewa.MultiCenterEsauWilliamsAlgorithm();

        Draw m = new Draw(graph, 1000, 1000);
        JFrame f = new JFrame();
        f.add(m);
        f.setSize(1000, 1000);
        f.setVisible(true);
    }
}
