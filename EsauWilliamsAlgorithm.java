package EsauWilliamsAlgorithm;

import java.util.*;

public class EsauWilliamsAlgorithm {

    Graph graph;
    Vertex minVertex;
    boolean terminate;

    public EsauWilliamsAlgorithm(Graph graph){
        this.graph = graph;
        this.minVertex = null;
        this.terminate = false;
    }

    /**
     * This method is used to obtain the CMST for the given graph.
     */
    public void esauWilliamsAlgorithm(){
        this.graph.setIncreasingDistanceForEachNode();
        this.graph.setDistanceFromHub();
        while(!this.terminate){
            Edge e = getMinTradeOff();
            if(e == null){
                break;
            }
            System.out.println("Min trade off edge is "+e);
            Vertex p1 = this.findParent(e.from);
            Vertex p2 = this.findParent(e.to);
            if(!p1.equals(p2)){
                if(this.union(e)){
                    this.graph.cmst.add(e);
                    this.graph.cmstWeight += e.cost;
                    this.graph.visited.add(e.from);
                    this.graph.visited.add(e.to);
                }
            }else{
                System.out.println(e.from+" and "+e.to+ " are already connected");
            }
            System.out.println(this.graph.cmst);
            System.out.println();
            System.out.println();
        }
        if(this.graph.cmst.size() != this.graph.vertex.size() - 1){
            System.out.println("The graph is disconnected");
        }
        System.out.println("The minimum cost is "+this.graph.cmstWeight);
        System.out.println("the Tree has following edges"+graph.cmst);
    }

    /**
     * This method is used to find the index of minimum element in an array.
     *
     * @param arr array whose minimum element is to be found
     * @return return the index of the minimum element in the array
     */
    private int minVal(int[] arr){
        int ind = 1;
        int min = arr[ind];
        for(int i=2;i<arr.length;i++){
            if(min > arr[i]){
                min = arr[i];
                ind = i;
            }
        }
        return ind;
    }

    /**
     * This method is used to determine when the Esau-Williams algorithm terminates.
     */
    private void checkForTermination(int[] tradeOff){
        boolean positive = true;
        for(int i=0;i<tradeOff.length;i++){
            if(tradeOff[i] < 0){
                positive = false;
            }
        }
        if(positive){
            this.terminate = true;
        }
        if(!positive){
            for(int i: this.graph.vertex.keySet()){

            }
        }
    }

    /**
     * This method is used to calculate the tradeoffs for each vertex along with its associated smallest cost edge.
     *
     * @return  the suitable edge that needs to be added to cmst based on the tradeoff/savings value.
     */
    private Edge getMinTradeOff() {
        int[] tradeOffs = new int[this.graph.vertex.size()];
        Edge[] edges = new Edge[this.graph.vertex.size()];
        for(Vertex v : this.graph.adjList.keySet()){
            if(v.equals(this.graph.vertex.get(0))){
                continue;
            }
            System.out.println(v.pq);
            int ind = v.name;
            if(v.pq.peek() == null){
                return null;
            }
            edges[ind] = v.pq.peek();
            int costSmallEdge = edges[ind].cost;
            Vertex parent = this.findParent(v);
            int costDistToHub = parent.distanceFromHub;
            tradeOffs[ind] = costSmallEdge - costDistToHub;
            System.out.println("smallest edge for "+v+" is "+edges[ind]+" with weight "+tradeOffs[ind]);
        }
        int smallest = this.minVal(tradeOffs);
        this.minVertex = this.graph.vertex.get(smallest);
        System.out.println("smallest is "+smallest);
        this.graph.vertex.get(smallest).pq.poll();
        this.checkForTermination(tradeOffs);
        return edges[smallest];
    }

    /**
     * This method is used to remove an initial edge from the hub to node so that it can be replaced with
     * the new selected edge.
     *
     * @param v the vertex which was selected to be replaced.
     */
    private void removeFromCMST(Vertex v){
        Iterator<Edge> itr = this.graph.cmst.iterator();
        while(itr.hasNext()){
            Edge e = itr.next();
            if(e.from.equals(v) && e.to.equals(this.graph.vertex.get(0))){
                itr.remove();
                this.graph.cmstWeight -= e.cost;
            }else if(e.to.equals(v) && e.from.equals(this.graph.vertex.get(0))){
                itr.remove();
                this.graph.cmstWeight -= e.cost;
            }else{

            }
        }
    }

    /**
     * This method is used to find the parent of the subtree the vertex belongs in the graph.
     *
     * @param v the vertex whose parent is needs to be found in the subtree of v
     * @return the parent of vertex in its subtree
     */
    private Vertex findParent(Vertex v){
        while(v.parent != null){
            v = v.parent;
        }
        return v;
    }

    /**
     * This method is used to update the components of the subtree with latest subtree details.
     *
     * @param parent the vertex which has updated subtree details and is used to update its other nodes in sub tree.
     */
    private void updateSubTree(Vertex parent){
        for(Vertex v : parent.subTreeGroup){
            if(!parent.equals(v)){
                v.subTreeGroup = parent.subTreeGroup;
                v.weight = parent.weight;
            }
        }
    }

    /**
     * This method is used to perform union of 2 vertices, based on the constraints set for the network.
     *
     * @param e the edge which needs to be added to the subtree and perform union of vertices.
     * @return  boolean value as to whether the union was successful or not based on the constraints.
     */
    private boolean union(Edge e){
        Vertex u = this.findParent(e.from);
        Vertex v = this.findParent(e.to);
        System.out.println(e.from.weight+" "+e.to.weight);
        if(((e.from.name == 0 || e.to.name == 0) || ((e.from.weight < graph.getWeight())&& (e.from.weight + e.to.weight <= this.graph.getWeight()) && (e.to.weight < graph.getWeight())))){
            if(u.name < v.name){
                e.to.parent = e.from;
            }else{
                e.from.parent = e.to;
            }
            System.out.println("Adding edge "+e);
            if((e.from.name != 0 && e.to.name != 0)){
                e.from.subTreeGroup.addAll(e.to.subTreeGroup);
                e.from.weight += e.to.weight;
                updateSubTree(e.from);
            }
            updateVertexPQ(e);
            removeFromCMST(this.minVertex);
            return true;
        }else{
            System.out.println("Skipping edge "+e);
            updateVertexPQ(e);
            return false;
        }
    }

    /**
     * This method is used to remove the edge from priority queue of all vertices as it was selected/rejected and
     * available in the future.
     *
     * @param e the edge which needs to be removed from priority queue of all vertices.
     */
    private void updateVertexPQ(Edge e) {
        for(int i: this.graph.vertex.keySet()){
            Vertex v = this.graph.vertex.get(i);
            if(v.pq.contains(e)){
                v.pq.remove(e);
            }
        }
    }
}


/*

6
0 0
1 1
2 1
3 1
4 1
5 1

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
0 0
1 1
2 1
3 2
4 1
5 1
6 1


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


5
0 0
1 1
2 1
3 1
4 1

10
0 1 14
0 2 1
0 3 42
0 4 34
1 2 19
1 3 24
1 4 38
2 3 44
2 4 4
3 4 7


4
0 0
1 1
2 1
3 1

6
0 1 10
0 2 15
0 3 20
1 2 35
1 3 25
2 3 30

 */