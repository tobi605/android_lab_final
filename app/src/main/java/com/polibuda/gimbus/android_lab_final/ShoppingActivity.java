package com.polibuda.gimbus.android_lab_final;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ShoppingActivity extends AppCompatActivity {
    private String listPath;
    private ArrayList<Product> products = new ArrayList<>();
    private ShoppingListAdapter adapter;
    private ListView list;
    private Button dismiss, unavailable, taken, showPhoto;
    private Product selected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        showPhoto = findViewById(R.id.shopping_show_foto_button);
        listChoiceDialog();
        initAdapter();
        initButtons();
    }

    private void initButtons() {
        dismiss = findViewById(R.id.shopping_cancel_button);
        unavailable = findViewById(R.id.shopping_unavailable_button);
        taken = findViewById(R.id.shopping_taken_button);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected!=null) {
                    products.remove(selected);
                    products.add(selected);
                    adapter.notifyDataSetChanged();
                    selected = null;
                    showPhoto.setVisibility(View.INVISIBLE);
                }
            }
        });
        unavailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected!=null) {
                    products.remove(selected);
                    adapter.notifyDataSetChanged();
                    selected = null;
                    showPhoto.setVisibility(View.INVISIBLE);
                    if(products.isEmpty()){
                        shoppingFinished();
                    }
                }
            }
        });
        taken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected!=null) {
                    products.remove(selected);
                    adapter.notifyDataSetChanged();
                    selected = null;
                    showPhoto.setVisibility(View.INVISIBLE);
                    if(products.isEmpty()){
                        shoppingFinished();
                    }
                }
            }
        });
        showPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected!=null){
                    showImageDialog();
                    selected = null;
                    showPhoto.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void shoppingFinished() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingActivity.this);
        builder.setTitle("Zakupy zakończone");
        builder.setNeutralButton("Wyjdź", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onBackPressed();
            }
        });
        builder.show();
    }

    private void showImageDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingActivity.this);
        LayoutInflater inflater = LayoutInflater.from(ShoppingActivity.this);
        final View view = inflater.inflate(R.layout.image_dialog, null);
        ((ImageView)view.findViewById(R.id.dialog_image)).setImageURI(null);
        ((ImageView)view.findViewById(R.id.dialog_image)).setImageURI(Uri.fromFile(new File(selected.getImagePath())));
        //image still not working, needs research
        builder.setView(view);
        builder.setTitle("Zdjęcie produktu");
        builder.setNeutralButton("Zamknij", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void initAdapter() {
        adapter = new ShoppingListAdapter(getApplicationContext(), R.layout.shopping_list_item, products);
        list = findViewById(R.id.shopping_list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = adapter.getItem(position);
                if(!selected.getImagePath().equals("none")){
                    showPhoto.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void createProducts() throws FileNotFoundException {
        File listFile = new File(listPath);
        Scanner scanner = new Scanner(listFile);
        String[] values = scanner.nextLine().split(";");
        for (int i = 1; i < values.length; i+=4){
            String name = values[i];
            String amount = values[i+1];
            String unit = values[i+2];
            String photoPath = null;
            if(!values[i+3].equals("none")) photoPath = values[i+3];
            if(photoPath!=null){
                Product product = new Product(name, amount, unit, photoPath);
                products.add(product);
            }
            else {
                Product product = new Product(name, amount, unit);
                products.add(product);
            }
        }
    }

    private void listChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingActivity.this);
        builder.setTitle("Wybierz listę zakupów");
        String path = Environment.getExternalStorageDirectory().getPath()+"/Zakupy++/";
        File folder = new File(path);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ShoppingActivity.this, android.R.layout.select_dialog_singlechoice);
        for(File f: folder.listFiles()){
            if(!f.getName().equals("Zdjęcia")) arrayAdapter.add(f.getName());
        }
        builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onBackPressed();
            }
        });
        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listPath = Environment.getExternalStorageDirectory().getPath()+"/Zakupy++/"+arrayAdapter.getItem(which);
                dialog.dismiss();
                try {
                    createProducts();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
        });
        builder.show();
    }
}
