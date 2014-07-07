package com.forged.data;

public class CategoryTree extends Tree<Category> {

    private CategoryTree() {

    }

    public static CategoryTree createCategoryTree() {
        return new CategoryTree();
    }

    public boolean addRootCategory(final Category c) {
        return addRootNode(c);
    }

    public boolean addMetaFileToCategory(final MetaFile f) {
        return getCurrentNode().addMetaFile(f);
    }
}
