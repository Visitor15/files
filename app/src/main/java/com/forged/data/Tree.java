package com.forged.data;

import com.forged.exceptions.NodeNotFoundException;

import java.util.Iterator;
import java.util.LinkedList;

public class Tree<T extends Node> {

    /* Opting for fine-grain synchronization.
    *  USE WHEN WORKING WITH _nodes OR _currentNode! */
    private static volatile Object _lock = new Object();

    private volatile LinkedList<T> _nodes;

    private T _currentNode;

    /* Reusable Iterator */
    private Iterator<T> _itter;

    public Tree() {
        _nodes = new LinkedList<T>();
    }

    public boolean addRootNode(final T node) {
        synchronized(_lock) {
            if (!_nodes.contains(node)) {
                _nodes.add(node);
                if(_itter == null) {
                    _itter = _nodes.listIterator(0);
                }
                return true;
            }
        }
        return false;
    }

    public boolean addChildNode(final T node) {
        synchronized(_lock) {
            if (_currentNode != null) {
                return _currentNode.addChild(node);
            }
        }
        return false;
    }

    public boolean addParentNode(final T node) {
        synchronized(_lock) {
            if (_currentNode != null) {
                try {
                    Node parent = (Node) _currentNode.getParent();
                    parent.addChild(node); // This returns a boolean, but we don't care here.
                    return _currentNode.addParent(node);
                } catch (NodeNotFoundException e) {
                /* Nothing to do but continue and return false. */
                } catch (ClassCastException e) {
                /* We should never be able to have a casting exception, but just in case. */
                }
            }
        }
        return false;
    }

    public T begin() throws NodeNotFoundException {
        synchronized(_lock) {
            if(size() > 0) {
                _itter = _nodes.listIterator(0);
                _currentNode = _nodes.get(0);
                return _currentNode;
            }
        }
        throw NodeNotFoundException.throwException();
    }

    public T getNextNode() throws NodeNotFoundException {
        synchronized(_lock) {
            if(_currentNode != null) {
                    return (T) _currentNode.getNextChild();
            }
            else {
                if(_itter != null && size() > 0) {
                    _itter = _nodes.listIterator();
                    _currentNode = _itter.next();
                    return _currentNode;
                }
            }
        }
        throw NodeNotFoundException.throwException();
    }

    public T getNextRootNode() throws NodeNotFoundException {
        synchronized(_lock) {
            if (_itter != null) {
                if (_itter.hasNext()) {
                    _currentNode = _itter.next();
                    return _currentNode;
                }
            }
        }
        throw NodeNotFoundException.throwException();
    }

    public T getNextChildNode() throws NodeNotFoundException {
        if(_currentNode != null) {
            return (T) _currentNode.getNextChild();
        }
        else {
            return (T) begin().getNextChild();
        }
    }

    public T stepIntoCurrentNode() throws NodeNotFoundException {
        if(_currentNode != null) {
            _currentNode = (T) _currentNode.getNextChild();
            return _currentNode;
        }
        else {
            _currentNode = (T) begin().getNextChild();
            return _currentNode;
        }
    }

    public T stepUpFromCurrentNode() throws NodeNotFoundException {
        if(_currentNode != null) {
            _currentNode = (T) _currentNode.getParent();
            return _currentNode;
        }
        else {
            return begin();
        }
    }

    public T getCurrentNode() {
        return _currentNode;
    }

    public int size() {
        return _nodes.size();
    }

    public T getNodeAt(int index) throws IndexOutOfBoundsException {
        return _nodes.get(index);
    }
}
