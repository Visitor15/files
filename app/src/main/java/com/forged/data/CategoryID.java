package com.forged.data;

import android.util.Base64;

import java.nio.ByteBuffer;
import java.util.Random;

/**
 * Created by visitor15 on 7/13/14.
 */
public class CategoryID {

    private long _id;             // unique identifier
    private String _strID;        // unique string identifier

    private int _categorySetPos;        // x-axis
    private int _filePos;               // y-axis
    private int _categoryPos;           // z-axis

    public CategoryID() {

    }

    public static CategoryID generateID() {
        CategoryID id = new CategoryID();
        id._categorySetPos  = 0;
        id._filePos         = 0;
        id._categoryPos     = 0;

        Random ran = new Random();
        id._id = ran.nextLong();

        ByteBuffer buffer = ByteBuffer.allocate(Long.SIZE);
        buffer.putLong(id._id);

        id._strID = Base64.encodeToString(buffer.array(), 0);

        return id;
    }

    public int getCategorySetPosition() {
        return _categorySetPos;
    }

    public int getCategoryPosition() {
        return _categoryPos;
    }

    public int getFilePosition() {
        return _filePos;
    }
}
