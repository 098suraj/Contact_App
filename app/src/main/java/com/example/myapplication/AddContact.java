package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class AddContact extends AppCompatActivity {
    EditText firstNamEt,lastNamEt,phoneMobileEt,phoneHomeEt,emailEt;
    FloatingActionButton fabSave;
    ImageView thumbnailTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        firstNamEt=findViewById(R.id.firstNamEt);
        phoneHomeEt=findViewById(R.id.phoneHomeEt);
        emailEt=findViewById(R.id.emailEt);
        fabSave=findViewById(R.id.fabSave);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ContactsContract.Intents.Insert.ACTION);
                i.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                i.putExtra(ContactsContract.Intents.Insert.NAME,firstNamEt.getText().toString());
                i.putExtra(ContactsContract.Intents.Insert.EMAIL,emailEt.getText().toString());
                i.putExtra(ContactsContract.Intents.Insert.PHONE, phoneHomeEt.getText().toString());
                i.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);

                startActivity(i);
            }

        });
    }
}