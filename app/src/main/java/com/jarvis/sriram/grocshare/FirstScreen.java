package com.jarvis.sriram.grocshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;

public class FirstScreen extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, ResultCallback<People.LoadPeopleResult> {

    GoogleApiClient mGoogleApiClient;
    boolean mSignInClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buidNewGoogleApiClient();
        setBtnClickListeners();


    }

    private void buidNewGoogleApiClient(){

        mGoogleApiClient =  new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API,Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
    }

    private void setBtnClickListeners(){
        // Button listeners

        findViewById(R.id.sign_out_button).setOnClickListener(this);
        //findViewById(R.id.disconnect_button).setOnClickListener(this);
    }

    private void gPlusSignOut() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            //changeUI(false);
            Intent i= new Intent(this,LoginActivity.class);
            startActivity(i);
        }
    }

    /*
     Revoking access from Google+ account
     */

    private void gPlusRevokeAccess() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            Log.d("MainActivity", "User access revoked!");
                            buidNewGoogleApiClient();
                            mGoogleApiClient.connect();

                            //changeUI(false);
                        }

                    });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.sign_out_button:
                Toast.makeText(this, "Sign Out from G+", Toast.LENGTH_LONG).show();
                gPlusSignOut();

                break;
            /*case R.id.disconnect_button:
                Toast.makeText(this, "Revoke Access from G+", Toast.LENGTH_LONG).show();
                gPlusRevokeAccess();
                break;*/
        }
    }


    @Override
    public void onConnected(Bundle arg0) {
        // TODO Auto-generated method stub
        mSignInClicked = false;

        // updateUI(true);
        Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(
                this);
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        // TODO Auto-generated method stub
        mGoogleApiClient.connect();
        // updateUI(false);
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub

    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public void onResult(People.LoadPeopleResult loadPeopleResult) {

    }
}