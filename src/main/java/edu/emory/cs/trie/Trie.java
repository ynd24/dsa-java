package edu.emory.cs.trie;

public class Trie<T>  {
    private final TrieNode<T> root;

    public Trie() {
     //   super(); // added super
        root = new TrieNode<>(null, (char)0);
    }

    public TrieNode<T> getRoot() {
        return root;
    }

    /** @return the node with the specific key if exists; otherwise, {@code null}. */
    public TrieNode<T> find(String key) {
        TrieNode<T> node = root;

        for (char c : key.toCharArray()) { // iterates through every character
            node = node.getChild(c);
            if (node == null) return null;
        }

        return node;
    }
    /** takes a string key and returns the value of the node corresponding to the key in this trie: */
    public T get(String key) {
        TrieNode<T> node = find(key);
        return (node != null && node.isEndState()) ? node.getValue() : null;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    /** @return the previously inserted value for the specific key if exists; otherwise, {@code null}. */
    public T put(String key, T value) {
        TrieNode<T> node = root;

        for (char c : key.toCharArray())
            node = node.addChild(c);

        node.setEndState(true);
        return node.setValue(value);
    }

    /** @return {@code true} if a node with the specific key if exists; otherwise, {@code false}. */
    public boolean remove(String key) {
        TrieNode<T> node = find(key);

        if (node == null || !node.isEndState()) // that means the word does not exist in the trie
        // if node is null or node's endstate is false (! false = true)
            return false;

        /** now we now node exists, check if has children */
        if (node.hasChildren()) {
            node.setEndState(false); // by turning to false you remove a word that has children
            return true;
        }
        /** if the last letter of the word does not have any children */
        TrieNode<T> parent = node.getParent();

        while (parent != null) {
            parent.removeChild(node.getKey()); // you remove the last node

            if (parent.hasChildren() || parent.isEndState()) // if parent has other children or
                // parent's end state is true, i.e, it is the end of a word
                break;
            else {
                node = parent;
                parent = parent.getParent();
            }
        }

        return true;
    }
}
