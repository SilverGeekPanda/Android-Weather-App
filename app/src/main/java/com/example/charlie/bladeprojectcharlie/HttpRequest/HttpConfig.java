package com.example.charlie.bladeprojectcharlie.HttpRequest;

import android.util.Log;

import com.android.volley.RequestQueue;

public class HttpConfig {

    //Attributs
    private RequestQueue queue;
    private String url;
    private String response;

    //Methods

    public HttpConfig(String url) {
        this.url = url;
        this.queue = null;
        this.response = null;
    }


    public void addParameter(String... p) {
        for(int i=0 ; i<p.length ; ++i) {
            this.url += p[i];
        }
        Log.d("Debug1","URL=" + this.url);
    }

    public RequestQueue getQueue() {
        return queue;
    }

    public void setQueue(RequestQueue queue) {
        this.queue = queue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
