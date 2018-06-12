package com.polibuda.gimbus.android_lab_final;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ProductListAdapter extends ArrayAdapter<Product> {
    private ArrayList<Product> productList;
    private String savePath;
    private int container;

    ProductListAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects, String savePath) {
        super(context, resource, objects);
        this.productList = (ArrayList<Product>) objects;
        container = resource;
        this.savePath = savePath;
    }

    public void addItem(String name, String amount, String unit){
        Product product = new Product(name, amount, unit);
        this.productList.add(product);
        notifyDataSetChanged();
    }

    public void addItem(String name, String amount, String unit, String imagePath){
        Product product = new Product(name, amount, unit, imagePath);
        this.productList.add(product);
        notifyDataSetChanged();
    }

    private void removeItem(int position){
        this.productList.remove(this.productList.get(position));
        notifyDataSetChanged();
    }

    public void saveToFile(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.savePath+"/"+fileName+".list"));
        writer.write(String.valueOf(productList.size()));
        writer.write(";");
        for (Product p : this.productList) {
            writer.write(p.toString());
        }
        writer.flush();
        writer.close();
        productList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(container, parent, false);
        }
        Product current = getItem(position);
        TextView name = convertView.findViewById(R.id.create_list_item_name);
        TextView amount = convertView.findViewById(R.id.create_list_item_amount);
        TextView unit = convertView.findViewById(R.id.create_list_item_unit);
        Button deleteButton = convertView.findViewById(R.id.create_list_item_delete);
        name.setText(current.getName());
        amount.setText(String.format("%.2f",current.getAmount()));
        unit.setText(current.getUnit());
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
            }
        });
        return convertView;
    }
}
