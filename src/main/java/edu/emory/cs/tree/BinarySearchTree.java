package edu.emory.cs.tree;

public class BinarySearchTree<T extends Comparable<T>> extends AbstractBinarySearchTree<T, BinaryNode<T>> {
    /**
     * @param key the key of this node.
     * @return a binary node with the specific key.
     */
    @Override
    public BinaryNode<T> createNode(T key) {
        return new BinaryNode<>(key);
    }
}

