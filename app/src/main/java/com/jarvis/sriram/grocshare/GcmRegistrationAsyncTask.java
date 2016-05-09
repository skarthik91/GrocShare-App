package com.jarvis.sriram.grocshare;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.karthikswaminathan.myapplication.backend.registration.Registration;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by KarthikSwaminathan on 5/5/16.
 */
class GcmRegistrationAsyncTask extends AsyncTask<String, Void, String> {
    private static Registration regService = null;
    private GoogleCloudMessaging gcm;
    private Context context;

    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = "752534414842";

    public GcmRegistrationAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        if (regService == null) {
            Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://groctestgcm.appspot.com/_ah/api/");
            // end of optional local run code


            /*
            Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // Need setRootUrl and setGoogleClientRequestInitializer only for local testing,
                    // otherwise they can be skipped
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                                throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end of optional local run code*/

            regService = builder.build();
        }




        String msg = "";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(context);
            }
            String regId = gcm.register(SENDER_ID);
            //msg = "Device registered, registration ID=" + regId;


            JSONObject json = new JSONObject();
            try {
                json.put("regID",regId);
                json.put("userID",params[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            msg=json.toString();
            // You should send the registration ID to your server over HTTP,
            // so it can use GCM/HTTP or CCS to send messages to your app.
            // The request to your server should be authenticated if your app
            // is using accounts.
            regService.register(regId).execute();

        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
        Toast.makeText(context, "Registered", Toast.LENGTH_LONG).show();
        //Logger.getLogger("REGISTRATION").log(Level.INFO, msg);
        AsyncHttpPost asyncHttpPost = new AsyncHttpPost(msg);
        asyncHttpPost.setListener(new AsyncHttpPost.Listener()

        {
            @Override
            public void onResult(String result) {
                // do something, using return value from network
            }
        });
        asyncHttpPost.execute("http://grocshare-0408.appspot.com/gcmregister");
        //asyncHttpPost.execute("http://requestb.in/1gessk31");
    }
}