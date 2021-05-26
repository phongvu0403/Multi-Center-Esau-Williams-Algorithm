package EsauWilliamsAlgorithm;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.lang.Math;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

public class Graph {

    private int weight;

    HashMap<Integer, Vertex> vertex;                        // Map names to vertices
    HashSet<Edge> edges;                                    // Collection of all edges
    HashMap<Vertex, HashSet<Edge>> adjList;                 // Adjacency List of Graph
    HashSet<Edge> cmst;                                     // The CMST edges
    HashSet<Vertex> visited;                                // keep track of visited nodes
    int cmstWeight;                                         // cmst value

//    public static void main(String[] args) {
//        Graph graph = new Graph();
//        try {
//            graph.getVertices();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        graph.getEdges();
//        graph.setWeight();
//        graph.setDistanceFromHub();
//    }
    public Graph(){
        this.adjList = new HashMap<Vertex, HashSet<Edge>>();
        this.vertex = new HashMap<Integer, Vertex>();
        this.edges = new HashSet<Edge>();
        this.cmst = new HashSet<Edge>();
        this.visited = new HashSet<Vertex>();
        this.cmstWeight = 0;
    }

    /**
     * This method is used to accept user Weight constraint.
     * */
    public int getWeight() {
        return weight;
    }

    /**
     * This method is used as a getter function for Weight constraint.
     * */
    public void setWeight() {
//        System.out.println("Enter the value of W, maximum weight the subtree can have.");
//        Scanner sc = new Scanner(System.in);
//        this.weight = sc.nextInt();
        this.weight = 3;
    }

    /**
     * This method is used to accept user input vertices.
     * */
    public void getVertices() throws IOException{
//        Scanner sc = new Scanner(System.in);
//        System.out.println("enter the number of vertices");
//        int n = sc.nextInt();
//        for(int i=0;i<n;i++){
//            System.out.println("enter the vertices number");
//            int num = sc.nextInt();
//            System.out.println("enter the weight of vertices");
//            int weight = sc.nextInt();
//            Vertex inpV = new Vertex(num, weight);
//            this.vertex.put(num, inpV);
//            this.adjList.put(inpV, new HashSet<Edge>());
//        }
//        System.out.println(this.vertex);
        FileInputStream file = new FileInputStream("vertex_data_test.xls");
        HSSFWorkbook wb = new HSSFWorkbook(file);
        HSSFSheet sheet = wb.getSheetAt(0);
        FormulaEvaluator formula = wb.getCreationHelper().createFormulaEvaluator();
        for (Row row:sheet){
            if (row.getRowNum() == 0) continue;
            else{
                int num = (int)row.getCell(0).getNumericCellValue();
                int x_axis = (int)row.getCell(1).getNumericCellValue();
                int y_axis = (int)row.getCell(2).getNumericCellValue();
                int weight = (int)row.getCell(3).getNumericCellValue();
                Vertex inpV = new Vertex(num, weight);
                inpV.x_axis = x_axis;
                inpV.y_axis = y_axis;
                this.vertex.put(num, inpV);
                this.adjList.put(inpV, new HashSet<Edge>());
            }
            }
        System.out.println(this.vertex);
    }

    /**
     * This method is used to accept user input edges and edge-weights.
     * */
    public void getEdges(){
//        Scanner sc = new Scanner(System.in);
//        System.out.println("enter the number of edges");
//        int m = sc.nextInt();
//        for(int i=0;i<m;i++){
//            System.out.println("End vertex of edge");
//            int f = sc.nextInt();
//            System.out.println("Other end vertex of edge");
//            int t = sc.nextInt();
//            System.out.println("Weight of Edge");
//            int c = sc.nextInt();
//            Edge e = new Edge(this.vertex.get(f),this.vertex.get(t),c);
//            this.edges.add(e);
//            HashSet<Edge> tmpEdge = this.adjList.get(this.vertex.get(f));
//            tmpEdge.add(e);
//            this.adjList.put(this.vertex.get(f),tmpEdge);
//            tmpEdge = this.adjList.get(this.vertex.get(t));
//            tmpEdge.add(e);
//            this.adjList.put(this.vertex.get(t),tmpEdge);
//        }
        Set<Integer> keySet = this.vertex.keySet();
        for (Integer key1: keySet){
            for(Integer key2: keySet){
                if (key1 != key2){
                    double cost = 0.3 * Math.sqrt(Math.pow((this.vertex.get(key1).x_axis - this.vertex.get(key2).x_axis), 2)
                            + Math.pow((this.vertex.get(key1).y_axis - this.vertex.get(key2).y_axis), 2));
                    Edge e = new Edge(this.vertex.get(key1),this.vertex.get(key2),cost);
                    Edge e1 = new Edge(this.vertex.get(key2), this.vertex.get(key1), cost);
                    if (this.edges.contains(e1)) continue;
                    this.edges.add(e);
                    HashSet<Edge> tmpEdge = this.adjList.get(this.vertex.get(key1));
                    tmpEdge.add(e);
                    this.adjList.put(this.vertex.get(key1),tmpEdge);
                    tmpEdge = this.adjList.get(this.vertex.get(key2));
                    tmpEdge.add(e);
                    this.adjList.put(this.vertex.get(key2),tmpEdge);
                }
            }
        }
    }

