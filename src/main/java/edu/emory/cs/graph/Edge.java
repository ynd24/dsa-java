package edu.emory.cs.graph;


/** @author Jinho D. Choi ({@code jinho.choi@emory.edu}) */
public class Edge implements Comparable<Edge> { // implements comparable interface
    // so that we can override the abstract compareTo method
    private int source;
    private int target;
    private double weight;
    // source and target vertices are represented by unique ints

    public Edge(int source, int target, double weight) {
        init(source, target, weight);
    }

    public Edge(int source, int target) {
        this(source, target, 0);
    }

    public Edge(Edge edge) { // copy constructor
        this(edge.getSource(), edge.getTarget(), edge.getWeight());
    }

    private void init(int source, int target, double weight) {
        setSource(source);
        setTarget(target);
        setWeight(weight);
    }

    public int getSource() {
        return source;
    }

    public int getTarget() {
        return target;
    }

    public double getWeight() {
        return weight;
    }

    public void setSource(int vertex) {
        source = vertex;
    }

    public void setTarget(int vertex) {
        target = vertex;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void addWeight(double weight) {
        this.weight += weight;
    }

    @Override // compares this edge to another edge by their weights
    public int compareTo(Edge edge) {
        double diff = weight - edge.weight;
        if (diff > 0) return 1; // if weight of this edge is greater
        else if (diff < 0) return -1; // if weight of edge passed is greater
        else
        {
            diff = target - edge.getTarget();
            if (diff > 0) return 1; // if weight of this edge is greater
            else if (diff < 0) return -1;
            else
            {
                diff = source - edge.getSource();
                if (diff > 0) return 1; // if weight of this edge is greater
                else if (diff < 0) return -1;
            }
        }
        return 0;
    }

    public String toString() {
        return String.format("%d <- %d : %f", getTarget(), getSource(), getWeight());
    }
}
