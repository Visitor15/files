package com.forged.data;

import android.net.Uri;

import java.io.File;

public class MetaFile {

    public static final String DEFAULT_ALIAS = "None";

    private String _alias;
    private String _name;
    private String _uri;

    private int _accessedCount;

    private MetaFile() { }

    public static MetaFile createMetaFile() {
        MetaFile f = new MetaFile();
        f._alias = "";
        f._name = "";
        f._uri = "";
        return f;
    }

    public static MetaFile createMetaFile(final String name, final String alias, final String uri) {
        MetaFile f = new MetaFile();
        f._name = name;
        f._alias = alias;
        f._uri = uri;
        return f;
    }

    public String getFilename() {
        return _name.toString();
    }

    public String getAlias() {
        return _alias.toString();
    }

    public String getUriString() {
        return _uri.toString();
    }

    public Uri getUri() {
        return Uri.fromFile(new File(_uri));
    }
}
