package edu.emory.cs.tree.balanced;
import edu.emory.cs.tree.AbstractBinaryNode;
import edu.emory.cs.tree.AbstractBinarySearchTree;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public abstract class AbstractBalancedBinarySearchTree<T extends Comparable<T>, N extends AbstractBinaryNode<T, N>>
        extends AbstractBinarySearchTree<T, N> { // ABBST takes 2 generics(T, N) andextends ABST which passes the
    // two generics we just made
    /**
     * Rotates the specific node to the left.
     * @param node the node to be rotated.
     */
    // make the rotation methods in abstract class and generic type so that all subclasses can take advantage
    protected void rotateLeft(N node) {
        N child = node.getRightChild(); // child is nodes right child
        node.setRightChild(child.getLeftChild());
        // set node's right child as child's left child. safe because child's left child is in node's right
        // subtree

        if (node.hasParent())
            node.getParent().replaceChild(node, child); // now node's parent points to child
        else
            setRoot(child); // else child is now root

        child.setLeftChild(node); // make child's left child as node.
    }

    /**
     * Rotates the specific node to the right.
     * @param node the node to be rotated.
     */
    protected void rotateRight(N node) {
        N child = node.getLeftChild();
        node.setLeftChild(child.getRightChild());

        if (node.hasParent())
            node.getParent().replaceChild(node, child);
        else
            setRoot(child);

        child.setRightChild(node);
    }

//	============================== Override ==============================

    @Override
    public N add(T key) {
        N node = super.add(key);
        balance(node);
        return node;
    }

    @Override
    public N remove(T key) {
        N node = findNode(root, key);

        if (node != null) {
            N lowest = node.hasBothChildren() ? removeHibbard(node) : removeSelf(node);
            if (lowest != null && lowest != node) balance(lowest);
        }

        return node;
    }

    /**
     * Preserves the balance of the specific node and its ancestors.
     * @param node the node to be balanced.
     */
    protected abstract void balance(N node); // checks if node needs to balanced if so it balances it
}
