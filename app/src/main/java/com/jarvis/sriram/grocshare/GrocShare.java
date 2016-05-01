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

        Menulist.put("Curd",4.99);
        Menulist.put("Chappati",4.99);
        Menulist.put("Bread",1.49);
        Menulist.put("Milk",2.89);
        Menulist.put("Noodles",1.99);
        Menulist.put("Schezwan Chutney",2.99);
        Menulist.put("Vinegar",1.99);
        Menulist.put("Paneer",4.99);
        Menulist.put("Mint Chutney",5.99);
        Menulist.put("Rice",8.99);



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
            }
        });
    }


    public void addItem(View view){
        double qty;
        String menuitem;
        double itemcost;
        Spinner itemSelected = (Spinner)findViewById(R.id.spinner);
        NumberPicker qtyselected = (NumberPicker) findViewById(R.id.numberpicker);

        menuitem = itemSelected.getSelectedItem().toString();
        qty = Integer.valueOf(qtyselected.getValue());
        itemcost = findcost(menuitem, qty);

        if(OrderList.containsKey(menuitem)){
            double tempcost = OrderList.get(menuitem);
            OrderList.put(menuitem,tempcost+itemcost);
        }else{
            OrderList.put(menuitem,itemcost);
        }

        StringBuilder builder = new StringBuilder();

        builder.append(menuitem).append(" ");
        builder.append(qty).append(" ");
        builder.append(itemcost);

        numberView.setText(builder.toString());

    }


    public void submitOrder(View view){
        Intent intent = new Intent(this,OrderActivity.class);
        intent.putExtra("map",OrderList);
        intent.putExtra("map1",Menulist);
        startActivity(intent);
    }

    public static double findcost(String menuitem , double qty){
        double itemcost;
        itemcost = Menulist.get(menuitem) * qty;
        return itemcost;
    }
}
