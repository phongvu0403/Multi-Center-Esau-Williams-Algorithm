package EsauWilliamsAlgorithm;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author acer
 */
public class nNodePanel extends JPanel {

    public List<Node> lstN;

    public nNodePanel(List<Node> lstN) {
        this.lstN = lstN;
    }

    public Node getFromSTT(String STT, List<Node> lstN) {
        for (Node n : lstN) {
            if (n.getSTT().equals(STT)) {
                return n;
            }
        }
        return null;
    }

    public List<String> timNutCon(String n, List<Node> lstN) {
        List<String> lstNutCon = new ArrayList<>();
        lstNutCon.addAll(getFromSTT(n, lstN).getNutCon());
        List<String> tempRound = new ArrayList();
        tempRound.addAll(getFromSTT(n, lstN).getNutCon());
        boolean end = false;
        while (!end) {
            end = true;
            List<String> lstTemp = new ArrayList();
            for (int i = 0; i < tempRound.size(); i++) {
                if (!getFromSTT(tempRound.get(i), lstN).getNutCon().isEmpty()) {
                    lstTemp.addAll(getFromSTT(tempRound.get(i), lstN).getNutCon());
                    end = false;
                }

            }
            tempRound.clear();
            tempRound.addAll(lstTemp);
            lstNutCon.addAll(lstTemp);
        }
        return lstNutCon;
    }

    public void drawBoarder(Graphics g1, List<Node> lstN) {
        Graphics2D g2d = (Graphics2D) g1;
        int x0 = 100;
        int y0 = 900;
        g2d.drawLine(100, 900, 100, 100);
        g2d.drawLine(100, 900, 900, 900);
    }

    public void drawLines(Graphics g, List<Node> lstN) {
        Graphics2D g2d = (Graphics2D) g;
        for (Node n : this.lstN) {
            if (!n.getSTT().equals("0")) {
                int x = 100 + n.getX();
                int y = 900 - n.getY();
                int x1 = 100 + getFromSTT(n.getNutCha(), lstN).getX();
                int y1 = 900 - getFromSTT(n.getNutCha(), lstN).getY();
                List<String> lstString = new ArrayList();
                List<Node> lstNutCon = new ArrayList();
                for (int i = 0; i < timNutCon(n.getSTT(), lstN).size(); i++) {
                    lstNutCon.add(getFromSTT(timNutCon(n.getSTT(), lstN).get(i), lstN));
                }
                int w = 0;
                for (int i = 0; i < lstNutCon.size(); i++) {
                    w += lstNutCon.get(i).getW();
                }
                //System.out.println("nút con của" + n.getSTT());
                for (Node n1 : lstNutCon) {
                    // System.out.print(n1.getSTT() + " ");
                }
                w += getFromSTT(n.getSTT(), lstN).getW();
                if (w <= 2) {
                    g2d.setColor(Color.green);
                    g2d.drawLine(x, y, x1, y1);
                } else if (2 < w && w <= 6) {
                    g2d.setColor(Color.yellow);
                    g2d.drawLine(x, y, x1, y1);
                } else if (6 < w && w <= 20) {
                    g2d.setColor(Color.red);
                    g2d.drawLine(x, y, x1, y1);
                } else if (w > 20) {
                    g2d.setColor(Color.black);
                    g2d.drawLine(x, y, x1, y1);
                }
            }
        }
    }

    public void drawNode(Graphics g, Node n) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillOval(100 + n.getX() - 2, 900 - n.getY() - 2, 4, 4);
        g2d.drawString(n.getSTT(), 100 + n.getX() - 8, 900 - n.getY() - 8);
    }

    public void drawNutGoc(Graphics g, Node n) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.drawString("5", 100 + n.getX(), 900 - n.getY());
    }

    public void paint(Graphics g) {
        super.paint(g);
        drawBoarder(g, lstN);
        for (Node n : this.lstN) {
            if (n.getSTT().equals("0")) {
                drawNutGoc(g, n);
            } else {
                drawNode(g, n);
            }
        }
        drawLines(g, lstN);
    }
}
