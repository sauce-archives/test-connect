package com.saucelabs.testconnect;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private String urlString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button testButton = findViewById(R.id.testButton);
        final EditText urlField = findViewById(R.id.urlField);
        textView = findViewById(R.id.textView);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlString = urlField.getText().toString();
                new EstablishConnectionTask().execute(urlString);
            }
        });

    }

    private class EstablishConnectionTask extends AsyncTask<String, Void, Boolean> {

        Exception exception;

        @Override
        protected Boolean doInBackground(String... urls) {

            boolean success;

            URL url = null;
            try {
                url = new URL(urls[0]);
                URLConnection urlConnection = url.openConnection();
                urlConnection.getInputStream();
                success = true;
            } catch (IOException e) {
                e.printStackTrace();
                exception = e;
                success = false;
            }

            return success;

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                textView.setText("Connection to " + urlString + " was successful.");
            } else {
                Writer writer = new StringWriter();
                exception.printStackTrace(new PrintWriter(writer));
                String stacktrace = writer.toString();

                textView.setText("Connection to " + urlString + " failed: " + stacktrace);
            }
        }

    }

}

