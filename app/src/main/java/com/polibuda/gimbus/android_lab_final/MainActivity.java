package com.polibuda.gimbus.android_lab_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button makeListButton, goShoppingButton, manageListsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUI();
        setListeners();
    }

    private void setUI() {
        this.makeListButton = findViewById(R.id.make_list_button);
        this.goShoppingButton = findViewById(R.id.go_shopping_button);
        this.manageListsButton = findViewById(R.id.manage_lists_button);
    }

    private void setListeners() {
        this.makeListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateListActivity.class));
            }
        });
        this.goShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShoppingActivity.class));
            }
        });
        this.manageListsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShoppingActivity.class));
            }
        });
    }
}
