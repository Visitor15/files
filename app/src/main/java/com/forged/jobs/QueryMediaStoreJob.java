package com.forged.jobs;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;

import com.forged.data.Category;
import com.forged.data.CategoryTree;
import com.forged.data.MetaFile;
import com.forged.main.FilesApp;
import com.forged.main.MainActivity.TaskCallback;
import com.forged.utils.QueryData;

import java.util.LinkedList;


public class QueryMediaStoreJob extends AsyncTask<QueryData.QUERY_TYPE, Integer, CategoryTree> {

    private TaskCallback mCallback;

    //Some audio may be explicitly marked as not being music
    public static String IS_MUSIC_SELECTION = MediaStore.Audio.Media.IS_MUSIC + " != 0";

    public static String[] AUDIO_PROJECTION_STR = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE
    };

    public static String[] VIDEO_PROJECTION_STR = {
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.RESOLUTION,
            MediaStore.Video.Media.SIZE
    };

    public static String[] IMAGES_PROJECTION_STR = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.TITLE
    };

    public static String[] FILES_PROJECTION_STR = {
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.TITLE,
            MediaStore.Files.FileColumns.MIME_TYPE
    };

    public QueryMediaStoreJob(TaskCallback callback) {
        mCallback = callback;
    }

    /**
     * Runs on the UI thread before {@link #doInBackground}.
     *
     * @see #onPostExecute
     * @see #doInBackground
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p/>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param categoryTree The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(CategoryTree categoryTree) {
        super.onPostExecute(categoryTree);
        mCallback.onTaskFinished(categoryTree);
    }

    /**
     * Runs on the UI thread after {@link #publishProgress} is invoked.
     * The specified values are the values passed to {@link #publishProgress}.
     *
     * @param values The values indicating progress.
     * @see #publishProgress
     * @see #doInBackground
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    /**
     * <p>Runs on the UI thread after {@link #cancel(boolean)} is invoked and
     * {@link #doInBackground(Object[])} has finished.</p>
     * <p/>
     * <p>The default implementation simply invokes {@link #onCancelled()} and
     * ignores the result. If you write your own implementation, do not call
     * <code>super.onCancelled(result)</code>.</p>
     *
     * @param categoryTree The result, if any, computed in
     *                     {@link #doInBackground(Object[])}, can be null
     * @see #cancel(boolean)
     * @see #isCancelled()
     */
    @Override
    protected void onCancelled(CategoryTree categoryTree) {
        super.onCancelled(categoryTree);
    }

    /**
     * <p>Applications should preferably override {@link #onCancelled(Object)}.
     * This method is invoked by the default implementation of
     * {@link #onCancelled(Object)}.</p>
     * <p/>
     * <p>Runs on the UI thread after {@link #cancel(boolean)} is invoked and
     * {@link #doInBackground(Object[])} has finished.</p>
     *
     * @see #onCancelled(Object)
     * @see #cancel(boolean)
     * @see #isCancelled()
     */
    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected CategoryTree doInBackground(QueryData.QUERY_TYPE... params) {
        QueryData.QUERY_TYPE queryType;
        String[] queryProjection;
        Cursor c;

        CategoryTree categoryTree = CategoryTree.createCategoryTree();

        for(int i = 0; i < params.length; i++) {
            queryType = params[i];
            c = getCursor(queryType);

            if(c != null) {
                Category category = Category.createNewCategory(queryType.name());
                while(c.moveToNext()) {
                    MetaFile f = MetaFile.createMetaFile(c.getString(3), c.getString(5), c.getString(4));
                    category.addMetaFile(f);
                }
                categoryTree.addRootNode(category);
            }
        }

        return categoryTree;
    }

    private Cursor getCursor(QueryData.QUERY_TYPE type) {
        switch(type) {
            case AUDIO:
                return FilesApp.getReference().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, AUDIO_PROJECTION_STR, IS_MUSIC_SELECTION, null, null);
            case FILES:
                return FilesApp.getReference().getContentResolver().query(Uri.fromFile(Environment.getExternalStorageDirectory()), FILES_PROJECTION_STR, null, null, null);
            case IMAGES:
                return FilesApp.getReference().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGES_PROJECTION_STR, null, null, null);
            case VIDEO:
                return FilesApp.getReference().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, VIDEO_PROJECTION_STR, null, null, null);
        }

        return null;
    }

    private String[] getQueryProjectionString(QueryData.QUERY_TYPE type) {
        LinkedList<String> projection = new LinkedList<String>();

        switch(type) {

            case ALL:
                break;
            case AUDIO:
                projection.add(MediaStore.Audio.Media._ID);
                break;
            case FILES:
                break;
            case IMAGES:
                break;
            case VIDEO:
                break;
        }

        return projection.toArray(new String[projection.size()]);
    }
}
