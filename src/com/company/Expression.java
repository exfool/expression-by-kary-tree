package com.company;

import java.util.Stack;

public class Expression {

    /**
     * Parse usual expression to polish notation stack
     *
     * @param input with expression
     * @throws Exception if some of character in expression is incorrect
     */
    private static Stack<String> parser(String input) throws Exception {
        Stack<String> result = new Stack<>(); // stack for result expression in polish notation
        Stack<String> buffer = new Stack<>(); // buffer stack for operations
        input = input.replace(" ", ""); // remove all space


        //buffer variable for each character
        char current = '\0';
        char prev = '\0';

        int length = input.length(); // length of input expression

        for (int i = length - 1; i >= 0; i--) {
            current = input.toCharArray()[i]; // reading expression character by character from end

            if (Character.isDigit(current)) {
                // if chatacter is digit

                if (Character.isDigit(prev)) {
                    // concatenate digit if previous values was digit also
                    result.push(current + "" + result.pop());
                } else {
                    // if digit is single
                    result.push(current + "");
                }

            } else if (")".equals(current + "")) {
                // if brackets open just add it too buffer stack
                buffer.push(current + "");
            } else if ("(".equals(current + "")) {
                // if brackets close then pick up all operations from stack to result
                while (!buffer.isEmpty() && !")".equals(buffer.peek() + "")) {
                    //Push to result stack from buffer stack until meet open bracket
                    result.push(buffer.pop());
                }
                // remove open bracket. If we haven't it, then will be exception
                try {
                    buffer.pop();
                } catch (Throwable t) {
                    throw new java.lang.Exception("Parser: couldn't found closed bracket.");
                }

            } else if ("*".equals(current + "") || "/".equals(current + "") || "+".equals(current + "") || "-".equals(current + "") || "^".equals(current + "")) {
                // if character is operation
                while (!buffer.isEmpty() && rank(buffer.peek()) > rank(current + "")) {
                    //Push to result stack from buffer stack until operation's rank more than rank current operation
                    result.push(buffer.pop());
                }
                //push current operation to buffer
                buffer.push(current + "");

            } else throw new java.lang.Exception("Parser: some of char of variable isn't correct.");

            prev = current;
        }
        while (!buffer.isEmpty()) {
            // do until buffer isn't empty
            if (!")".equals(buffer.peek())) {
                //check for missing open brackets
                result.push(buffer.pop());
            } else {
                throw new java.lang.Exception("Parser: couldn't found open bracket.");
            }
        }

        return result;
    }

    /**
     * Get rank of operator
     *
     * @param input operator
     * @return rank
     */
    private static int rank(String input) {
        switch (input) {
            case "+":
            case "-":
                return 0;
            case "*":
            case "/":
                return 1;
            case "^":
                return 2;
            default:
                return -1;
        }
    }

    /**
     * Creating expression tree with
     *
     * @param expressions
     */
    public static int compute(String expressions) throws Exception {
        KaryTree<String> tree = new KaryTree<>(2); // create binary tree base on KaryTree
        setToNode(tree.getRoot(),parser(expressions), tree); // set starting from root parsed expression to tree
        return calculateByTree(tree); //calculate this expression tree
    }

    /**
     * Wrapper to calculateRecursion
     * @param tree's calculation
     * @return compute value
     */
    public static int calculateByTree(KaryTree<String> tree) throws Exception {
        return  calculateRecursion(tree.getRoot(), tree);
    }

    /**
     * Calculate value with expression tree
     * @param node
     * @param tree
     * @return
     */
    private static int calculateRecursion(int node, KaryTree<String> tree) throws Exception {

        if (node >= tree.getLength()) throw new java.lang.Exception("calculateRecursion:something wrong, out of bound tree.");
        if      (
                        "*".equals(tree.getValue(node)) ||
                        "/".equals(tree.getValue(node)) ||
                        "+".equals(tree.getValue(node)) ||
                        "-".equals(tree.getValue(node)) ||
                        "^".equals(tree.getValue(node))
                )
        {
            //if node's value is operation then calculate it
            switch (tree.getValue(node)){
                case "*":
                    return calculateRecursion(tree.getChild(node, 0), tree) * calculateRecursion(tree.getChild(node, 1), tree);
                case "/":
                    return calculateRecursion(tree.getChild(node, 0), tree) / calculateRecursion(tree.getChild(node, 1), tree);
                case "-":
                    return calculateRecursion(tree.getChild(node, 0), tree) - calculateRecursion(tree.getChild(node, 1), tree);
                case "+":
                    return calculateRecursion(tree.getChild(node, 0), tree) + calculateRecursion(tree.getChild(node, 1), tree);
                case "^":
                    return (int)Math.pow(calculateRecursion(tree.getChild(node, 0), tree), calculateRecursion(tree.getChild(node, 1), tree));
            }
        }

        return Integer.valueOf(tree.getValue(node)); // if node's value is number
    }



    /**
     * Recours
     * @param node
     * @param expressions
     * @param tree
     */
    private static void  setToNode(int node, Stack<String> expressions, KaryTree<String> tree){
        if (expressions.isEmpty())return; // recursion's condition of exit when stack is empty
        tree.setValue(node, expressions.pop()); // anyway set value from stack
        if      (
                "*".equals(tree.getValue(node)) ||
                "/".equals(tree.getValue(node)) ||
                "+".equals(tree.getValue(node)) ||
                "-".equals(tree.getValue(node)) ||
                "^".equals(tree.getValue(node))
                )
        {
            // if element from stack is operation then start recursion
            setToNode(tree.getChild(node, 0), expressions, tree);
            setToNode(tree.getChild(node,1), expressions, tree);
        }
    }

}
