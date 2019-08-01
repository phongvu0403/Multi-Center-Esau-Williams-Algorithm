package EsauWilliamsAlgorithm;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Graph {

    private int weight;

    HashMap<Integer, Vertex> vertex;                        // Map names to vertices
    HashSet<Edge> edges;                                    // Collection of all edges
    HashMap<Vertex, HashSet<Edge>> adjList;                 // Adjacency List of Graph
    HashSet<Edge> cmst;                                     // The CMST edges
    HashSet<Vertex> visited;                                // keep track of visited nodes
    int cmstWeight;                                         // cmst value

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
        System.out.println("Enter the value of W, maximum weight the subtree can have.");
        Scanner sc = new Scanner(System.in);
        this.weight = sc.nextInt();
    }

    /**
     * This method is used to accept user input vertices.
     * */
    public void getVertices(){
        Scanner sc = new Scanner(System.in);
        System.out.println("enter the number of vertices");
        int n = sc.nextInt();
        for(int i=0;i<n;i++){
            int num = sc.nextInt();
            int weight = sc.nextInt();
            Vertex inpV = new Vertex(num, weight);
            this.vertex.put(num, inpV);
            this.adjList.put(inpV, new HashSet<Edge>());
        }
        System.out.println(this.vertex);
    }

    /**
     * This method is used to accept user input edges and edge-weights.
     * */
    public void getEdges(){
        Scanner sc = new Scanner(System.in);
        System.out.println("enter the number of vertices");
        int m = sc.nextInt();
        for(int i=0;i<m;i++){
            int f = sc.nextInt();
            int t = sc.nextInt();
            int c = sc.nextInt();
            Edge e = new Edge(this.vertex.get(f),this.vertex.get(t),c);
            this.edges.add(e);
            HashSet<Edge> tmpEdge = this.adjList.get(this.vertex.get(f));
            tmpEdge.add(e);
            this.adjList.put(this.vertex.get(f),tmpEdge);
            tmpEdge = this.adjList.get(this.vertex.get(t));
            tmpEdge.add(e);
            this.adjList.put(this.vertex.get(t),tmpEdge);
        }
    }

    /**
     * This method is used to set the direct distance from the hub to each vertex. It also add these edges to initial
     * CMST.
     * */
    public void setDistanceFromHub(){
        for(Edge e : this.edges){
            if(e.from.equals(this.vertex.get(0))){
                this.cmst.add(e);
                this.cmstWeight+= e.cost;
                e.to.distanceFromHub = e.cost;
            }else if(e.to.equals(this.vertex.get(0))){
                e.from.distanceFromHub = e.cost;
                this.cmstWeight+= e.cost;
                this.cmst.add(e);
            }else{

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
