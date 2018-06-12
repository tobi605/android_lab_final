package com.polibuda.gimbus.android_lab_final;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ManageListsActivity extends AppCompatActivity {

    SavedListAdapter adapter;
    ListView listView;
    ArrayList<String> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_lists);
        this.listView = findViewById(R.id.manage_lists_list);
        try {
            initLists();
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapter = new SavedListAdapter(getApplicationContext(), R.layout.manage_list_item, lists);
        listView.setAdapter(adapter);
    }

    private void initLists() throws IOException {
        this.lists = new ArrayList<>();
        String path = Environment.getExternalStorageDirectory().getPath()+"/Zakupy++/";
        File folder = new File(path);
        for(File f: folder.listFiles()){

            if(!f.getName().equals("Zdjęcia")) lists.add(parse(f));
        }
    }

    private String parse(File f) throws IOException {
        Scanner scanner = new Scanner(f);
        String filename = f.getAbsolutePath();
        String insides = scanner.nextLine();
        return filename+";"+insides;
    }
}
