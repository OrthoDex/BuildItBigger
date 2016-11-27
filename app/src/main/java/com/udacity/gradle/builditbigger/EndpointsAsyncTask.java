package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.ishaan.myapplication.backend.myApi.MyApi;
import com.example.randomjokes.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by ishaan on 27/11/16.
 */
public class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
    private MainActivity mainActivity;
    private MyApi myApiService = null;
    Context context;
    String joke;

    public EndpointsAsyncTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(Context... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("https://udacity-and-p4-build-it-bigger.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            context = params[0];
            myApiService = builder.build();
        }

        try {
            return myApiService.joke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        joke = getJokeDataFromString(result);
        mainActivity.spinner.setVisibility(View.GONE);
        Intent jokeIntent = new Intent(context, JokeActivity.class)
                .putExtra("joke", joke);
        mainActivity.startActivity(jokeIntent);
    }

    private String getJokeDataFromString(String data) {

        JSONObject object, value;
        String joke = null;
        try {
            Log.d(MainActivity.class.getSimpleName(), "DATA: " + data);
            object = new JSONObject(data);
            value = object.getJSONObject("value");
            joke = value.getString("joke");
            if (joke.isEmpty() || joke == null) {
                joke = mainActivity.getString(R.string.server_no_response);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return joke;
    }
}
