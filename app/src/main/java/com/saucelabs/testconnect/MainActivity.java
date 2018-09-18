package com.saucelabs.testconnect;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        urlField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                if (keyEvent != null && actionId == KeyEvent.KEYCODE_ENTER
                        || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {

                    if (urlField.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), getString(R.string.message_enter_url), Toast.LENGTH_SHORT).show();
                    } else {
                        urlString = urlField.getText().toString();
                        new EstablishConnectionTask().execute(urlString);
                    }

                    return true;
                }

                return false;
            }
        });

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlString = urlField.getText().toString();
                new EstablishConnectionTask().execute(urlString);
            }
        });

    }

    private class EstablishConnectionTask extends AsyncTask<String, Void, Boolean> {

        private Exception exception;

        @Override
        protected Boolean doInBackground(String... urls) {

            boolean success;

            URL url;
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

