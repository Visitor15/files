package com.forged.data;

import com.forged.exceptions.NodeNotFoundException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

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

    private ListIterator<T> _childItter;
    private ListIterator<T> _parentItter;

    public Node() {
        _currentChild = null;
        _currentParent = null;
        _data = null;

        _dataMap = new HashMap<DATA_TYPE, LinkedList<T>>();
        _dataMap.put(DATA_TYPE.PARENT, new LinkedList<T>());
        _dataMap.put(DATA_TYPE.CHILD, new LinkedList<T>());
    }

    public static <T> Node<T> createNewNode() {
        return new Node<T>();

    }

    public boolean addChild(T child) {
        if(!_dataMap.get(DATA_TYPE.CHILD).contains(child)) {
            _dataMap.get(DATA_TYPE.CHILD).add(child);
            if(_childItter == null) {
                _childItter = _dataMap.get(DATA_TYPE.CHILD).listIterator(0);
            }
        }
        return false;
    }

    public boolean addParent(T parent) {
        if(!_dataMap.get(DATA_TYPE.PARENT).contains(parent)) {
            _dataMap.get(DATA_TYPE.PARENT).add(parent);
            if(_parentItter == null) {
                _parentItter = _dataMap.get(DATA_TYPE.PARENT).listIterator(0);
            }
        }
        return false;
    }

    public boolean hasChildren() {
        return _dataMap.get(DATA_TYPE.CHILD).size() > 0;
    }

    public boolean hasParent() {
        return _dataMap.get(DATA_TYPE.PARENT).size() > 0;
    }

    public T getNextChild() throws NodeNotFoundException {
        if(_childItter != null) {
            if (_childItter.hasNext()) {
                _currentChild = _childItter.next();
                return _currentChild;
            }
        }
        throw NodeNotFoundException.throwException();
    }

    public T getPreviousChild() throws NodeNotFoundException {
        if(_childItter != null) {
            if (_childItter.hasPrevious()) {
                _currentChild = _childItter.previous();
                return _currentChild;
            }
        }
        throw NodeNotFoundException.throwException();
    }

    public T getCurrentChild() throws NodeNotFoundException {
        if(_currentChild != null) {
            return _currentChild;
        }
        throw NodeNotFoundException.throwException();
    }

    public T getParent() throws NodeNotFoundException {
        if(_currentParent != null) {
            return _currentParent;
        }
        throw NodeNotFoundException.throwException();
    }
}
