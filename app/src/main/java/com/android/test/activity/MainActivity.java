package com.android.test.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.test.R;
import com.android.test.adapter.NewsListAdapter;
import com.android.test.data.DataModel;
import com.android.test.network.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private ArrayList<DataModel> newsList;
    private final String TAG = MainActivity.class.getSimpleName();

    private ListView mListView;
    private String headerTitle;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.canada_news_list);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.pullToRefresh) ;
        newsList = new ArrayList<>();

        loadDataFromJSON();
    }

    private  void loadDataFromJSON()
    {
        new DownloadJsonAsync().execute();
    }

    @Override
    public void onRefresh() {
        loadDataFromJSON();
        refreshLayout.setRefreshing(false);
    }

    private class DownloadJsonAsync extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler requestHandler = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = requestHandler.makeServiceCall(getResources().getString(R.string.info_json_url));

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    //Getting the Title Header
                    headerTitle = jsonObj.getString("title");
                    Log.e(TAG, "Title header: " + headerTitle);
                    // Getting JSON Array node
                    JSONArray rows = jsonObj.getJSONArray("rows");

                    // looping through All Contacts
                    for (int i = 0; i < rows.length(); i++) {
                        JSONObject c = rows.getJSONObject(i);

                        String title = c.getString("title");
                        String desc = c.getString("description");
                        String url = c.getString("imageHref");
                        DataModel model = new DataModel(title,desc,url);
                        // adding contact to contact list
                        newsList.add(model);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            setTitle(headerTitle);
            Log.d(TAG, "onPostExecute  List visibility."+mListView.getVisibility());
            NewsListAdapter adapter = new NewsListAdapter(newsList,MainActivity.this);
            mListView.setAdapter(adapter);
        }
    }

}


