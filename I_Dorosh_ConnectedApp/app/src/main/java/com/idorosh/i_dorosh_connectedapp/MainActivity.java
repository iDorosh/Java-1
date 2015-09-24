package com.idorosh.i_dorosh_connectedapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    //Progress bar for showing that the data is being downloaded
    ProgressBar pb;

    //Array that stores the data from the Info custom class
    ArrayList<Info> Info = new ArrayList<>();
    //Sets default tag when the app first loads
    String searchFieldText = "animals";
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Stops list view from moving up over the search bar when the keyboard is pulled up.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //Sets the progress bar to invisible when the app loads
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);

        //Runs refresh method when the sync button is clicked
        TextView refreshButton = (TextView) findViewById(R.id.button);
        refreshButton.setOnClickListener(refresh);

        //Runs search method when the search button is clicked
        TextView searchButton = (TextView) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(searchTag);

        //Checks for netowrk
        checkNetwork();
        //Requests data and passes through the search field text
        requestData(searchFieldText);

    }

    //Populate UI
    protected void updateDisplay() {

        //Array to hold image urls
        final ArrayList<String> images = new ArrayList<>();

        //gets image urls
        for(int i =0; i < Info.size(); i++)
        {
            String img = Info.get(i).getmMedia();
            images.add(img);

        }

        //Gets list view
        listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_row_layout, R.id.title, images) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                //Getting text views from the xml files
                TextView text1 = (TextView) view.findViewById(R.id.title);
                TextView text2 = (TextView) view.findViewById(R.id.width);

                //Some posts don't have a title so this is used to set it to untitled.
                if(Info.get(position).getmTitle() != null && !Info.get(position).getmTitle().isEmpty()) {
                    text1.setText(Info.get(position).getmTitle());
                } else {
                    text1.setText("Untitled");
                }
                text2.setText(Info.get(position).getmWidth()+"x"+Info.get(position).getmHeight());

                return view;
            }

        };

        //Setting adapter
        listView.setAdapter(adapter);

        //Runs when row is selected
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //New intent to pass data over to next screen
                Intent photoView = new Intent(getApplicationContext(), FlickrImageView.class);
                //creating variable with title and image information
                String selectedTitle = (Info.get(position).getmTitle());
                String selectedImage = (images.get(position));
                //Putting extra into Intent
                photoView.putExtra("selectedTitle", selectedTitle);
                photoView.putExtra("selectedPhoto", selectedImage);
                //Opening FlickrImageView
                startActivity(photoView);
            }
        });
    }



    //Networking
    //Starting progress bar
    //Getting URL information
    //Creating JSON object
    //Getting JSON Array from the object
    //Creating Custom objects using the custom class and the data from the JSON object
    //Stopping progress bar


    public boolean checkNetwork()
    {
        ConnectivityManager mgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = mgr.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            } else {
                return false;
            }
    }


    private View.OnClickListener refresh = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Checking network and opening toast if there is no network
            if (checkNetwork()) {
                requestData(searchFieldText);
            } else {
                Toast.makeText(MainActivity.this, "Network isn't Available", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener searchTag = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Searching for tag that is put into the url when request data is run
            TextView searchText = (TextView) findViewById(R.id.editText);

            if (searchText.getText().toString().trim().length() != 0) {
                searchFieldText = searchText.getText().toString();
                requestData(searchFieldText);
            } else {
                Toast.makeText(MainActivity.this, "Tag empty", Toast.LENGTH_SHORT).show();
            }
            //Setting field to empty after search
            searchText.setText("");
        }
    };

    private void requestData(String tagText) {
        //Clears Info array
        Info.clear();
        GetData data = new GetData();
        //Url that excepts tag from tagText
        String urlString = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=df84bec775bd78998b2098c37a1a92c7&tags="+tagText+"&format=json&nojsoncallback=1&text=cats&extras=url_c";
        URL url = null;

        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        data.execute(url);
    }

    //Getting data with an Async Task
    private class GetData extends AsyncTask<URL, String, JSONArray> {
        @Override
        protected void onPreExecute() {
            //Starting Progress bar loading
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONArray doInBackground(URL... urls) {
            String jsonString = "";

            //Connects to url and saves the json information to json strings
            for (URL queryURL : urls) {
                try {
                    URLConnection connection = queryURL.openConnection();
                            jsonString = IOUtils.toString(connection.getInputStream());

                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //Objects and Array to hold information
            JSONArray apiArray = null;
            JSONObject apiData;
            JSONObject apiData2;

            //Sets apiData JSONObject using the jsonString
            try {
                apiData = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
                apiData = null;
            }

            //Gets photos object from the JSONObject
            try {
                apiData2 = apiData.getJSONObject("photos");
            } catch (JSONException e) {
                e.printStackTrace();
                apiData2 = null;
            }

            //Getting the array from the JSON Object
            try {
                assert apiData != null;
                apiArray = apiData2.getJSONArray("photo");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return apiArray;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            //Setting Object from JSON Array
           for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject childJSONObject = null;
                try {
                    childJSONObject = jsonArray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    //Creating new objects in custom class using the information in the JSON object from childJSONObject
                    if (childJSONObject.has("url_c")){
                        Info.add(new Info(childJSONObject.getString("title"), childJSONObject.getString("url_c"), childJSONObject.getString("width_c"), Integer.toString(childJSONObject.getInt("height_c"))));
                    } else {
                        Info.add(new Info(childJSONObject.getString("title"), "https://s.yimg.com/pw/images/en-us/photo_unavailable.png", childJSONObject.getString("width_c"), Integer.toString(childJSONObject.getInt("height_c"))));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //Updates UI with information
            updateDisplay();
            //Stops progress bar
            pb.setVisibility(View.INVISIBLE);
        }
    }
}
