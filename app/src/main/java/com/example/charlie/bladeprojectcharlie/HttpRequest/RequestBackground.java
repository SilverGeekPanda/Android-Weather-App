package com.example.charlie.bladeprojectcharlie.HttpRequest;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.VolleyError;

public class RequestBackground extends AsyncTask<HttpConfig,Void,Void> {

    @Override
    protected Void doInBackground(final HttpConfig... httpConfig) {

        RequestQueue queue = httpConfig[0].getQueue();
        String url = httpConfig[0].getUrl();

        Log.d("REQUEST1","Do In Background");
        Log.d("REQUEST1",httpConfig[0].getUrl());

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        httpConfig[0].setResponse(response);
                        Log.d("REQUEST1","Response: " + response);
                        Log.d("REQUEST1",httpConfig[0].getResponse());
                        // Display the first 500 characters of the response string.
                        // mTextView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
                Log.d("REQUEST1","Request did not work" );
            }
        });


        Log.d("REQUEST1","Adding to Queue...");
        Log.d("REQUEST1","RequestQueue"+stringRequest);
        queue.add(stringRequest);
        return null;
    }
}
