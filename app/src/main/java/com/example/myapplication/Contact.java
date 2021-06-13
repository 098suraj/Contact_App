package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.fragments.CallFragment;
import com.example.myapplication.fragments.HistoryFragment;
import com.example.myapplication.fragments.conta;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;




public class Contact extends AppCompatActivity {
    ChipNavigationBar btmnav;
    FragmentManager fragmentManager;
    TextView temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        btmnav=findViewById(R.id.btmnav);
        String callint=getIntent().getStringExtra("call");
        String histo=getIntent().getStringExtra("history");
        String contac=getIntent().getStringExtra("contacts");
        temp=findViewById(R.id.temptext);
        Fragment k=null;
        if (contac!=null){
            k=new conta();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragcont,k).commit();
        }else if(histo!=null){
            k=new HistoryFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragcont,k).commit();
        }else if(callint!=null)
            k=new CallFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragcont,k).commit();


        btmnav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment=null;
                switch (i){
                    case R.id.contact:
                        fragment=new conta();
                        break;
                    case R.id.his:
                        fragment=new HistoryFragment();
                        break;
                    case R.id.call:
                        fragment=new CallFragment();
                        break;
                }
               getSupportFragmentManager().beginTransaction().replace(R.id.fragcont,fragment).commit();
            }
        });


    }
}