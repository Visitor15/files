package com.forged.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.forged.data.Category;
import com.forged.data.CategoryTree;
import com.forged.exceptions.NodeNotFoundException;
import com.forged.files.R;
import com.forged.jobs.QueryMediaStoreJob;
import com.forged.utils.QueryData;


public class MainActivity extends Activity {

    CategoryTree mainTree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    @SuppressLint("ValidFragment")
    public class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onStart() {
            super.onStart();
            QueryMediaStoreJob queryJob = new QueryMediaStoreJob(new TaskCallback() {

                @Override
                public void onTaskFinished(CategoryTree tree) {
                    mainTree = tree;
                    continueWork();
                }
            });
            queryJob.execute(QueryData.QUERY_TYPE.FILES);
        }
    }

    public void continueWork() {
        int count = 0;
        for(int i = 0; i < mainTree.size(); i++) {
            ++count;
        }

        if(mainTree.size() > 0) {
            for(int i = 0; i < mainTree.size(); ++i) {
                try {
                    Category c = mainTree.getNextRootNode();

                } catch (NodeNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public interface TaskCallback {

        public abstract void onTaskFinished(final CategoryTree tree);
    }
}
