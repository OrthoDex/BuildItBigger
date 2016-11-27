package com.example.randomjokes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        String joke = getIntent().getStringExtra("joke");
        TextView jokeTextView = (TextView) findViewById(R.id.joke_content);
        jokeTextView.setText(joke);
    }
}
