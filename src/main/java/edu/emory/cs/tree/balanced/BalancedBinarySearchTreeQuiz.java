package edu.emory.cs.tree.balanced;
import edu.emory.cs.tree.BinaryNode;

public class BalancedBinarySearchTreeQuiz<T extends Comparable<T>> extends AbstractBalancedBinarySearchTree<T, BinaryNode<T>> {
    @Override
    public BinaryNode<T> createNode(T key) {
        return new BinaryNode<>(key);
    }

    @Override
    protected void balance(BinaryNode<T> node) {
        BinaryNode<T> uncle = node.getUncle();
        BinaryNode<T> parent = node.getParent();
        BinaryNode<T> grandparent = node.getGrandParent();

        if (parent.isLeftChild(node) && parent.isRightChild(null)) {
            if (grandparent.isRightChild(parent) && !uncle.hasBothChildren()) {
                if (uncle.hasRightChild()) rotateLeft(uncle);
                rotateRight(parent); // can no longer use local variables
                rotateLeft(node.getParent());
                rotateRight(node.getLeftChild());
            }
        } else if (parent.isRightChild(node) && parent.isLeftChild(null)) {
            if (grandparent.isRightChild(parent) && !uncle.hasBothChildren()) {
                if (uncle.hasRightChild()) rotateLeft(uncle);
                rotateLeft(node.getGrandParent());
                rotateRight(node.getSibling());
            }
        }
    }

}

