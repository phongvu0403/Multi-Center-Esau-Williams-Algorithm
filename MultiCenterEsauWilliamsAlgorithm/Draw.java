package MultiCenterEsauWilliamsAlgorithm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Draw extends JPanel {
    Graph graph;
    int width;
    int height;

    public Draw(Graph graph, int w, int h){
        this.graph = graph;
        this.width = w;
        this.height = h;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Set<Integer> keySet = this.graph.vertex.keySet();
        for (Integer key: keySet){
            int name = this.graph.vertex.get(key).name;
            String PointName = String.valueOf(name);
            int x = this.graph.vertex.get(key).x_axis;
            int y = this.graph.vertex.get(key).y_axis;
            if (name == 5 || name == 12 || name == 41 || name ==65) g2d.setColor(Color.RED);
            else g2d.setColor(Color.BLACK);
            g2d.drawString(PointName, x, y);
        }
        for (Edge e: this.graph.cmst){
            int x1 = e.from.x_axis;
            int y1 = e.from.y_axis;
            int x2 = e.to.x_axis;
            int y2 = e.to.y_axis;
            Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);
            g2d.draw(line);
            g2d.setColor(Color.ORANGE);

        }
    }


}
