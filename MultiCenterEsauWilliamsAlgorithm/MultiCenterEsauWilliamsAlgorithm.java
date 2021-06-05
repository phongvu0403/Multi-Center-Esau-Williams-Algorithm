package MultiCenterEsauWilliamsAlgorithm;

import org.apache.commons.math3.analysis.function.Min;

import java.util.*;

public class MultiCenterEsauWilliamsAlgorithm {

    Graph graph;
    Vertex minVertex;
    boolean terminate;

    public MultiCenterEsauWilliamsAlgorithm(Graph graph){
        this.graph = graph;
        this.minVertex = null;
        this.terminate = false;
    }

    /**
     * This method is used to obtain the CMST for the given graph.
     */
    public void MultiCenterEsauWilliamsAlgorithm(){
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
            Set<Integer> keySet = this.graph.vertex.keySet();
            for (Integer key: keySet){
                System.out.println("Size of Subtree Node "+ key +" is: " + this.graph.vertex.get(key).subTreeGroup.size());}
        }
        if(this.graph.cmst.size() != this.graph.vertex.size() - 1){
            System.out.println("Size of CMST" + this.graph.cmst.size());
            System.out.println("Size of Vertex" + this.graph.vertex.size());
            System.out.println("The graph is disconnected");
        }
        System.out.println("The minimum cost is "+this.graph.cmstWeight);
        System.out.println("the Tree has following edges"+graph.cmst);

//        Set<Integer> keySet = this.graph.vertex.keySet();
//        for (Integer key: keySet){
//            System.out.println("Size of Subtree Node "+ key +"is:" + this.graph.vertex.get(key).subTreeGroup.size());
//        }
    }

    /**
     * This method is used to find the index of minimum element in an array.
     *
     * @param arr array whose minimum element is to be found
     * @return return the index of the minimum element in the array
     */
    private int minVal(int[] arr){
        int ind = 1;
        double min = arr[ind];
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
            if(v.equals(this.graph.vertex.get(5)) || v.equals(this.graph.vertex.get(12))
                || v.equals(this.graph.vertex.get(41)) || v.equals(this.graph.vertex.get(65))){
                continue;
            }
            System.out.println(v.pq);
            int ind = v.name;
            if(v.pq.peek() == null){
                return null;
            }
            edges[ind] = v.pq.peek();
            double costSmallEdge = edges[ind].cost;
            Vertex parent = this.findParent(v);
            double costDistToHub = this.graph.getminDistance(parent);
            tradeOffs[ind] = (int) (costSmallEdge - costDistToHub);
            System.out.println("costSmallEdge" + costSmallEdge);
            System.out.println("costDistToHub" + costDistToHub);
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
            if(e.from.equals(v) && e.to.equals(this.graph.vertex.get(5))){
                itr.remove();
                this.graph.cmstWeight -= e.cost;
            }else if(e.to.equals(v) && e.from.equals(this.graph.vertex.get(5))){
                itr.remove();
                this.graph.cmstWeight -= e.cost;
            }
            else if(e.from.equals(v) && e.to.equals(this.graph.vertex.get(12))){
                itr.remove();
                this.graph.cmstWeight -= e.cost;
            }
            else if(e.to.equals(v) && e.from.equals(this.graph.vertex.get(12))){
                itr.remove();
                this.graph.cmstWeight -= e.cost;
            }else if(e.from.equals(v) && e.to.equals(this.graph.vertex.get(41))){
                itr.remove();
                this.graph.cmstWeight -= e.cost;
            }else if(e.to.equals(v) && e.from.equals(this.graph.vertex.get(41))){
                itr.remove();
                this.graph.cmstWeight -= e.cost;
            }
            else if(e.from.equals(v) && e.to.equals(this.graph.vertex.get(65))){
                itr.remove();
                this.graph.cmstWeight -= e.cost;
            }
            else if(e.to.equals(v) && e.from.equals(this.graph.vertex.get(65))){
                itr.remove();
                this.graph.cmstWeight -= e.cost;
            }else if((e.from.equals(v) && (!e.from.equals(e.to.parent))) ||
                    ((e.to.equals(v) && (!e.to.equals(e.from .parent))))){
                itr.remove();
                this.graph.cmstWeight -= e.cost;

            }
        }
    }

    /**
     * This method is used to find the parent of the subtree the vertex belongs in the graph.
     *
     * @param v the vertex whose parent is needs to be found in the subtree of v
     * @return the parent of vertex in its subtree
     */
    public Vertex findParent(Vertex v){
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
        System.out.println(e.from.weight+" "+e.to.weight);

//        if(((e.from.name == 5 || e.to.name == 5) || (e.from.name == 12 || e.to.name == 12) ||
//                (e.from.name == 41 || e.to.name == 41) || (e.from.name == 65 || e.to.name == 65) || ((e.from.weight < graph.getWeight())
//                && (e.from.weight + e.to.weight <= this.graph.getWeight()) && (e.to.weight < graph.getWeight())))){

        if(((e.from.name == 5 || e.to.name == 5) || (e.from.name == 12 || e.to.name == 12) ||
                (e.from.name == 41 || e.to.name == 41) || (e.from.name == 65 || e.to.name == 65) ||
                ((e.from.weight < graph.getWeight()) && (e.from.weight + e.to.weight <= this.graph.getWeight()) &&
                        (e.to.weight < graph.getWeight()) && (e.from.subTreeGroup.size() < 2) && (e.to.subTreeGroup.size() < 2)))){

            if (this.graph.getminDistance(e.from) > this.graph.getminDistance(e.to)) e.from.parent = e.to;
            else e.to.parent = e.from;
            System.out.println("Adding edge "+e);
            if(e.from.name != 5 && e.to.name != 5 && e.from.name != 12 && e.to.name != 12
            && e.from.name != 41 && e.to.name != 41 && e.from.name != 65 && e.to.name != 65 ){
                e.from.subTreeGroup.addAll(e.to.subTreeGroup);
                e.from.weight += e.to.weight;
                updateSubTree(e.from);
                System.out.println("size of subtree" + e.from.subTreeGroup.size());
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

