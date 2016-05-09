package com.jarvis.sriram.grocshare;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HistoryActivity extends Activity implements onDownload {
    RecyclerView recList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.table);
        //setContentView(R.layout.activity_history);
         /*recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);*/


        String userID = getIntent().getExtras().getString("userID");
        String URL = "http://grocshare-0408.appspot.com/history/" + userID;
        RequestTask request = new RequestTask(this);
        request.execute(URL);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDownload(String values) {


        if(values==null) return;
        int id =0;

        //System.out.println("JSON object in history activity is     "+values);
        JSONObject json = null;
        JSONArray arr = null;
        try {
            json = new JSONObject(values);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            arr = json.getJSONArray("orders");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        /*ContactAdapter ca = new ContactAdapter(arr);
        recList.setAdapter(ca);*/

        TableLayout t1;

        TableLayout tl = (TableLayout) findViewById(R.id.main_table);

        /*{
            "userid": "105612544511245083899",
                "orders": [
            {
                "orderid": 1,
                    "items": [
                {
                    "item": "Bread",
                        "cost": 2.98,
                        "qty": 2
                }
                ],
                "status": 1
            }
            ]
        }
        Sriram SV*/

        for (int i = 0; i < arr.length(); i++) {
            JSONObject orderID = null;
            try {
                orderID = arr.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            TableRow tr_head = new TableRow(this);

            tr_head.setBackgroundColor(Color.GRAY);        // part1
            tr_head.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));


            TextView label_hello = new TextView(this);

            try {
                label_hello.setText("Order ID :" + orderID.getString("orderid"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            label_hello.setTextColor(Color.WHITE);          // part2
            label_hello.setPadding(5, 5, 5, 5);
            label_hello.setId(id++);
            tr_head.addView(label_hello);// add the column to the table row here

            TextView label_android = new TextView(this);    // part3
            label_android.setId(id++);// define id that must be unique

            TextView total = new TextView(this);    // part3
            total.setId(id++);// define id that must be unique
            try {
                label_android.setText("Status :" + orderID.getString("status"));
                total.setText("Total :" + orderID.getString("total"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            label_android.setTextColor(Color.WHITE); // set the color
            label_android.setPadding(5, 5, 5, 5); // set the padding (if required)
            tr_head.addView(label_android); // add the column to the table row here

            total.setTextColor(Color.WHITE); // set the color
            total.setPadding(5, 5, 5, 5); // set the padding (if required)
            tr_head.addView(total); // add the column to the table row here

            tl.addView(tr_head, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,                    //part4
                    TableRow.LayoutParams.WRAP_CONTENT));

            JSONArray items = null;

            try {
                items = orderID.getJSONArray("items");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            for (int j = 0; j < items.length(); j++) {

                JSONObject itemObj = null;


                try {
                    itemObj = items.getJSONObject(j);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                TableRow itemRow = new TableRow(this);

                itemRow.setBackgroundColor(Color.GRAY);        // part1
                itemRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                TextView itemText = new TextView(this);

                try {
                    itemText.setText("Item :"+itemObj.getString("item")+" ");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                itemText.setTextColor(Color.WHITE);
                itemText.setId(id++);// part2
                itemText.setPadding(5, 5, 5, 5);
                itemRow.addView(itemText);// add the column to the table row here

                TextView cost = new TextView(this);    // part3
                //label_android.setId(21);// define id that must be unique
                try {
                    cost.setText("Cost :"+itemObj.getString("cost")+" ");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cost.setTextColor(Color.WHITE); // set the color
                cost.setId(id++);
                cost.setPadding(5, 5, 5, 5); // set the padding (if required)
                itemRow.addView(cost); // add the column to the table row here

                TextView qty = new TextView(this);

                try {
                    qty.setText("Qty :"+itemObj.getString("qty"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                qty.setTextColor(Color.WHITE);
                qty.setId(id++);// part2
                qty.setPadding(5, 5, 5, 5);
                itemRow.addView(qty);// add the column to the table row here


                tl.addView(itemRow, new TableLayout.LayoutParams(
                        TableRow.LayoutParams.FILL_PARENT,                    //part4
                        TableRow.LayoutParams.WRAP_CONTENT));


                TableRow empty = new TableRow(this);
                empty.setId(id++);

                empty.setBackgroundColor(Color.BLUE);        // part1
                empty.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));



            }

            View line = new View(this);
            line.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, 10));
            line.setBackgroundColor(Color.rgb(51, 51, 51));
            tl.addView(line);


        }
    }


}