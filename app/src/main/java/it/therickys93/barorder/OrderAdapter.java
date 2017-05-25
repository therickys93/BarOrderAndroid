package it.therickys93.barorder;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import it.therickys93.javabarorderapi.Order;

public class OrderAdapter extends BaseAdapter {

    private Activity activity;
    private List<Order> orders;
    private static LayoutInflater inflater=null;

    public OrderAdapter(Activity a, List<Order> data) {
        this.activity = a;
        this.orders = data;
        this.inflater = (LayoutInflater)this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return orders.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View vi = view;
        if(view == null) {
            System.out.println("view == null");
            vi = this.inflater.inflate(R.layout.row_layout, null);
        }
        TextView id = (TextView)vi.findViewById(R.id.orderID);
        TextView details = (TextView)vi.findViewById(R.id.orderDetails);
        Order order = orders.get(position);
        id.setText("#" + order.id());
        details.setText(order.prettyToString());
        return vi;
    }
}