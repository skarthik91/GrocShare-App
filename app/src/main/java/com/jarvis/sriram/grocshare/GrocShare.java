package com.jarvis.sriram.grocshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;

public class GrocShare extends AppCompatActivity {
    TextView numberView;

    public static HashMap<String,Double> Menulist = new HashMap<String,Double>();
    public static HashMap<String,Double> OrderList = new HashMap<String,Double>();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groc_share);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d("CREATION","oncreate is being executed");
        //Added Code 2

        Menulist.put("Curd",4.99);
        Menulist.put("Chapati",4.99);
        Menulist.put("Bread",1.49);
        Menulist.put("Milk",2.89);
        Menulist.put("Noodles",1.99);
        Menulist.put("Schezwan Sauce",2.99);
        Menulist.put("Vinegar",1.99);
        Menulist.put("Paneer",4.99);
        Menulist.put("Mint Chutney",5.99);
        Menulist.put("Rice",8.99);

        //Added Code 1


        numberView = (TextView)findViewById(R.id.textView);
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberpicker);
        numberPicker.setMaxValue(100);      // #1
        numberPicker.setMinValue(1);        // #2
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setOnValueChangedListener( new NumberPicker.
                OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int
                    oldVal, int newVal) {
              //  numberView.setText("Selected number is "+
              //          newVal);
            }
        });

        ///Added Code ends



     /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

 /*   @Overide
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_groc_share, menu);
        return true;
    }
*/

    public void addItem(View view){
        int qty;
        String menuitem;
        double itemcost;
        Spinner itemSelected = (Spinner)findViewById(R.id.spinner);
        NumberPicker qtyselected = (NumberPicker) findViewById(R.id.numberpicker);

        menuitem = itemSelected.getSelectedItem().toString();
        qty = Integer.valueOf(qtyselected.getValue());
        itemcost = findcost(menuitem,qty);



        if(OrderList.containsKey(menuitem)){
            double tempcost = OrderList.get(menuitem);
            OrderList.put(menuitem,tempcost+itemcost);
        }else{
            OrderList.put(menuitem,itemcost);
        }

        //Log.d("VALUES",menuitem);
        //Log.d("VALUES", String.valueOf(qty));
        //Log.d("VALUES", String.valueOf(itemcost));

        StringBuilder builder = new StringBuilder();

        builder.append(menuitem).append(" ");
        builder.append(qty).append(" ");
        builder.append(itemcost);

        numberView.setText(builder.toString());

    }


    public void submitOrder(View view){
        /*double total = 0;
        StringBuilder builder = new StringBuilder();
        for(String key : OrderList.keySet()){
            double val = OrderList.get(key);
            total += val;
            double unitprice = Menulist.get(key);
            int units =  (int)(val/unitprice);
            builder.append(key).append("  ");
            builder.append(units).append("  ");
            builder.append(val).append("  ");
            builder.append("\n");
        }

        builder.append("\n");
        builder.append("Total ").append(total);

        numberView.setText(builder.toString());*/


        Intent intent = new Intent(this,OrderActivity.class);
        intent.putExtra("map",OrderList);
        startActivity(intent);
    }

    /*@Override
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
    }*/


    public static double findcost(String menuitem , int qty){
        double itemcost;
        itemcost = Menulist.get(menuitem) * qty;
        return itemcost;
    }
}
