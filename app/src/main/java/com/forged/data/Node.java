package com.forged.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

enum DATA_TYPE {
    PARENT,
    CHILD
}

public class Node<T> {

    private volatile HashMap<DATA_TYPE, LinkedList<T>> _dataMap;

    private T _currentChild;
    private T _currentParent;
    private T _data;

    private static volatile Object _lock = new Object();

    /* Reusable Iterator */
    Iterator<T> _itter;

    public Node() {
        _currentChild = null;
        _currentParent = null;

        _dataMap = new HashMap<DATA_TYPE, LinkedList<T>>();
        _dataMap.put(DATA_TYPE.PARENT, new LinkedList<T>());
        _dataMap.put(DATA_TYPE.CHILD, new LinkedList<T>());
    }

    public static <T> Node<T> createNewNode() {
        return new Node<T>();
    }


    public synchronized boolean addChild(T child) {
        if(_dataMap.get(DATA_TYPE.CHILD).contains(child)) {
            return false;
        }

        _dataMap.get(DATA_TYPE.CHILD).add(child);

        return true;
    }

    public synchronized boolean addParent(T parent) {
        if(_dataMap.get(DATA_TYPE.PARENT).contains(parent)) {
            return false;
        }

        _dataMap.get(DATA_TYPE.PARENT).add(parent);

        return true;
    }
}
