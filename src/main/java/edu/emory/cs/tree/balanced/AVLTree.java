package edu.emory.cs.tree.balanced;

public class AVLTree<T extends Comparable<T>> extends AbstractBalancedBinarySearchTree<T, AVLNode<T>> {
    // inherits AbstractBalancedBinarySearchTree and override the createNote(), rotateLeft(), and rotateRight() methods:
    @Override
    public AVLNode<T> createNode(T key) {
        return new AVLNode<>(key);
    }

    @Override
    protected void rotateLeft(AVLNode<T> node) {
        super.rotateLeft(node);
        node.resetHeights();
    }

    @Override
    protected void rotateRight(AVLNode<T> node) {
        super.rotateRight(node);
        node.resetHeights();
    }

    @Override
    protected void balance(AVLNode<T> node) {
        /* Rotation cases (N = node, C = child)
         * Case 1:
         *     N
         *    /
         *   C
         *  /
         * O
         *
         * Case 2:
         *   N
         *  /
         * C
         *  \
         *   O
         *
         * Case 3:
         * N
         *  \
         *   C
         *    \
         *     O
         * Case 4:
         * N
         *  \
         *   C
         *  /
         * O
         */

        if (node == null) return;
        int bf = node.getBalanceFactor();

        if (bf == 2) { // check at 2 because this algorithm keeps balancing tree as it gets unbalanced.
            // this is the left heavy subtree
            // either just rotate top node right or middle node left then top node right
            AVLNode<T> child = node.getLeftChild();

            if (child.getBalanceFactor() == -1)    // Case 2
                // zig zag case. right subtree of child is heavy
                rotateLeft(child);

            rotateRight(node);        // Case 1
        }
        else if (bf == -2) {
            AVLNode<T> child = node.getRightChild();

            if (child.getBalanceFactor() == 1)        // Case 4
                rotateRight(child);

            rotateLeft(node);        // Case 3
        }
        else
            balance(node.getParent());
    }
}