package com.jarvis.sriram.grocshare;

/**
 * Created by KarthikSwaminathan on 5/4/16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    Context context;
    private List<ContactInfo> contactList;
    JSONArray jsonArr;
    protected TableLayout tableLayout;

    public ContactAdapter(JSONArray jsonArr) {
        this.jsonArr = jsonArr;
    }


    @Override
    public int getItemCount() {
        return jsonArr.length();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {


        TableRow tr_head = new TableRow(context);




    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);


        return new ContactViewHolder(itemView);
    }

    public  class ContactViewHolder extends RecyclerView.ViewHolder {

        //protected TextView orderID;

        protected TextView cost;


        public ContactViewHolder(View v)  {
            super(v);
            //orderID =  (TextView) v.findViewById(R.id.orderID);

            TableLayout t1;



            tableLayout = (TableLayout) v.findViewById(R.id.main_table);



            for(int j =0;j<jsonArr.length();j++)
            {tableLayout = (TableLayout)v.findViewById(R.id.tableLayout);
                JSONObject b= null;
                try {
                    b = jsonArr.getJSONObject(j);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray arr=null;
                try {
                     arr = b.getJSONArray("items");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                TableRow row =  (TableRow)v.findViewById(R.id.tableRow1);


                for(int i=0;i<arr.length();i++) {

                    JSONObject item = null;
                    try {
                        item = arr.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Inflate your row "template" and fill out the fields.

                    try {
                        ((TextView) row.findViewById(R.id.textView1)).setText(item.getString("item"));
                        ((TextView) row.findViewById(R.id.textView2)).setText(Integer.toString(item.getInt("cost")));
                        ((TextView) row.findViewById(R.id.textView3)).setText(Integer.toString(item.getInt("qty")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                //tableLayout.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }




        }
    }
}