package com.polibuda.gimbus.android_lab_final;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CreateListActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1337;

    Button createListButton, itemAddPhotoButton, itemAddToListButton;
    EditText itemNameInput, itemAmountInput;
    TextView addPhotoStatus;
    Spinner itemAmountUnit;
    String imagePath = null;
    Uri imageURI = null;
    ListView currentList;
    File savePath;
    ProductListAdapter productListAdapter;
    ArrayList<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        initUI();
        fileSystemSetup();
        productListAdapter = new ProductListAdapter(getApplicationContext(), R.layout.create_list_item, products, savePath.getAbsolutePath());
        this.currentList.setAdapter(productListAdapter);
        setupListeners();
    }

    private void setupListeners() {
        this.itemAddToListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = itemNameInput.getText().toString();
                String amount = itemAmountInput.getText().toString();
                String unit = itemAmountUnit.getSelectedItem().toString();
                if(imagePath!=null) productListAdapter.addItem(name, amount, unit, imagePath);
                else productListAdapter.addItem(name, amount, unit);
                clearEdits();
            }
        });
        this.itemAddPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        this.createListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeStamp = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new Date());
                String fileName = "Lista "+timeStamp;
                try {
                    productListAdapter.saveToFile(fileName);
                } catch (IOException e) {}
                Toast.makeText(getApplicationContext(),"Lista zapisana",Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) { }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.polibuda.gimbus.android_lab_final",
                        photoFile);
                this.imageURI = photoURI;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
        addPhotoStatus.setVisibility(View.VISIBLE);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new Date());
        String imageFileName = "Zakupy++ " + timeStamp;
        File storageDir = new File(savePath,"Zdjęcia");
        File image = new File(storageDir.getAbsolutePath()+"/"+imageFileName+".jpeg");
        imagePath = image.getAbsolutePath();
        return image;
    }

    private void clearEdits(){
        this.itemNameInput.setText("");
        this.itemAmountInput.setText("");
        this.addPhotoStatus.setVisibility(View.INVISIBLE);
    }

    private void fileSystemSetup() {
        String path = Environment.getExternalStorageDirectory().getPath()+"/Zakupy++";
        this.savePath = new File(path);
        if(!this.savePath.exists()) this.savePath.mkdir();
        File fotopath = new File(path+"/Zdjęcia/");
        if(!fotopath.exists()) fotopath.mkdir();
    }

    private void initUI() {
        this.createListButton = findViewById(R.id.create_list_button);
        this.itemAddPhotoButton = findViewById(R.id.item_add_photo);
        this.itemAddToListButton = findViewById(R.id.item_add_to_list_button);
        this.itemNameInput = findViewById(R.id.item_name_input);
        this.itemAmountInput = findViewById(R.id.item_amount_input);
        this.itemAmountUnit = findViewById(R.id.item_amount_unit);
        this.currentList = findViewById(R.id.creator_current_list);
        this.addPhotoStatus = findViewById(R.id.item_photo_status);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.item_amount_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.itemAmountUnit.setAdapter(adapter);
    }
}
