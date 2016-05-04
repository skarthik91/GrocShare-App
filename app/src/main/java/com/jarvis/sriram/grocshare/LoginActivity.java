package com.jarvis.sriram.grocshare;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.BufferedReader;

//import com.jarvis.sriram.grocshare.R;

/**
 * Demonstrates retrieving an ID token for the current Google user.
 */
public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private static final int RC_GET_TOKEN = 9002;

    private GoogleApiClient mGoogleApiClient;
    private TextView mIdTokenTextView;
    private TextView content;
    String userID="";
    String emailID="";
    String personName="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Views
        mIdTokenTextView = (TextView) findViewById(R.id.detail);
        content = (TextView) findViewById(R.id.detail2);

        // Button click listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.disconnect_button).setOnClickListener(this);

        // For sample only: make sure there is a valid server client ID.
        validateServerClientID();

        // [START configure_signin]
        // Request only the user's ID token, which can be used to identify the
        // user securely to your backend. This will contain the user's basic
        // profile (name, profile picture URL, etc) so you should not need to
        // make an additional call to personalize your application.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        // [END configure_signin]

        // Build GoogleAPIClient with the Google Sign-In API and the above options.
        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(AppIndex.API).build();
    }

    private void getIdToken() {
        // Show an account picker to let the user choose a Google account from the device.
        // If the GoogleSignInOptions only asks for IDToken and/or profile and/or email then no
        // consent screen will be shown here.
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_GET_TOKEN);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Log.d(TAG, "signOut:onResult:" + status);
                        updateUI(false);
                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Log.d(TAG, "revokeAccess:onResult:" + status);
                        updateUI(false);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        BufferedReader reader = null;
        String text = null;

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_GET_TOKEN) {
            // [START get_id_token]
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d(TAG, "onActivityResult:GET_TOKEN:success:" + result.getStatus().isSuccess());


            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                String idToken = acct.getIdToken();

                 personName = acct.getDisplayName();
                 System.out.println("PErson name is "+ personName);
                 emailID = acct.getEmail();
                 userID = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();


                // Show signed-in UI.
                Log.d(TAG, "idToken:" + idToken);
                //mIdTokenTextView.setText(getString(R.string.id_token_fmt, idToken));
                updateUI(true);

                Log.i(TAG, "Sending Token");

                // TODO(user): send token to server and validate server-side

                /*try
                {

                    // Defined URL  where to send data
                    URL url = new URL("http://httpbin.org/post");

                    // Send POST data request

                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");

                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(idToken);
                    wr.flush();

                    Log.i(TAG, "Response Code:" + Integer.toString(conn.getResponseCode()));
                    Log.i(TAG, "Response Message:"+ conn.getResponseMessage());
                    // Get the server response

                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = null;



                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        // Append server response in string
                        sb.append(line + "\n");
                    }



                    text = sb.toString();
                    Log.i(TAG, "Signed in as: " + text);
                }
                catch(Exception ex)
                {
                Log.i(TAG,"Exception in Sending");
                }
                finally
                {
                    try
                    {

                        reader.close();
                    }

                    catch(Exception ex) {}
                }

                // Show response on activity
                content.setText( text  );
                Log.i(TAG, "Sent Brooo");
            }*/

                /*HttpClient httpClient = new DefaultHttpClient();
HttpPost httpPost = new HttpPost("https://yourbackend.example.com/tokensignin");

try {
    List nameValuePairs = new ArrayList(1);
    nameValuePairs.add(new BasicNameValuePair("idToken", idToken));
    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

    HttpResponse response = httpClient.execute(httpPost);
    int statusCode = response.getStatusLine().getStatusCode();
    final String responseBody = EntityUtils.toString(response.getEntity());
    Log.i(TAG, "Signed in as: " + responseBody);
} catch (ClientProtocolException e) {
    Log.e(TAG, "Error sending ID token to backend.", e);
} catch (IOException e) {
    Log.e(TAG, "Error sending ID token to backend.", e);
}}
*/

            register(idToken);


               // new PostClass(this,idToken).execute();

                /*AsyncHttpPost asyncHttpPost = new AsyncHttpPost(idToken);
                asyncHttpPost.setListener(new AsyncHttpPost.Listener()

                 {
                    @Override
                    public void onResult(String result) {
                        // do something, using return value from network
                    }
                });
                //asyncHttpPost.execute("http://grocshare-0408.appspot.com/auth");
                //asyncHttpPost.execute("http://requestb.in/1bsumeu1");
*/


                Intent i= new Intent(this,GrocShare.class);

                i.putExtra("userID", userID);
                i.putExtra("emailID", emailID);
                i.putExtra("Name",personName);
                i.putExtra("Photo",personPhoto);
                startActivity(i);


            } else {
                // Show signed-out UI.
                updateUI(false);
            }
            // [END get_id_token]
        }

    }






    private void register(final String parameter) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {


                String result = ruc.sendPostRequest("http://grocshare-0408.appspot.com/auth",parameter);
                Log.i(TAG, "Result is :"+result);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(parameter);
    }
