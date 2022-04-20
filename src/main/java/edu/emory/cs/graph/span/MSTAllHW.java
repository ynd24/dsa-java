package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Edge;

import java.util.*;

/** @author Jinho D. Choi */
public class MSTAllHW implements MSTAll {
    @Override
    //implement finding all minimum spanning trees using Prim's algorithm
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
        //output list of minimum spanning trees
        //output should not include redundant trees

        //create a list of minimum spanning trees
        List<SpanningTree> minimumSpanningTrees = new ArrayList<>();

        //create a list of edges
        List<Edge> edges = new ArrayList<>();

        //create a list of vertices
        List<Integer> vertices = new ArrayList<>();

        //create a list of unvisited vertices
          List<Integer> unvisited = new ArrayList<>();

        //Prims algorithm

        PriorityQueue<Edge> queue = new PriorityQueue<>(); // Priority queue so that edges picked on least weight
        SpanningTree tree = new SpanningTree();
        Set<Integer> visited = new HashSet<>();
        Edge edge;

        // add all connecting vertices from start vertex to the queue
        add(queue, visited, graph, 0);

        while (!queue.isEmpty()) {
            edge = queue.poll();

            if (!visited.contains(edge.getSource())) {
                tree.addEdge(edge);
                // if a spanning tree is found, break.
                if (tree.size() + 1 == graph.size()) break;
                // add all connecting vertices from current vertex to the queue
                add(queue, visited, graph, edge.getSource());
            }
        }

        //create a list of minimum spanning trees
        for (Edge edge1 : edges) {
            //create a new minimum spanning tree
            SpanningTree spanningTree = new SpanningTree();

            //add the edge to the spanning tree
            spanningTree.addEdge(edge1);

            //add the spanning tree to the list of minimum spanning trees
            minimumSpanningTrees.add(spanningTree);
        }

        //remove redundant spanning trees
        for (int i = 0; i < minimumSpanningTrees.size(); i++) {
            for (int j = i + 1; j < minimumSpanningTrees.size(); j++) {
                if (minimumSpanningTrees.get(i).getTotalWeight() > minimumSpanningTrees.get(j).getTotalWeight()) {
                    minimumSpanningTrees.remove(i);
                    i--;
                    break;
                }
            }
        }

        //return the list of minimum spanning trees
        return minimumSpanningTrees;

    }

    private void add(PriorityQueue<Edge> queue, Set<Integer> visited, Graph graph, int target) {
        visited.add(target);
        for (Edge edge : graph.getIncomingEdges(target)) {
            if (!visited.contains(edge.getSource()))
                queue.add(edge);
        }
    }

}
