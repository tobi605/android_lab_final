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

import java.io.File;
import java.util.ArrayList;

class SavedListAdapter extends ArrayAdapter<String> {

    private ArrayList<String> list;
    private int container;

    SavedListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        list = objects;
        container = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(container, parent, false);
        }
        final String[] current = getItem(position).split(";");
        final TextView date = convertView.findViewById(R.id.item_date);
        TextView amount = convertView.findViewById(R.id.item_amount);
        Button deleteButton = convertView.findViewById(R.id.item_delete);
        date.setText(current[0].substring(current[0].length() - 24, current[0].length() - 5));
        amount.setText("Liczba produktów: " + current[1]);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String listFilePath = current[0];
                File listFile = new File(listFilePath);
                String folder = listFile.getParentFile().getAbsolutePath();
                listFile.delete();
                for (int i = 5; i < current.length; i += 4) {
                    String fotoPath = current[i];
                    if (!fotoPath.equals("none")) {
                        new File(folder + "/" + current[i]).delete();
                    }
                }
                list.remove(list.get(position));
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
