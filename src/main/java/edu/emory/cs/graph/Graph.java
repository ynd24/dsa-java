package edu.emory.cs.graph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Graph {
    /**
     * A list of edge lists where each dimension of the outer list indicates a target vertex and
     * the inner list corresponds to the list of incoming edges to that target vertex.
     */
    private final List<List<Edge>> incoming_edges;

    public Graph(int size) { // initialize graph with no. of vertices(size)
        incoming_edges = Stream.generate(ArrayList<Edge>::new).limit(size).collect(Collectors.toList());
    } // creates an empty edge list for each target vertex.

    public Graph(Graph g) { // copy constructor
        incoming_edges = g.incoming_edges.stream().map(ArrayList::new).collect(Collectors.toList());
    } // copies the list of edge lists from g to this graph

    public boolean hasNoEdge() {
        return IntStream.range(0, size()).allMatch(i -> getIncomingEdges(i).isEmpty());
    } // returns true if all incoming edge lists are empty using the allMatch() method.

    public int size() {
        return incoming_edges.size();
    }

    public List<Edge> getIncomingEdges(int target) {
        return incoming_edges.get(target);
    }

    public List<Edge> getAllEdges() {
        return incoming_edges.stream().flatMap(List::stream).collect(Collectors.toList());
    }

    public Deque<Integer> getVerticesWithNoIncomingEdges() {
        return IntStream.range(0, size()).filter(i -> getIncomingEdges(i).isEmpty()).boxed().collect(Collectors.toCollection(ArrayDeque::new));
    }

    /**
     * @return a list of edge deque where each dimension in the outer list represents the deque of outgoing edges for
     * the corresponding source vertex.
     */
    public List<Deque<Edge>> getOutgoingEdges() {
        List<Deque<Edge>> outgoing_edges = Stream.generate(ArrayDeque<Edge>::new).limit(size()).collect(Collectors.toList());

        for (int target = 0; target < size(); target++) {
            for (Edge incoming_edge : getIncomingEdges(target))
                outgoing_edges.get(incoming_edge.getSource()).add(incoming_edge);
        }

        return outgoing_edges;
    }

    public Edge setDirectedEdge(int source, int target, double weight) {
        List<Edge> edges = getIncomingEdges(target);
        Edge edge = new Edge(source, target, weight);
        edges.add(edge);
        return edge;
    }

    public void setUndirectedEdge(int source, int target, double weight) {
        setDirectedEdge(source, target, weight);
        setDirectedEdge(target, source, weight);
    }

    public boolean containsCycle() {
        Deque<Integer> notVisited = IntStream.range(0, size()).boxed().collect(Collectors.toCollection(ArrayDeque::new));
        // Integer represents each vertex, initially all vertices are not visited
        while (!notVisited.isEmpty()) { // makes sure it visits every vertex
            if (containsCycleAux(notVisited.poll(), notVisited, new HashSet<>())) // HashSet contains all vertices that
                // have been visited in that path
                return true;
        }

        return false;
    }

    private boolean containsCycleAux(int target, Deque<Integer> notVisited, Set<Integer> visited) {
        notVisited.remove(target); // because now visited
        visited.add(target); // add it to visited

        for (Edge edge : getIncomingEdges(target)) { // for each incoming edge of target vertex
            if (visited.contains(edge.getSource())) // check if source of incoming edge has been visited before
                return true;

            if (containsCycleAux(edge.getSource(), notVisited, new HashSet<>(visited))) // must make new hashset
                return true;
        }

        return false;
    }

    public List<Integer> topological_sort(boolean depth_first) { // by boolean,
        // can choose to do depth or breadth first search
        Deque<Integer> global = getVerticesWithNoIncomingEdges(); // all vertices with no incoming edges
        List<Deque<Edge>> outgoingEdgesAll = getOutgoingEdges();
        List<Integer> order = new ArrayList<>(); // what we return

        while (!global.isEmpty()) {
            Deque<Integer> local = new ArrayDeque<>(); // creat a local deque, to store the target vertices of current
            //vertex

            // add vertex to the path
            int vertex = global.poll(); // the current vertex with no incoming edges
            order.add(vertex); // add to order
            Deque<Edge> outgoingEdges = outgoingEdgesAll.get(vertex); // get outgoing edges of current

            while (!outgoingEdges.isEmpty()) { // while it has outgoing edges
                Edge edge = outgoingEdges.poll(); // pick one outgoing edge, iterate thru all

                // remove one outgoing edge at a time
                List<Edge> incomingEdges = getIncomingEdges(edge.getTarget());
                incomingEdges.remove(edge);

                // if the target vertex has no incoming edges,
                // add it to the local queue awaited to be added to the global deque
                if (incomingEdges.isEmpty())
                    local.add(edge.getTarget());
            }

            //Transfer all vertices in local to global
            while (!local.isEmpty()) {
                if (depth_first) global.addFirst(local.removeLast());
                else global.addLast(local.removeFirst());
            }
        }

        if (!hasNoEdge()) throw new IllegalArgumentException("Cyclic graph.");
        return order;
    }


    public String toString() {
        StringBuilder build = new StringBuilder();

        for (int i = 0; i < incoming_edges.size(); i++) {
            build.append(i);
            build.append(" <- ");
            build.append(incoming_edges.get(i).toString());
            build.append("\n");
        }

        return build.toString();
    }
}

