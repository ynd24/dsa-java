package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Edge;

import java.util.*;

/** @author Jinho D. Choi */
public class MSTAllHW implements MSTAll {

    List<SpanningTree> list = new ArrayList<>();
    List<List<String>> stringTree = new ArrayList<>();
    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {

        if (graph.getAllEdges().size() == 0) return list;

        MSTAux(0, graph, new PriorityQueue<>(), new SpanningTree(), new HashSet<>());

        System.out.println(list.size());
        return list;
    }

    public void MSTAux(int vertex, Graph graph, PriorityQueue<Edge> queue, SpanningTree tree, Set<Integer> visited) {
        add(queue, visited, graph, vertex);
        List<Edge> edges = new ArrayList<>();
        if (queue.isEmpty()) return;

        double min = queue.peek().getWeight();

        for (Edge e : queue) {
            PriorityQueue<Edge> temporary = new PriorityQueue<>(queue);
            temporary.remove(e);
            if (!visited.contains(e.getSource())) {
                SpanningTree t = new SpanningTree(tree);
                t.addEdge(e);
                if (t.size() + 1 == graph.size()) {
                    if (!stringTree.contains(stringRep(t))) {
                        list.add(t);
                        stringTree.add(stringRep(t));
                    }
                }
                MSTAux(e.getSource(), graph, temporary, t, new HashSet<>(visited));
            }
        }
    }

    private void add(PriorityQueue<Edge> queue, Set<Integer> visited, Graph graph, int target) {
        visited.add(target);
        for (Edge edge : graph.getIncomingEdges(target)) {
            if (!visited.contains(edge.getSource())) queue.add(edge);
        }
    }

    public List<String> stringRep(SpanningTree t) {
        List<String> list = new ArrayList<>();
        for (Edge e : t.getEdges()) {
            String temp = "";
            if (e.getSource() < e.getTarget()) {
                temp = e.getSource() + "-" + e.getTarget();
            } else {
                temp = e.getTarget() + "-" + e.getSource();
            }
            list.add(temp);
        }
        Collections.sort(list);
        return list;
    }
}