package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ContactDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        TextView contname=findViewById(R.id.contname);
        TextView contnum=findViewById(R.id.contnum);
        String name="kkk";
        Bundle ectras=getIntent().getExtras();
        if(ectras!=null){
            name=ectras.getString("name");
        }
        contname.setText(name);
    }
}