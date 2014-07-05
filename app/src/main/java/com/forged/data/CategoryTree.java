package com.forged.data;

public class CategoryTree extends Tree<Category> {

    private CategoryTree() {

    }

    public static CategoryTree createCategoryTree() {
        return new CategoryTree();
    }
}
