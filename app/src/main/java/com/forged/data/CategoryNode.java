package com.forged.data;

/**
 * Created by visitor15 on 7/13/14.
 */
public class CategoryNode extends Node<Category> {

    private static volatile boolean _isPrivate = false;
    private static volatile String _uri = "";
    private static volatile String _displayName = "";
    private static volatile String _fileName = "";

    public CategoryNode() {

    }

    public static CategoryNode createNewNode() {
        return new CategoryNode();
    }

    public Category getCategory() {
        return getData();
    }
}
