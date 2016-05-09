package com.jarvis.sriram.grocshare;

/*
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;

public class GrocShare extends AppCompatActivity {

    //TextView numberView;

    //Navigation Drawer
    private DrawerLayout mDrawer;
    private Toolbar toolbar;

    public static HashMap<String,Double> Menulist = new HashMap<String,Double>();
    public static HashMap<String,Double> OrderList = new HashMap<String,Double>();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groc_share);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Navigation Drawer
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

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
        numberPicker.setOnValueChangedListener(new NumberPicker.
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
    //Navigation Drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                //fragmentClass = FirstFragment.class;
                Log.i("TAG","First Fragment Selected");
                break;
            case R.id.nav_second_fragment:
                //fragmentClass = SecondFragment.class;
                Log.i("TAG","Second Fragment Selected");
                break;
            case R.id.nav_third_fragment:
                //fragmentClass = ThirdFragment.class;
                Log.i("TAG","Third Fragment Selected");
                break;
            default:
               // fragmentClass = FirstFragment.class;
        }

        try {
            //fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        //FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    // Navigation drawer elements end

    public void submitOrder(View view){
        Intent intent = new Intent(this,Order_activity.class);
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
*/


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;

public class GrocShare extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView numberView;

    //Navigation Drawer
    private DrawerLayout mDrawer;
    private Toolbar toolbar;

    public static HashMap<String,Double> Menulist = new HashMap<String,Double>();
    public static HashMap<String,Double> OrderList = new HashMap<String,Double>();
    String userID = "";
    String personName="";
    String emailID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        userID= getIntent().getExtras().getString("userID");
        //Registering GCM
        GcmRegistrationAsyncTask task = new GcmRegistrationAsyncTask(this);
       task.execute(userID);
        personName= getIntent().getExtras().getString("Name");
        emailID=getIntent().getExtras().getString("emailID");

       Log.i("TAG","User id is    "+userID);
        Log.i("TAG","Name  is    "+personName);
        Log.i("TAG", "Email id is    " + emailID);




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

        //Navigation Drawer
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        Log.d("CREATION", "oncreate is being executed");

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
        Menulist.put("Bagel",1.89);
        Menulist.put("Pizza",5.99);



        numberView = (TextView)findViewById(R.id.textView);
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberpicker);
        numberPicker.setMaxValue(100);      // #1
        numberPicker.setMinValue(1);        // #2
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setOnValueChangedListener(new NumberPicker.
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
        getMenuInflater().inflate(R.menu.navigation, menu);
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


    public void submitOrder(View view){
        Intent intent = new Intent(this,Order_activity.class);
        intent.putExtra("map",OrderList);
        intent.putExtra("map1",Menulist);
        intent.putExtra("userID", userID);
        intent.putExtra("emailID", emailID);
        intent.putExtra("Name",personName);

        startActivity(intent);
    }

    public static double findcost(String menuitem , double qty){
        double itemcost;
        itemcost = Menulist.get(menuitem) * qty;
        return itemcost;
    }

    public void onResume() {
        super.onResume();
        Intent rxint = getIntent();
        userID= getIntent().getExtras().getString("userID");
        personName= getIntent().getExtras().getString("Name");
        emailID=getIntent().getExtras().getString("emailID");
        int intvalue = rxint.getIntExtra("flush", 0);
        //flushstatus = bundle.getString("flush");

            OrderList.clear();



    }
}
