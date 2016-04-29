package com.jarvis.sriram.grocshare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderActivity extends Activity {

   //static
    static HashMap<String,Double> orderList;

    //Where to send data

    public static  final String POST_URL = "http://grocshare-0408.appspot.com/";

    // How to send datal

    private static final String POST_OPTION_APPID = "appid";
    private static final String POST_OPTION_ITEMID = "itemid";
    private static final String POST_OPTOIN_DATA = "data";

    //What data to send

    public static final String APP_ID = "grocshare-0408";
    public static final String ITEM_ID = "42";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ArrayList<String> orderitems= new ArrayList<String>();

        Intent intent = getIntent();
        orderList = (HashMap<String, Double>)intent.getSerializableExtra("map");

        //Added Code
        double total = 0;
        StringBuilder SB = new StringBuilder();
        for(String key:orderList.keySet()){
            //Toast.makeText(this, key, Toast.LENGTH_LONG).show();

            StringBuilder bldr = new StringBuilder();
            double val = orderList.get(key);
            total += val;
            bldr.append(key).append("   ").append(val);
            orderitems.add(bldr.toString());
        }

        SB.append("Total  ").append(total);
        orderitems.add(SB.toString());

        String[] orderitems2 = new String[orderitems.size()];
        orderitems2 = orderitems.toArray(orderitems2);

        //listView = (ListView) findViewById(R.id.list);


        ListAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,orderitems2);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        //listView.setListAdapter(adapter);
        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();

            }

        });
        */
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


    }

    public void sendDataToCloud(View v) throws JSONException {

       // ListView lview = (ListView)findViewById(R.id.list);


        JSONObject json = new JSONObject();

        for(String key: orderList.keySet()){
            double value = orderList.get(key);
            json.put(key,value);

            Log.d("VALUES",key);
            Log.d("VALUES", String.valueOf(value));
            //Log.d("VALUES", String.valueOf(itemcost));
        }



    }


}
