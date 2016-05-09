package com.jarvis.sriram.grocshare;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Order_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //static
    static HashMap<String, Double> orderList = new HashMap<String, Double>();
    static HashMap<String, Double> menuList = new HashMap<String, Double>();
    static HashMap<String, OrderItems> ObjectList = new HashMap<String, OrderItems>();
    ArrayList<String> orderitems = new ArrayList<String>();
    //String[] orderitems2 = null;
    public static double total = 0;

    String userID = "";
    String personName = "";
    String emailID = "";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.nav_text);
        nav_user.setText(personName);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


        Intent intent = getIntent();
        orderList = (HashMap<String, Double>) intent.getSerializableExtra("map");
        menuList = (HashMap<String, Double>) intent.getSerializableExtra("map1");

        userID = getIntent().getExtras().getString("userID");
        personName = getIntent().getExtras().getString("Name");
        emailID = getIntent().getExtras().getString("emailID");

        Log.i("TAG", "User id is    " + userID);
        Log.i("TAG", "Name  is    " + personName);
        Log.i("TAG", "Email id is    " + emailID);


        StringBuilder SB = new StringBuilder();
        for (String key : orderList.keySet()) {
            //Toast.makeText(this, key, Toast.LENGTH_LONG).show();

            StringBuilder bldr = new StringBuilder();
            double val = orderList.get(key);
            double unitprice = menuList.get(key);
            int quant = (int)(val/unitprice);
            //total += val;
            bldr.append(key).append(" ").append("*").append(" ").append(quant).append(" ").append("=").append(" ").append(val);
            orderitems.add(bldr.toString());
        }
        total = findtotal();
        SB.append("Total  ").append(total);
        orderitems.add(SB.toString());

        // orderitems2 = new String[orderitems.size()];
        //orderitems2 = orderitems.toArray(orderitems2);

        final ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, orderitems);
        final ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        //Edited code 6th May

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(Order_activity.this);
                final int positionToRemove = position;
                final String item = String.valueOf(parent.getItemAtPosition(positionToRemove));
                final String[] itemarr = item.split(" ");
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + itemarr[0]);
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        orderitems.remove(positionToRemove);
                        orderList.remove(itemarr[0]);
                        total = findtotal();
                        Log.i("Total", "Total is    " + total);
                        StringBuilder sb = new StringBuilder();
                        sb.append("Total").append(" ").append(total);
                        orderitems.set(orderitems.size() -1 ,sb.toString());
                        adapter.notifyDataSetChanged();

                    }});
                adb.show();
            }
        });


    }

    private double findtotal() {
        double temp = 0 ;
        for(String key : orderList.keySet()){
            temp += orderList.get(key);
        }
        return temp;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_signOut) {
            // Handle the camera action
        } else if (id == R.id.nav_orderHistory) {
            Intent i = new Intent(this,HistoryActivity.class);
            i.putExtra("userID", userID);
            startActivity(i);


        } else if (id == R.id.nav_squareCash) {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.squareup.cash");
            startActivity(launchIntent);

        } else if (id == R.id.nav_splitwise) {

            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.Splitwise.SplitwiseMobile");
            startActivity(launchIntent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void sendDataToCloud(View v) throws JSONException {


        for (String itemname : orderList.keySet()) {
            double itemcost, itemquantity;
            itemcost = orderList.get(itemname);
            itemquantity = itemcost / menuList.get(itemname);
            Log.i("Quantity", String.valueOf(itemquantity));
            OrderItems obj = new OrderItems(itemname, itemquantity, itemcost);
            ObjectList.put(itemname, obj);

        }

        Toast.makeText(getApplicationContext(),"Order Placed Successfully",Toast.LENGTH_LONG).show();

        JSONObject json = new JSONObject();

        JSONArray jsonarr = new JSONArray();
        for (String key : ObjectList.keySet()) {
            OrderItems value = ObjectList.get(key);

            JSONObject intobj = new JSONObject();

            intobj.put("item", value.item);
            intobj.put("qty", value.qty);
            intobj.put("cost", value.cost);

            jsonarr.put(intobj);

        }

        json.put("userID", userID);
        json.put("items", jsonarr);
        json.put("total", total);

        String order = json.toString();

        AsyncHttpPost asyncHttpPost = new AsyncHttpPost(order);
        asyncHttpPost.setListener(new AsyncHttpPost.Listener()

        {
            @Override
            public void onResult(String result) {
                // do something, using return value from network
            }
        });
        asyncHttpPost.execute("http://grocshare-0408.appspot.com/addorder");
        //asyncHttpPost.execute("http://requestb.in/1leji8q1");

        Log.d("Order", json.toString());

        Intent back = new Intent(this,GrocShare.class);
        back.putExtra("flush",1);
        back.putExtra("userID",userID);
        back.putExtra("name",personName);
        back.putExtra("emailID",emailID);
        startActivity(back);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Order_activity Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.jarvis.sriram.grocshare/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Order_activity Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.jarvis.sriram.grocshare/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
