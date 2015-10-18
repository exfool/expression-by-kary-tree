package com.company;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;

/**
 * K-ary Tree - tree base on array
 * as link to the node using Integer values
 *
 * @param <E> Type of stored elements
 */
public class KaryTree<E> {
    private Object[] array; // K-ary Tree base on array
    private int level; // level(high) of tree
    private int k; // count of childes

    /**
     * Constructor for K-ary Tree
     *
     * @param degree Count of childes
     */
    public KaryTree(int degree) {
        this.k = degree;
        this.level = 0;
        array = new Object[1];

    }

    /**
     * Get key of root
     *
     * @return root's key
     */
    public int getRoot() {
        return 0;
    }

    /**
     * Compute count elements of for any level
     *
     * @param level for computing
     * @return count elements for level
     */
    private int getCapacity(int level) {
        return (int) (Math.pow(k, level + 1) - 1) / (k - 1);
    }

    /**
     * Count elements for current level
     *
     * @return count of elements
     */
    public int getLength() {
        return getCapacity(this.level);
    }

    /**
     * Expand capacity to the new level
     *
     * @param newLevel to expand
     */
    private void expandCapacity(int newLevel) {
        int newLength = getCapacity(newLevel);
        int oldLength = getCapacity(this.level);

        if (newLength > oldLength) {
            this.level++;
            array = Arrays.copyOf(array, newLength);
        } else {
            throw new java.lang.RuntimeException("Expand capacity, new length less than old length.");
        }
    }

    /**
     * Set value to element by key
     *
     * @param key   of node
     * @param value value for node
     */
    public void setValue(int key, E value) {
        if (key >= getCapacity(this.level) && key < getCapacity(this.level + 1)) {
            expandCapacity(this.level + 1);
            array[key] = value;
        } else if (key < getCapacity(this.level) && key >= 0) {
            array[key] = value;
        } else throw new java.lang.RuntimeException("setValue. Out of bound K-ary tree");
    }

    /**
     * Get value of node by key
     *
     * @param key of node
     * @return value
     */
    public E getValue(int key) {
        if (key < getCapacity(this.level) && key >= 0) {
            return (E) array[key];
        } else throw new java.lang.RuntimeException("getValue. Out of bound K-ary tree.");
    }

    /**
     * Get child key
     *
     * @param parent's      key
     * @param childNumber's ordinal number
     * @return key of this child
     */
    public int getChild(int parent, int childNumber) {
        if (parent < getCapacity(this.level) && parent >= 0 && childNumber >= 0 && childNumber < k) {
            return k * parent + childNumber + 1;
        } else throw new java.lang.RuntimeException("getChild. Out of bound K-ary tree.");

    }

    /**
     * Get parent key
     *
     * @param child's key
     * @return parent's key
     */
    public int getParent(int child) {
        if (child < getCapacity(this.level) && child >= 0) {
            return (k + child - 1) / k - 1;
        } else throw new java.lang.RuntimeException("getValue. Out of bound K-ary tree.");
    }

    /**
     * Tree preorder traversal
     *
     * @param key    The node to start of traversal
     * @param map    Function to action with inner element and each element of tree
     * @param values Values array to action
     * @param <R>    Type of values to
     */
    public <R> void preOrderTraversal(int key, BiFunction<ArrayList<Object>, Object, Object> map, ArrayList<Object> values) {
        if (key >= getCapacity(this.level)) return;

        map.apply(values, getValue(key));
        for (int i = 0; i < this.k; i++) {
            preOrderTraversal(this.getChild(key, i), map, values);
        }

    }

    /**
     * Tree postorder traversal
     *
     * @param <R>    Return type of BiFunction
     * @param key    The node to start of traversal
     * @param map    BiFunction to action with inner element and each element of tree
     * @param values Values array to action
     */
    public <R> void postOrderTraversal(int key, BiFunction<ArrayList<Object>, Object, Object> map, ArrayList<Object> values) {
        if (key >= getCapacity(this.level)) return;

        for (int i = 0; i < this.k; i++) {
            postOrderTraversal(getChild(key, i), map, values);
        }
        map.apply(values, getValue(key));
    }


    /**
     * Also base on K-ary Tree easy to implement level ordered traversal
     * traversal from left to right
     *
     * @param <R>    Type of values to
     * @param key    The node to start of traversal
     * @param map    Function to action with inner element and each element of tree
     * @param values Values array to action
     */
    public <R> void levelOrderTraversal(int key, BiFunction<ArrayList<Object>, Object, Object> map, ArrayList<Object> values) {
        if (key >= getCapacity(this.level)){return;}

        for (int i = key; i < getCapacity(this.level); i++) {
            map.apply(values, getValue(i));
        }
    }
}
