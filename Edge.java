package EsauWilliams;

public class Edge {
    Vertex from;        // from vertex in the directed edge
    Vertex to;          // to vertex in the directed edge
    int cost;       // cost of the edge

    public Edge(Vertex from, Vertex to, int cost){
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

    /**
     * This method is used to override the equals method for the class Edge.
     * */
    public boolean equals(Object o)
    {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Edge pair = (Edge) o;

        if(pair.from == this.from && pair.to == this.to){
            return true;
        }else if(pair.from == this.to && pair.to == this.from){
            return true;
        }
        return false;
    }

    /**
     * This method is used to override the hashcode method for the class Edge.
     * */
    public int hashCode()
    {
        // use hash codes of the underlying objects
        return 31 * this.from.hashCode() + this.to.hashCode();
    }

    /**
     * This method takes a vertex of the edge and returns the vertex at the other end in
     * that edge.
     *
     * @param u     The vertex whose other end in the edge is required.
     * @return      The other end of the vertex specified in the edge.
     * */
    public Vertex otherEnd(Vertex u){
        return (this.from.equals(u))?this.to:this.from;
    }

    /**
     * This method is used to print the edge object in the pattern as follows
     * 'from' - flow/capacity -> 'to' for reference.
     * */
    public String toString(){
        return this.from+" -"+this.cost+"-> "+this.to;
    }
}