    /**
     * This method is used to set the direct distance from the hub to each vertex. It also add these edges to initial
     * CMST.
     * */
    public void setDistanceFromHub(){
        Set<Integer> keySet = this.vertex.keySet();
//        Vertex Hub1 = new Vertex(0,1);
//        Vertex Hub2 = new Vertex(4, 3);
        Vertex Hub1 = this.vertex.get(0);
        Vertex Hub2 = this.vertex.get(4);

        for (Integer key: keySet){
            double DistanceFromHub1 = 0.3 * Math.sqrt(Math.pow((this.vertex.get(key).x_axis - Hub1.x_axis), 2) +
                    Math.pow((this.vertex.get(key).y_axis - Hub1.y_axis), 2));
            double DistanceFromHub2 = 0.3 * Math.sqrt(Math.pow((this.vertex.get(key).x_axis - Hub2.x_axis), 2) +
                    Math.pow((this.vertex.get(key).y_axis - Hub2.y_axis), 2));
            this.vertex.get(key).distanceFromHub.put(Hub1, DistanceFromHub1);
            this.vertex.get(key).distanceFromHub.put(Hub2, DistanceFromHub2);
//            System.out.println(this.vertex.get(key).distanceFromHub);
//            System.out.println(this.vertex.get(key).distanceFromHub.get(Hub1));
        }
        for(Edge e : this.edges){
            if((e.from.equals(Hub1)) & (e.to.distanceFromHub.get(Hub1) == getminDistance(e.to))){
                this.cmst.add(e);
                this.cmstWeight+= e.cost;
                System.out.println(e.to.distanceFromHub);
                e.cost = e.to.distanceFromHub.get(Hub1);
            }else if((e.to.equals(Hub1)) & (e.from.distanceFromHub.get(Hub1) == getminDistance(e.from))){
                e.cost = e.from.distanceFromHub.get(Hub1);
                this.cmstWeight+= e.cost;
                this.cmst.add(e);
            }else if ((e.from.equals(Hub2)) & (e.to.distanceFromHub.get(Hub2) == getminDistance(e.to))){
                this.cmst.add(e);
                this.cmstWeight+= e.cost;
                e.cost = e.to.distanceFromHub.get(Hub2);
            }else if((e.to.equals(Hub2)) & (e.from.distanceFromHub.get(Hub2) == getminDistance(e.from))){
                e.cost = e.from.distanceFromHub.get(Hub2);
                this.cmstWeight+= e.cost;
                this.cmst.add(e);
            }else {

            }
        }
    }


    /**
     * This method is used to set the edges for each vertex in the increasing order of the cost of the edges.
     * */
    public void setIncreasingDistanceForEachNode(){
        for(Vertex v : this.adjList.keySet()){
            HashSet<Edge> vset = new HashSet<Edge>(this.adjList.get(v));
            for(Edge e: vset){
                if(e.from.equals(this.vertex.get(0)) || e.to.equals(this.vertex.get(0))){
                    continue;
                }
                v.pq.offer(e);
            }
        }
    }


    /**
     * This method is used to Print the adjacency list of the graph.
     * */
    public void printGraph(){
        for(Vertex v : this.adjList.keySet()){
            System.out.println(v+" : "+this.adjList.get(v));
        }
    }

    public double getminDistance(Vertex vertex){
        double d1 = vertex.distanceFromHub.get(this.vertex.get(0));
        double d2 = vertex.distanceFromHub.get(this.vertex.get(4));
        double minDistance = d1;
        Set<Integer> keySet = new HashSet<Integer>();
        keySet.add(0);
        keySet.add(4);
        for (Integer key: keySet){
            if (minDistance > vertex.distanceFromHub.get(this.vertex.get(key))) minDistance = vertex.distanceFromHub.get(this.vertex.get(key));
        }
        return minDistance;
    }
}

/*

6
0
1
2
3
4
5

15
0 2 6
0 1 5
0 4 12
0 5 15
0 3 9
2 1 4
2 5 12
2 4 5
1 5 10
1 4 8
1 3 3
5 4 7
5 3 6
4 3 6
2 3 8


7
0
1
2
3
4
5
6


21
0 1 5
0 2 6
0 3 9
0 4 10
0 5 11
0 6 15
1 2 9
1 3 6
1 4 6
1 5 8
1 6 17
2 3 7
2 4 9
2 5 8
2 6 12
3 4 10
3 5 5
3 6 11
4 5 14
4 6 9
5 6 8

* */
