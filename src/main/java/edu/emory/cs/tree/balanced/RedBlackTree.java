package edu.emory.cs.tree.balanced;

public class RedBlackTree<T extends Comparable<T>> extends AbstractBalancedBinarySearchTree<T, RedBlackNode<T>> {
    @Override
    public RedBlackNode<T> createNode(T key) {
        return new RedBlackNode<T>(key);
    }

    @Override
    protected void balance(RedBlackNode<T> node) {
        if (isRoot(node))
            node.setToBlack(); // root should be black always.
        else if (node.getParent().isRed()) { // if not root check if parent is red
            RedBlackNode<T> uncle = node.getUncle();

            if (uncle != null && uncle.isRed())
                balanceWithRedUncle(node, uncle);
            else
                balanceWithBlackUncle(node);
        }
    }

    /**
     * color(node) = red & color(parent) = red.
     */
    private void balanceWithRedUncle(RedBlackNode<T> node, RedBlackNode<T> uncle) {
        node.getParent().setToBlack();
        uncle.setToBlack();
        RedBlackNode<T> grandParent = node.getGrandParent();
        grandParent.setToRed();
        balance(grandParent);
    }

    /**
     * color(node) = red & color(parent) = red.
     */
    private void balanceWithBlackUncle(RedBlackNode<T> node) {
        /* Rotation cases (G = grandparent, P = parent, C = child)
         * Case 1:
         *     G
         *    /
         *   P
         *  /
         * C
         *
         * Case 2:
         *   G
         *  /
         * P
         *  \
         *   C
         *
         * Case 3:
         * G
         *  \
         *   P
         *    \
         *     C
         * Case 4:
         * G
         *  \
         *   P
         *  /
         * C
         */

        RedBlackNode<T> grandParent = node.getGrandParent();

        if (grandParent != null) {
            RedBlackNode<T> parent = node.getParent();

            if (grandParent.isLeftChild(parent) && parent.isRightChild(node)) {       // Case 2
                rotateLeft(parent);
                node = parent;
            }
            else if (grandParent.isRightChild(parent) && parent.isLeftChild(node)) {       // Case 4
                rotateRight(parent);
                node = parent;
            }

            node.getParent().setToBlack();
            grandParent.setToRed();

            if (node.getParent().isLeftChild(node))        // Case 1
                rotateRight(grandParent);
            else
                rotateLeft(grandParent);            // Case 3
        }
    }
}