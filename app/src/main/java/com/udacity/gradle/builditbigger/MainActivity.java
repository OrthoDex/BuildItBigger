package com.udacity.gradle.builditbigger;

import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    ProgressBar spinner;
    private final String FRAGMENT_TAG = "mainactivityfragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        if (!Utility.isNetWorkAvailable(this)){
            Log.v(MainActivity.class.getSimpleName(),"No Network Detected");
            Toast.makeText(this, getString(R.string.net_error), Toast.LENGTH_LONG).show();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, new MainActivityFragment(), FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getJokeFromServer() {
        if (Utility.isNetWorkAvailable(this)) {
            spinner.setVisibility(View.VISIBLE);
            new EndpointsAsyncTask(this).execute(this);
        } else {
            Toast.makeText(this, getString(R.string.net_error), Toast.LENGTH_LONG).show();
        }
    }
}
