package com.jarvis.sriram.grocshare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderActivity extends Activity {

   //static
    static HashMap<String,Double> orderList = new HashMap<String,Double>() ;
    static HashMap<String,Double> menuList = new HashMap<String,Double>() ;
    static HashMap<String,OrderItems> ObjectList = new HashMap<String,OrderItems>();

    public static double total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ArrayList<String> orderitems= new ArrayList<String>();

        Intent intent = getIntent();
        orderList = (HashMap<String, Double>)intent.getSerializableExtra("map");
        menuList = (HashMap<String,Double>)intent.getSerializableExtra("map1");



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
        ListAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,orderitems2);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);

    }

    public void sendDataToCloud(View v) throws JSONException {


        for(String itemname:orderList.keySet()){
            double itemcost,itemquantity;
            itemcost = orderList.get(itemname);
            itemquantity = itemcost/menuList.get(itemname);
            Log.i("Quantity", String.valueOf(itemquantity));
            OrderItems obj = new OrderItems(itemname,itemquantity,itemcost);
            ObjectList.put(itemname, obj);
        }


        JSONObject json = new JSONObject();
        String Userid = "first";
        JSONArray jsonarr = new JSONArray();
        for(String key: ObjectList.keySet()){
            OrderItems value = ObjectList.get(key);

            JSONObject intobj = new JSONObject();

            intobj.put("item",value.item);
            intobj.put("qty", value.qty);
            intobj.put("cost",value.cost);

            jsonarr.put(intobj);

        }

        json.put("userid",Userid);
        json.put("items",jsonarr);
        json.put("total",total);

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

        Log.d("Order",json.toString());
    }


}
