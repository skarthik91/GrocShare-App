package com.jarvis.sriram.grocshare;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by KarthikSwaminathan on 4/26/16.
 */
public class HttpGet extends AsyncTask<String, String, String> {
    interface Listener {
        void onResult(String result);
    }

    private Listener mListener;
    String mData = null;// post data

    /**
     * constructor
     */

    /**
     * background
     */
    @Override
    protected String doInBackground(String... params) {
        byte[] result = null;
        String str = "";
        HttpClient client = new DefaultHttpClient();
        HttpPost get = new HttpPost(params[0]);// in this case, params[0] is URL
        try {
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                InputStream inputstream = entity.getContent();
                BufferedReader bufferedreader =
                        new BufferedReader(new InputStreamReader(inputstream));
                StringBuilder stringbuilder = new StringBuilder();

                String currentline = null;
                while ((currentline = bufferedreader.readLine()) != null) {
                    stringbuilder.append(currentline + "\n");
                }
                str= stringbuilder.toString();
                Log.v("HTTP REQUEST: +", str);
                System.out.println("Content Type "+ entity.getContentType());
                System.out.println("HTTP Request + "+ str);
                inputstream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return str;

    }
}


