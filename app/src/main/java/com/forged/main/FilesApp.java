package com.forged.main;

import android.app.Application;

import com.forged.data.Category;
import com.forged.data.CategoryID;
import com.forged.data.CategorySet;
import com.forged.data.MetaFile;

import java.util.LinkedList;

public class FilesApp extends Application {
    private static volatile FilesApp singleton;

    private static volatile LinkedList<CategorySet> _masterCategoryList;

    private static volatile LinkedList<MetaFile> _masterFileList;

    public static final FilesApp getReference() {
        return FilesApp.singleton;
    }

    public FilesApp() {
        super();
    }

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        FilesApp.singleton = this;
        _masterCategoryList = new LinkedList<CategorySet>();
        super.onCreate();
    }

    public synchronized MetaFile getMetaFileById(final CategoryID id) {
        return _masterFileList.get(id.getFilePosition());
    }

    public synchronized Category getCategoryById(final CategoryID id) {
        return _masterCategoryList.get(id.getCategorySetPosition()).getCategoryById(id);
    }

    public synchronized CategorySet getCategorySetById(final CategoryID id) {
        return _masterCategoryList.get(id.getCategorySetPosition());
    }

    public void addMetaFile(final MetaFile file) {
        file.setIndex(_masterFileList.size());
        _masterFileList.add(file);
    }

    public void addCategorySet(CategorySet set) {
        set.setIndex(_masterCategoryList.size());
        _masterCategoryList.add(set);
    }
}
