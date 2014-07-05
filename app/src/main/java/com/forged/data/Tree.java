package com.forged.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tree<T extends Node> {

    private static volatile Object _lock = new Object();

    private volatile List<Node<T>> _nodes;

    /* Reusable Iterator */
    private Iterator<T> _itter;

    public Tree() {
        _nodes = new ArrayList<Node<T>>();
    }

    public static <T extends Node> Tree<T> createNewTree() {
        return new Tree<T>();
    }

    public synchronized boolean addRootNode(T node) {

        if(_nodes.contains(node)) {
            return false;
        }

        _nodes.add(node);

        return true;
    }

    public int size() {
        return _nodes.size();
    }

    public Node<T> getNodeAt(int index) {
        return _nodes.get(index);
    }
}
