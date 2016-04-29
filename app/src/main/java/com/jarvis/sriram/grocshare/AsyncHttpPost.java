package com.jarvis.sriram.grocshare;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KarthikSwaminathan on 4/26/16.
 */
public class AsyncHttpPost extends AsyncTask<String, String, String> {
    interface Listener {
        void onResult(String result);
    }
    private Listener mListener;
  String mData = null;// post data

    /**
     * constructor
     */
    public AsyncHttpPost(String data) {
        mData = data;
    }
    public void setListener(Listener listener) {
        mListener = listener;
    }

    /**
     * background
     */
    @Override
    protected String doInBackground(String... params) {
        byte[] result = null;
        String str = "";
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(params[0]);// in this case, params[0] is URL
        try {
            // set up post data
            List nameValuePairs = new ArrayList(1);
            nameValuePairs.add(new BasicNameValuePair("idToken", mData));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            post.setHeader("Content-Type", "text/plain");
            post.setHeader("Accept-Encoding", "gzip");
            post.setHeader("Content-Type","utf-8");



            //post.setEntity(new UrlEncodedFormEntity(mData, "UTF-8"));
            HttpResponse response = client.execute(post);

            //System.out.println("Encoding "+ response.getEntity().getContentEncoding());
            String responseBody = EntityUtils.toString(response.getEntity());
            System.out.println("Body is  : "+responseBody);
            System.out.println("Response body"+response.getEntity().getContentLength());
            System.out.println("Response Code :"+response.getStatusLine().getStatusCode());
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpURLConnection.HTTP_OK){
                result = EntityUtils.toByteArray(response.getEntity());
                str = new String(result, "UTF-8");

            }

            //System.out.println("Response Status:" + statusLine);
           // System.out.println("Response body"+response.getEntity().getContentLength());
            InputStream is = response.getEntity().getContent();

            String line="";
            String data="";
            try{
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                while((line=br.readLine())!=null){

                    data+=line;
                }

                System.out.println("Body:  "+data);
                br.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }





        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
        }
        return str;
    }

    /**
     * on getting result
     */
    @Override
    protected void onPostExecute(String result) {
        // something...

        //HttpGet get = new HttpGet();
        //get.execute("http://grocshare-0408.appspot.com/auth");

        /*if (mListener != null) {
            mListener.onResult(result);
        }*/
    }
}


