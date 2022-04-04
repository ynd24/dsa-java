/*
 * Copyright 2020 ---
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.emory.cs.trie.autocomplete;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.Map.Entry;

import edu.emory.cs.trie.TrieNode;


/**
 * @author --)
 */
public class AutocompleteHWExtra extends Autocomplete<List<String>> {
    public AutocompleteHWExtra(String dict_file, int max) {
        super(dict_file, max);
    }

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

    //suggest
    public List<String> suggest(TrieNode<List<String>> node, String prefix, List<String> list) {
        Queue<TrieNode<List<String>>> queue = new ArrayDeque<>();
        //Add the root node to the queue
        queue.add(node);

        String cBuffer = "";
        //Do a BFS starting from the root
        while(!queue.isEmpty()) {
            //Remove the first node from the queue
            TrieNode<List<String>> current = queue.poll();

            //If the current node is a leaf node, add the word to the list
            if (current.isEndState()) {
                cBuffer += current.getKey();
                list.add(cBuffer);
            }

            //If the current node is not a leaf node, add its children to the queue
            for (Entry<Character, TrieNode<List<String>>> entry : current.getChildrenMap().entrySet()) {
                queue.add(entry.getValue());
            }
        }

        return list;
    }

    //Show the most requently picked candidates, the second 
    //mosty picked candidates, and so on
    //if there are more than one candidates with the same frequency, sort them recencey
    @Override
    public List<String> getCandidates(String prefix) {
        // TODO: to be updated
        TrieNode<List<String>> node = getRoot();

        //If the prefix is empty, return all the words in the dictionary
        if (prefix.length() == 0) {
            List<String> list = new ArrayList<>();
            for (Entry<Character, TrieNode<List<String>>> entry : node.getChildrenMap().entrySet()) {
                list.addAll(entry.getValue().getValue());
            }
            return list;
        }

        //If the prefix is not empty, find the node that contains the prefix
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (node.getChildrenMap().containsKey(c)) {
                node = node.getChildrenMap().get(c);
            } else {
                return new ArrayList<>();
            }
        }

        //If the prefix is not in the dictionary, return an empty list
        if (node.getValue() == null) {
            return new ArrayList<>();
        }

        //If the prefix is in the dictionary, return the candidates
        List<String> list = new ArrayList<>();
        suggest(node, prefix, list);

        //Sort the candidates by frequency
        Collections.sort(list, new comparator());

        //If there are more than max candidates, return the first max candidates

        if (list.size() > getMax()) {
            return list.subList(0, getMax());
        }

        return list;

    }

    @Override
    public void pickCandidate(String prefix, String candidate) {
        // TODO: to be filled
        TrieNode<List<String>> node = getRoot();
        List<String> list = new ArrayList<>();
        //If the prefix is empty, return all the words in the dictionary
        if (prefix.length() == 0) {
            for (Entry<Character, TrieNode<List<String>>> entry : node.getChildrenMap().entrySet()) {
                list.addAll(entry.getValue().getValue());
            }
            return;
        }

        //If the prefix is not empty, find the node that contains the prefix
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (node.getChildrenMap().containsKey(c)) {
                node = node.getChildrenMap().get(c);
            } else {
                return;
            }
        }

        //If the prefix is not in the dictionary, return an empty list
        if (node.getValue() == null) {
            return;
        }

        //If the prefix is in the dictionary, return the candidates
        suggest(node, prefix, list);

        //Sort the candidates by frequency
        Collections.sort(list, new comparator());

        //If there are more than max candidates, return the first max candidates

        if (list.size() > getMax()) {
            return;
        }

        //If the candidate is in the list, add one to its frequency
        if (list.contains(candidate)) {
            int index = list.indexOf(candidate);
            list.add(1, list.get(index));
        } else {
            //If the candidate is not in the list, add it to the list
            list.add(candidate);
            //list.add(0, list.size() - 1);

            // list.get((list.size() - 1)).add(1);
            list.add(1, list.get(list.size() - 1));
        }

        //Sort the candidates by frequency
        Collections.sort(list, new comparator());

    }
}
