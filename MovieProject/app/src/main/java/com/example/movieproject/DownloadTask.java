package com.example.movieproject;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;

public class DownloadTask extends AsyncTask<String, Void, String> {
    Hashtable<String, String> my_dict = new Hashtable<String, String>();

    @Override
    protected String doInBackground(String... urls){
        //
        StringBuilder stb = new StringBuilder("");
        StringBuilder stb2 = new StringBuilder("");
        Log.d("json", "teststring");
        try{
            URL url = new URL(urls[0]);
            Log.d("json", urls[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = bf.readLine()) !=null){
                stb.append(line+"\n");
            }
            JSONObject jsonObject = new JSONObject(stb.toString());


            String searched = jsonObject.optString("expression"); // you got the name to check for searched content
            String[] parts = searched.split(" ");
            String Year = parts[parts.length - 1];
            String Title = null;
            for(int i=0; i<parts.length-1; i++){
                if (Title == null){
                    Title = parts[i];
                }else{
                    Title = Title+ (" "+parts[i]);// adds space if more words
                }

            }

            JSONArray jsonArray =jsonObject.getJSONArray("results");
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject results = jsonArray.getJSONObject(i);
                if (results.optString("title").equalsIgnoreCase(Title) && results.optString("description").contains(Year)){

                    Log.d("json","found?"); // found the movie with the title

                    // no need to return the id to the activity i can just get the rating here

                    my_dict.put(Title+"I", results.optString("image"));
                    Log.d("json",results.optString("id"));
                    URL newurl = new URL("https://imdb-api.com/en/API/UserRatings/k_zbeil8oy/"+results.optString("id"));
                    HttpURLConnection newcon = (HttpURLConnection) newurl.openConnection();
                    BufferedReader newbf = new BufferedReader(new InputStreamReader(newcon.getInputStream()));
                    String newline = null;
                    while ((newline = newbf.readLine()) !=null){
                        stb2.append(newline+"\n");
                    }
                    JSONObject newjsonObject = new JSONObject(stb2.toString());
                    // reason i'm not doing checks here is because i have a try and i trust the api to work...
                    double totalvotes = newjsonObject.optDouble("totalRatingVotes"); // Never mind the api is broken so doing it the long way -- i better get more marks for this XDD
                    JSONArray newjsonArray = newjsonObject.getJSONArray("ratings");
                    double votesadded = 0;
                    for(int v=0; v<newjsonArray.length(); v++) {
                        JSONObject newresults = newjsonArray.getJSONObject(v);
                        votesadded = votesadded+ newresults.getDouble("votes")*(10-v); // know that it's gonna loop 10 times soo...
                    }
                    double RealRating = Math.round((votesadded/totalvotes) * 10.0) / 10.0;;
                    Log.d("json", "Total Rating:"+ RealRating);
                    my_dict.put(Title+"R", String.valueOf(RealRating));
                }

            }



        }catch(Exception ex){
            ex.printStackTrace();
        }
        return stb.toString();
    }

    @Override

    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    Hashtable<String, String> getAPI(){ //
        return my_dict;
    }
}