package edu.uw.listdatademo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

//        Button btn = (Button)findViewById(R.id.btnSearch);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.v(TAG, "Button clicked!");
//            }
//        });



        //model
//        String[] data = new String[99];
//        for(int i=99; i>0; i--){
//            data[99-i] = i+ " bottles of beer on the wall";
//        }
//        Log.v(TAG,"Data: "+ Arrays.toString(data));


        //view
        //-> see xml!


        //controller
        adapter = new ArrayAdapter<String>(this,
            R.layout.list_item, R.id.txtItem, new ArrayList<String>());

        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = (String)parent.getItemAtPosition(position);
                Log.v(TAG, "You clicked on: "+text);
            }
        });

    }

    public class MovieDownloadTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {

            String[] data = MovieDownloader.downloadMovieData(params[0]);

            return data;
        }

        @Override
        protected void onPostExecute(String[] movies) {
            super.onPostExecute(movies);

            adapter.clear();
            for(String movie : movies){
                adapter.add(movie);
            }
        }
    }


    public void handleClick(View v){
        Log.v(TAG, "Button handled");

        EditText text = (EditText)findViewById(R.id.txtSearch);
        String searchTerm = text.getText().toString();

        Log.v(TAG, "You searched for: "+searchTerm);
        MovieDownloadTask task = new MovieDownloadTask();
        task.execute(searchTerm);

    }

}
