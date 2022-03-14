package edu.emory.cs.tree;

public class BinaryNode<T extends Comparable<T>> extends AbstractBinaryNode<T, BinaryNode<T>> {
    //defines only 1 generic type T for the comparable key and
    // passes itself for the generic type N to theAbstractBinaryNode class.
    // now every method using N in AbstractBinaryNode will pass BinaryNode
    public BinaryNode(T key) {
        super(key);
    }
}
