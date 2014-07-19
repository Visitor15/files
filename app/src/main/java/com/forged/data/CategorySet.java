package com.forged.data;

import java.util.LinkedList;

/**
 * Created by visitor15 on 7/13/14.
 */
public class CategorySet {

    private long _index;

    private LinkedList<CategoryNode> _masterCategoryList;

    private CategorySet() {

    }

    public static CategorySet createCategorySet() {
        CategorySet set = new CategorySet();

        set._masterCategoryList = new LinkedList<CategoryNode>();

        return set;
    }

    public Category getCategoryById(final CategoryID id) {
        return _masterCategoryList.get(id.getCategoryPosition()).getCategory();
    }

    public void setIndex(int index) {
        _index = index;
    }

    public void addCategoryNode(final CategoryNode node) {
        _masterCategoryList.add(node);
    }
}
