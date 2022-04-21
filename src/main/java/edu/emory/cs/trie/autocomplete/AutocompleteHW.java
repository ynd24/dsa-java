package edu.emory.cs.trie.autocomplete;

import edu.emory.cs.trie.TrieNode;

import java.util.*;

public class AutocompleteHW extends Autocomplete<List<String>> {
    public AutocompleteHW(String dict_file, int max) {
        super(dict_file, max);
    }

    @Override
    public List<String> getCandidates(String prefix) {

        prefix = prefix.replaceAll("\\s", "");

        if (prefix == "") {
            List<String> result = new LinkedList<String>();
            TrieNode node = this.getRoot();
            finder(node, result,  "");
            result.sort(Comparator.comparing(String::length));
            List<String> result2 = result.subList(0, this.getMax());

            return result2;
        }

        List<String> result = new LinkedList<String>();

        TrieNode node = this.getRoot();

        for (char ch: prefix.toCharArray()) {
            node = node.getChild(ch);

            if (node == null) {
                return new LinkedList<String>();
            }

        }

        finder(node, result,  prefix.substring(0, prefix.length()-1));

        Collections.sort(result);
        result.sort(Comparator.comparing(String::length));


        List<String> pick = get(prefix);

        if (this.get(prefix) != null) {

            for (int i = 0; i < pick.size(); i++) {

                if (result.contains(pick.get(i))) {

                    String on = pick.get(i);
                    result.remove(on);
                    result.add(0, on);

                } else {
                    result.add(0, pick.get(i));
                }

            }

        }

        if (node.getValue() != null) {
            result.remove(prefix);
        }

        List<String> result2;

        if (result.size() > this.getMax()) {
            result2 = result.subList(0, this.getMax());
        } else {
            result2 = result;
        }


        return result2;
    }

    void finder(TrieNode node, List<String> result, String prefix) {
        if (node == null ) {
            return;
        } if (node.isEndState()) {
            String prefix2 = prefix + node.getKey();
            prefix2 = prefix2.replaceAll("\0", "");
            result.add(prefix2);
        }

        Map<Character,TrieNode> childMap = node.getChildrenMap();
        for (Character c: childMap.keySet()) {
            finder(childMap.get(c), result, prefix + node.getKey());
        }
    }

    @Override
    public void pickCandidate(String prefix, String candidate) {

        candidate = candidate.replaceAll("\\s", "");

        TrieNode node = this.getRoot();

        for (char ch: prefix.toCharArray()) {

            node = node.getChild(ch);

            if (node == null) {
                this.put(prefix, null);
                //return;
                break;
            }

        }

        if (this.contains(candidate) == false) {
            this.put(candidate, null);
        }

        List<String> picks;

        if (this.get(prefix) == null) {

            picks = new LinkedList<>();
            picks.add(candidate);

        } else {
            picks = this.get(prefix);
            picks.add(candidate);
        }

        this.put(prefix, picks);


    }
}