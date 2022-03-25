package edu.emory.cs.trie;
import java.util.*;


public class TrieQuiz extends Trie<Integer> {
    /**
     * PRE: this trie contains all country names as keys and their unique IDs as values
     * (e.g., this.get("United States") -> 0, this.get("South Korea") -> 1).
     *
     * @param input the input string in plain text
     *              (e.g., "I was born in South Korea and raised in the United States").
     * @return the list of entities (e.g., [Entity(14, 25, 1), Entity(44, 57, 0)]).
     */
    List<Entity> getEntities(String input) {
        // TODO: to be updated
        TrieNode<Integer> node = getRoot();
        char[] arr = new char[input.length()];
        for (int i = 0; i < input.length(); i++)
            arr[i] = input.charAt(i); // copy input into an array

        node = getRoot();
        int begin = 0;
        int end = 0;
        int ID = 0;
        List<Entity> ent_list = new ArrayList<Entity>();

        for (int i = 0; i < input.length(); i++) {
            for (int j = i; j < input.length(); j++) {
                TrieNode<Integer> n = node.getChild(arr[j]);
                if (n == null) break;
                if (n.isEndState()) {
                    ID = n.getValue();
                    begin = i;
                    end = j + 1;
                    Entity new_E = new Entity(begin, end, ID);
                    ent_list.add(new_E);
                }
                node = (!n.isEndState()) ? n : getRoot();
            }
        }
        // return List.of(new Entity(begin, end, ID));
        // Entity new_E = new Entity(3, 5, 2);
        // ent_list.add(new_E);
        return ent_list;
    }

}