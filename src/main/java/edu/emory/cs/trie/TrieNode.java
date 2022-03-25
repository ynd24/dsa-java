package edu.emory.cs.trie;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class TrieNode<T>  { // generic type T // made it extend entity
    private final Map<Character, TrieNode<T>> children; // map used to store children
    private TrieNode<T> parent;
    private boolean end_state; // true if node represents last character of a certain word
    private char key;
    private T value;

    public TrieNode(TrieNode<T> parent, char key) {
        children = new HashMap<>(); // because it gives 0(1) complexity for searching a key
        setEndState(false);
        setParent(parent);
        setKey(key);
        setValue(null);
    }

    //	============================== Getters ==============================

    public TrieNode<T> getParent() {
        return parent;
    }

    public char getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }

    public TrieNode<T> getChild(char key) {
        return children.get(key);
    }

    /** @return the map consisting of children's characters as keys and their nodes as values. */
    public Map<Character, TrieNode<T>> getChildrenMap() {
        return children;
    }

    //	============================== Setters ==============================

    public void setParent(TrieNode<T> node) {
        parent = node;
    }

    public void setKey(char key) {
        this.key = key;
    }

    public void setEndState(boolean endState) {
        end_state = endState;
    }

    /** @return the previously assigned value of this node. */
    public T setValue(T value) {
        T tmp = this.value;
        this.value = value;
        return tmp;
    }

    /**
     * @return the child with the specific key if exists; otherwise, a new child with the specific key.
     */
    public TrieNode<T> addChild(char key) {
        TrieNode<T> child = getChild(key);

        if (child == null) {
            child = new TrieNode<>(this, key);
            children.put(key, child);
        }

        return child;
    }

    public TrieNode<T> removeChild(char key) {
        return children.remove(key);
    }

//	============================== Checkers ==============================

    /** @return {@code true}} if this node represents the last character of a certain word; otherwise, {@code false}. */
    public boolean isEndState() {
        return end_state;
    }

    public boolean hasValue() {
        return value != null;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }
}