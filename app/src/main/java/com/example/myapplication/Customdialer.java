package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;

public class Customdialer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customdialer);
        TextView num=findViewById(R.id.num);
        FloatingActionButton fncall=findViewById(R.id.fncall);
        num.getText().toString();
        fncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j =new Intent(Intent.ACTION_CALL);
                String s ="tel:"+num;
                j.setData(Uri.parse(s));
                j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(j);
            }
        });


    }
}