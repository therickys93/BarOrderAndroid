package it.therickys93.barorder;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.therickys93.javabarorderapi.Order;

public class OrderAdapter extends BaseAdapter implements Filterable {

    private Activity activity;
    private List<Order> orders;
    private List<Order> temporaryOrders;
    private static LayoutInflater inflater=null;

    public OrderAdapter(Activity a, List<Order> data) {
        this.activity = a;
        this.orders = data;
        this.temporaryOrders = data;
        this.inflater = (LayoutInflater)this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    public int getCount() {
        if(temporaryOrders == null || temporaryOrders.size() == 0){
            return 1;
        }
        return temporaryOrders.size();
    }

    public Order getItem(int position) {
        return temporaryOrders.get(position);
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
        TextView price = (TextView)vi.findViewById(R.id.orderPrice);
        TextView details = (TextView)vi.findViewById(R.id.orderDetails);
        if(temporaryOrders == null || temporaryOrders.size() == 0){
            id.setText("Nessun Ordine Trovato");
            details.setText("");
            price.setText("");
        } else {
            Order order = temporaryOrders.get(position);
            id.setText("#" + order.id());
            details.setText(order.prettyToString());
            price.setText(order.price() + "€");
        }
        return vi;
    }

    @Override
    public Filter getFilter() {
        final Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                if(charSequence == null || charSequence.length() == 0){
                    results.count = orders.size();
                    results.values = orders;
                } else {
                    List<Order> filteredOrders = new ArrayList<>();
                    int table = Integer.parseInt(charSequence.toString());
                    for(Order o : orders){
                        if(table == o.table()){
                            filteredOrders.add(o);
                        }
                    }
                    results.count = filteredOrders.size();
                    results.values = filteredOrders;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                temporaryOrders = (List<Order>)filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
