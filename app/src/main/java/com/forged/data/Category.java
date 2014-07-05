package com.forged.data;

import java.util.ArrayList;
import java.util.List;

public class Category extends Node<Category> {

    private String _name;

    private int _fileCount;

    private int _childCategoryCount;

    private int _accessedCount;

    private volatile List<MetaFile> _files;

    private Category() { }

    public static Category createNewCategory(String name) {
        Category c = new Category();
        c._name = name;
        c._files = new ArrayList<MetaFile>();
        return c;
    }

    public synchronized boolean addMetaFile(final MetaFile f) {
        if(_files.contains(f)) {
            return false;
        }

        _files.add(f);

        return true;
    }
}
