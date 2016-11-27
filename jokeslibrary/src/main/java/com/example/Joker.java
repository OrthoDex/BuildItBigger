package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Joker {

    public Joker() {
        super();
        getJokeData();
    }

    private String jokeString = null;

    public void getJokeData() {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        final String JOKES_BASE_URL = "http://api.icndb.com/jokes/random?limitTo=[nerdy]";

        try {

            URL url = new URL(JOKES_BASE_URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if(inputStream == null){
                jokeString = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null){
                //Adding new line to make debugging easier
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0){
                //Empty Stream, so dont parse
                jokeString = null;
            }

            jokeString = buffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (reader != null){
                try
                {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getJoke() {
        getJokeData();
        return jokeString;
    }
}
