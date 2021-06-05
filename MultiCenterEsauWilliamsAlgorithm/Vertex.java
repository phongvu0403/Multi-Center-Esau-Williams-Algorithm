package MultiCenterEsauWilliamsAlgorithm;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.HashMap;

public class Vertex {
    int name;
    int weight;
    int x_axis;
    int y_axis;
    Vertex parent;
    HashSet<Vertex> subTreeGroup;
    PriorityQueue<Edge> pq;
    //int distanceFromHub;
    HashMap<Vertex, Double> distanceFromHub;

    public Vertex(int name, int weight){
        this.name = name;
        this.parent = null;
        this.subTreeGroup = new HashSet<Vertex>();
        this.subTreeGroup.add(this);
        this.pq = new PriorityQueue<Edge>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return Double.compare(o1.cost,o2.cost);
            }
        });
        this.weight = weight;
        //this.distanceFromHub = Integer.MAX_VALUE;
        this.distanceFromHub = new HashMap<Vertex, Double>();
    }

    /**
     * This method is used to override the hashcode method for the class Vertex.
     * */
    public int hashCode()
    {
        // use hash codes of the underlying objects
        return this.name;
    }

    /**
     * This method is used to override the equals method for the class Vertex.
     * */
    public boolean equals(Object o)
    {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Vertex v = (Vertex) o;

        if(v.name == this.name){
            return true;
        }
        return false;
    }

    /**
     * This method is used to print the vertex object.
     * */
    public String toString(){
        return String.valueOf(this.name);
    }
}
