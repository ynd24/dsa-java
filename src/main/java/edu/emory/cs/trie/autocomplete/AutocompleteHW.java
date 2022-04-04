
package edu.emory.cs.trie.autocomplete;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.Map.Entry;

import edu.emory.cs.trie.TrieNode;


public class AutocompleteHW extends Autocomplete<List<String>> {
    public AutocompleteHW(String dict_file, int max) {
        super(dict_file, max);
    }

    // Return a list of candidate words for the given prefix.
    // The most frequent candidate words should be returned first. The second most frequent next and so on
    // The rest of the list should be filled with the shortest words matching prefix
    // If there are more than one candidate with the same lengths they should be sorted alphabetically
    // Same candidate should not appear more than once
    
    class comparator implements Comparator<String> {
        public int compare(String str, String str_1) {
            if (str.length() > str_1.length()) {
                return 1;
            } else if (str.length() < str_1.length()) {
                return -1;
            } else {
                return str.compareTo(str_1);
            }
        }
    }

    private String findword(TrieNode<List<String>> node, String prefix) {
        String res = "";
        while (node != find(prefix)) {
            res += node.getKey();
            node = node.getParent();
        }
        return prefix + res;
    }

    //suggest
    public List<String> suggest(TrieNode<List<String>> node, String prefix, List<String> list) {
        Queue<TrieNode<List<String>>> queue = new ArrayDeque<>();
        //Add the root node to the queue
        queue.add(node);

        //Do a BFS starting from the root
        while(!queue.isEmpty()) {
            //Remove the first node from the queue
            TrieNode<List<String>> current = queue.poll();

            //If the current node is a leaf node, add the word to the list
            if (current.isEndState()) {
                list.add(findword(node, prefix));
            }

            //If the current node is not a leaf node, add its children to the queue
            for (Entry<Character, TrieNode<List<String>>> entry : current.getChildrenMap().entrySet()) {
                queue.add(entry.getValue());
            }
        }

        if(node.isEndState()){
            list.add(prefix);
        }

        Collections.sort(list, new comparator());

        node.getChildrenMap().forEach((c, t) -> suggest((TrieNode<List<String>>)t, prefix + c, list));

        return list;
    }
    
    @Override
    public List<String> getCandidates(String prefix) {
        //Trim out whitespace
        String prefix_trimmed = prefix.trim(); 
        List<String> list = new ArrayList<>();
        List<String> final_list = new ArrayList<>(); 
        
        if (find(prefix_trimmed)!= null) { // the case when we can find the prefix in the tries
            TrieNode<List<String>> node = find(prefix_trimmed); // find the node with trimmed prefix and to lowercase
            
            suggest(node, prefix, list);

            if (node.getValue() != null) {
                for (int i = 0; i < node.getValue().size(); i++) {
                    list.remove((node.getValue()).get(i)); // remove duplicates
                }
                final_list.addAll(node.getValue());
            }

            Collections.sort(list, new comparator());

            final_list.addAll(list);
            return (final_list.size() >= getMax()) ? final_list.subList(0, getMax() - 1) : final_list.subList(0, final_list.size() - 1) ;
        } else { 
            return list;
        }
    }

    @Override
    public void pickCandidate(String prefix, String candidate) {
        //Trim the prefix
        String prefix_trimmed = prefix.trim(); 
        List<String> picked_list = new ArrayList<>();

        //If the prefix is empty, add the candidate to the root node
        if (find(prefix_trimmed) == null) {
            put(prefix_trimmed, null);
            TrieNode<List<String>> target = find(prefix_trimmed);
            target.setEndState(false);
        }

        //If the candidate is already in the trie, set the end state to true
        if (find(candidate) == null) {
            put(candidate, null);
        } else {
            find(candidate).setEndState(true);
        }

        //If the prefix is not empty, find the node that contains the prefix
        TrieNode<List<String>> node = find(prefix_trimmed);

        if (node.getValue() == null) {
            picked_list.add(0, candidate);
            node.setValue(picked_list);
        } else {
            node.getValue().remove(candidate);
            node.getValue().add(0, candidate);
        }
    }
}