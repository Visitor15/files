package com.forged.main;

import android.app.Application;

public class FilesApp extends Application {
    private static FilesApp singleton;

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
        super.onCreate();
    }
}
