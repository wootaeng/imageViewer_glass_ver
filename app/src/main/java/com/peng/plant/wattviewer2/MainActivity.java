package com.peng.plant.wattviewer2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.peng.plant.wattviewer2.view.LocalGallery;

public class MainActivity extends AppCompatActivity {

    Button moveTolocal;
    Button moveToserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //localgallery 이동
        moveTolocal = (Button)findViewById(R.id.localBtn);
        moveTolocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LocalGallery.class);
                startActivity(i);
            }
        });


    }
}