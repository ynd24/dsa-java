package edu.emory.cs.tree;

public abstract class AbstractBinarySearchTree<T extends Comparable<T>, N extends AbstractBinaryNode<T, N>> {
    protected N root;

    public AbstractBinarySearchTree() {
        setRoot(null);
    }

    /** @return a new node with the specific key. */
    abstract public N createNode(T key);

    /** @return {@code true} if the specific node is the root of this tree. */
    public boolean isRoot(N node) { // if node is the root
        return root == node;
    }

    /** @return the root of this tree. */
    public N getRoot() {
        return root;
    }

    /** Sets the root of this tree to the specific node. */
    public void setRoot(N node) {
        if (node != null) node.setParent(null);
        root = node;
    }

    /** @return the node with the specific key if exists; otherwise, {@code null}. */
    public N get(T key) {
        return findNode(root, key);
    }

    /** @return the node with the specific key if exists; otherwise, {@code null}. */
    protected N findNode(N node, T key) {
        if (node == null) return null;
        int diff = key.compareTo(node.getKey()); // compare key you are passing
        // and key of node

        if (diff < 0) // if negative means key passed is less than node key
            return findNode(node.getLeftChild(), key); // hence go down left tree, using recursive call
        else if (diff > 0)
            return findNode(node.getRightChild(), key);
        else
            return node; // if equal found the node
    }

    /** @return the node with the minimum key under the subtree of {@code node}. */
    protected N findMinNode(N node) { // left most child
        return node.hasLeftChild() ? findMinNode(node.getLeftChild()) : node;
        // if node has left child, recursively call function for the left child till it no longer has
        // left child, then return that node
    }

    /** @return the node with the maximum key under the subtree of {@code node}. */
    protected N findMaxNode(N node) {
        return node.hasRightChild() ? findMaxNode(node.getRightChild()) : node;
    }

//	============================== Add ==============================

    /**
     * @return the new node that is added to this tree.
     * If this tree already contains the key, nothing is added.
     */
    public N add(T key) { // add key
        N node;

        if (root == null) // no root
            setRoot(node = createNode(key)); // set the root as the node
        // create a node with the key and set it to node
        else
            node = addAux(root, key); // pass root since you have to be below, key is specified

        return node;
    }

    private N addAux(N node, T key) {
        int diff = key.compareTo(node.getKey());
        N child, newNode = null;

        if (diff < 0) { // key being passed is smalled than node's key
            if ((child = node.getLeftChild()) == null) // set child as node.leftchild, then check if left child exists
                node.setLeftChild(newNode = createNode(key)); // if left child doesnt exist, create node and set it
            // as newNode with key as this is where you add the node
            else // left child exists
                newNode = addAux(child, key); // now 'child' will act as the root
        }
        else if (diff > 0) { // key is bigger
            if ((child = node.getRightChild()) == null) // check if right child is null
                node.setRightChild(newNode = createNode(key)); // add it as the right chilf
            else // recursively call it with 'child' (right child) as root
                newNode = addAux(child, key);
        }

        return newNode;
    }

//	============================== Remove ==============================

    /** @return the removed node with the specific key if exists; otherwise, {@code null}. */
    public N remove(T key) { // trying to remove certain key
        N node = findNode(root, key); // find the node

        if (node != null) { // means youve found the node
            if (node.hasBothChildren()) removeHibbard(node);
            else removeSelf(node); // for 1 or no children
        }

        return node;
    }

    private void replaceChild(N oldNode, N newNode) { // pass parent, child
        if (isRoot(oldNode)) // if parent is root
            setRoot(newNode); // set child as root
        else
            oldNode.getParent().replaceChild(oldNode, newNode); // make node's parents new child not parent but child
        // oldNode is getParents' child, replace it with NewNode
    }

    /** @return the lowest node whose subtree has been updatede. */
    protected N removeHibbard(N node) {
        N successor = node.getRightChild(); // right child of node
        N min = findMinNode(successor); // smalled node in the right subtree. left most node of right chil
        // why? because smallest in the right sub tree but guaranteed to be bigger than node and node.leftchild
        N parent = min.getParent(); // parent of this min

        min.setLeftChild(node.getLeftChild()); // set left child of min as the current left child of node
        // works because left child of node is guaranteed to be smaller than min

        if (min != successor) {
            parent.setLeftChild(min.getRightChild()); // set left child of min's parent as min's right child
            // safe because min.rightchild is in min's parents left subtree(i.e, smaller)
            // now parent's left child is no longer min, but it is min's right child
            min.setRightChild(successor); // set min's right child as node's right child
            // safe because nodes right child > min always as min was in left subtree of sucessor
        }
        // if successor has no left children then successor = min
        // then you simply make node's parents new child as min

        // but if you entered if statement above, now min's right child is successor
        // and node has no right child.
        // hence, just make nodes parents child as min
        replaceChild(node, min);
        return parent;
    }

    /** @return the lowest node whose subtree has been updated. */
    protected N removeSelf(N node) {
        N parent = node.getParent(); // check if it has parents
        N child = null;

        if (node.hasLeftChild()) child = node.getLeftChild();
        else if (node.hasRightChild()) child = node.getRightChild();
        replaceChild(node, child); // replace node with child, if no child replace with null

        return parent;
    }

//	============================== Min/Max ==============================

    /** @return {@code true} if the specific key exists; otherwise, {@code false}. */
    public boolean contains(T key) {
        return get(key) != null;
    }

    /** @return the minimum key in this tree if exists; otherwise, {@code null}. */
    public T min() {
        return (root != null) ? findMinNode(root).getKey() : null;
    }

    /** @return the maximum key in this tree if exists; otherwise, {@code null}. */
    public T max() {
        return (root != null) ? findMaxNode(root).getKey() : null;
    }

    public String toString() {
        return (root != null) ? root.toString() : "null";
    }
}
