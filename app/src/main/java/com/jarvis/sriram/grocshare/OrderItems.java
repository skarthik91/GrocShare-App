package com.jarvis.sriram.grocshare;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by KarthikSwaminathan on 4/30/16.
 */
public class OrderItems implements Parcelable {

    String item;
    double qty;
    double cost;

    OrderItems(String item, Double qty, Double cost){
        this.item = item;
        this.qty = qty;
        this.cost = cost;
    }


    protected OrderItems(Parcel in) {
        item = in.readString();
        qty = in.readDouble();
        cost = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(item);
        dest.writeDouble(qty);
        dest.writeDouble(cost);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<OrderItems> CREATOR = new Parcelable.Creator<OrderItems>() {
        @Override
        public OrderItems createFromParcel(Parcel in) {
            return new OrderItems(in);
        }

        @Override
        public OrderItems[] newArray(int size) {
            return new OrderItems[size];
        }
    };
}
