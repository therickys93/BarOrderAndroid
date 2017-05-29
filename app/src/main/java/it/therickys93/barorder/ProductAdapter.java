package it.therickys93.barorder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import it.therickys93.javabarorderapi.Product;

public class ProductAdapter extends BaseAdapter {

    private Activity activity;
    private List<Product> products;
    private static LayoutInflater inflater=null;

    public ProductAdapter(Activity a, List<Product> prodotti) {
        this.activity = a;
        this.products = prodotti;
        this.inflater = (LayoutInflater)this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        if(products == null || products.size() == 0){
            return 1;
        }
        return products.size();
    }

    public Product getItem(int position) {
        return products.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View vi = view;
        if(view == null) {
            System.out.println("view == null");
            vi = this.inflater.inflate(R.layout.product_layout, null);
        }
        TextView id = (TextView)vi.findViewById(R.id.productName);
        TextView details = (TextView)vi.findViewById(R.id.productCount);
        if(products == null || products.size() == 0){
            id.setText("No Products Found");
            details.setText("");
        } else {
            Product product = products.get(position);
            id.setText(product.name());
            details.setText("" + product.quantity());
        }
        return vi;
    }
}
