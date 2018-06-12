package com.polibuda.gimbus.android_lab_final;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

class ShoppingListAdapter extends ArrayAdapter<Product> {
    private int container;
    private List<Product> products;

    ShoppingListAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        container = resource;
        products = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Product current = products.get(position);
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(container, parent, false);
        }
        TextView name = convertView.findViewById(R.id.shopping_item_name);
        TextView amount = convertView.findViewById(R.id.shopping_item_amount);
        TextView unit = convertView.findViewById(R.id.shopping_item_unit);
        name.setText(current.getName());
        amount.setText(String.valueOf(current.getAmount()));
        unit.setText(current.getUnit());
        return convertView;
    }
}