/*
    private class PostClass extends AsyncTask<String, Void, Void> {

        private final Context context;
        String idToken="";
        public PostClass(Context c,String token){
            this.context = c;
            idToken=token;
        }

        protected void onPreExecute(){
            //progress= new ProgressDialog(this.context);
           // progress.setMessage("Loading");
            //progress.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {

                //final TextView outputView = (TextView) findViewById(R.id.showOutput);
                URL url = new URL("http://grocshare-0408.appspot.com/auth");

                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                String urlParameters = idToken;
                connection.setRequestMethod("POST");
                //connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                //connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
               // connection.setHeader('Accept-Encoding', 'gzip');
                connection.setDoOutput(true);
                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(urlParameters);
                dStream.flush();
                dStream.close();
                int responseCode = connection.getResponseCode();

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
                output.append(System.getProperty("line.separator")  + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator")  + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                InputStream in = connection.getInputStream();
                String encoding = connection.getContentEncoding();
                System.out.println("Encoding : "+encoding);
                encoding = encoding == null ? "UTF-8" : encoding;
                //String body = IOUtils.toString(in, encoding);
                //System.out.println(body);
                String line = "";
                StringBuilder responseOutput = new StringBuilder();

                //System.out.println("output===============" + );
                while((line = br.readLine()) != null ) {
                    responseOutput.append(line);
                }
               // br.close();
                in.close();
                int len = connection.getContentLength();
                Log.d(TAG, "Content Length:  "+ Integer.toString(len));
                System.out.println("Response:   "+ responseOutput.toString());
               // output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString()+"k");
                //Log.i(TAG, "Output before : " + output);
                LoginActivity.this.runOnUiThread(new Runnable() {

                                                     @Override
                                                     public void run() {
                                                         //outputView.setText(output);
                                                         //progress.dismiss();
                                                         //Log.i(TAG, "Output: " + output);
                                                     }
                                                 }


                );

                //Log.i(TAG, "Output after : " + output);

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }*/

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            ((TextView) findViewById(R.id.status)).setText(R.string.signed_in);

            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            ((TextView) findViewById(R.id.status)).setText(R.string.signed_out);
            mIdTokenTextView.setText(getString(R.string.id_token_fmt, "null"));

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    /**
     * Validates that there is a reasonable server client ID in strings.xml, this is only needed
     * to make sure users of this sample follow the README.
     */
    private void validateServerClientID() {
        String serverClientId = getString(R.string.server_client_id);
        String suffix = ".apps.googleusercontent.com";
        if (!serverClientId.trim().endsWith(suffix)) {
            String message = "Invalid server client ID in strings.xml, must end with " + suffix;

            Log.w(TAG, message);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                getIdToken();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.disconnect_button:
                revokeAccess();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.jarvis.sriram.grocshare/http/host/path")
        );
        AppIndex.AppIndexApi.start(mGoogleApiClient, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("http://httpbin.org/post")
        );
        AppIndex.AppIndexApi.end(mGoogleApiClient, viewAction);
        mGoogleApiClient.disconnect();
    }
}