package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.telecom.Call;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static android.Manifest.permission.READ_CALL_LOG;
import static android.Manifest.permission.READ_PHONE_NUMBERS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;

import com.example.myapplication.adapter.CallAdapter;
import com.example.myapplication.adapter.CallHisAdapter;
import com.example.myapplication.adapter.MainAdapter;
import com.example.myapplication.model.CallHisModel;
import com.example.myapplication.model.ContactModel;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ContactModel> arrayList = new ArrayList<ContactModel>();

    TextView ph, moutv, mintv,moutv1;
    CallAdapter adapter;
    FloatingActionButton adconta, dialbtn;
    CardView cont, recn, fav;
    static final int requestcode = 122;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        TextView ph = findViewById(R.id.ph);
        checkPermission();
        cont = findViewById(R.id.cont);
        fav = findViewById(R.id.fav);
        recn = findViewById(R.id.recn);
        adconta = findViewById(R.id.addconta);
        dialbtn = findViewById(R.id.dialBtn);
        moutv = findViewById(R.id.moutv);
        mintv = findViewById(R.id.mintv);
        moutv1=findViewById(R.id.moutv1);

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coni = new Intent(MainActivity.this, Contact.class);
                String a = "a";
                coni.putExtra("contacts", a);
                startActivity(coni);
            }
        });
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fa = new Intent(MainActivity.this, Contact.class);
                String b = "b";
                fa.putExtra("call", b);
                startActivity(fa);
            }
        });
        recn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rec = new Intent(MainActivity.this, Contact.class);
                String c = "c";
                rec.putExtra("history", c);
                startActivity(rec);

            }
        });

        String num = getMyPhoneNO();
        ph.setText(num.substring(2));

        adconta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kai = new Intent(MainActivity.this, AddContact.class);
                startActivity(kai);
            }
        });
        dialbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ki = new Intent(MainActivity.this, Customdialer.class);
                startActivity(ki);
            }
        });

    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS)
                + ActivityCompat.checkSelfPermission(MainActivity.this, READ_SMS)
                + ActivityCompat.checkSelfPermission(MainActivity.this, READ_PHONE_STATE)
                + ActivityCompat.checkSelfPermission(MainActivity.this, READ_PHONE_NUMBERS)
                + ActivityCompat.checkSelfPermission(MainActivity.this, READ_CALL_LOG)
                + ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)
                + ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                + ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS,
                            Manifest.permission.CALL_PHONE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            READ_CALL_LOG,
                            READ_PHONE_NUMBERS,
                            READ_PHONE_STATE,
                            READ_SMS
                    }, requestcode);
        } else {
            getContactlist();
            getMyPhoneNO();


        }
    }


    private String getMyPhoneNO() {
        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String mPhoneNumber = tMgr.getLine1Number();
        return mPhoneNumber;
    }

    private void getContactlist() {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
        Cursor cursor = getContentResolver().query(uri, null, null, null, sort);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts._ID
                ));
                String name = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME
                ));
                Uri uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                        + " =?";
                Cursor phoneCursor = getContentResolver().query(uriPhone, null, selection
                        , new String[]{id}, null);
                if (phoneCursor.moveToNext()) {
                    String number = phoneCursor.getString(phoneCursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    ));
                    ContactModel model = new ContactModel();
                    model.setName(name);
                    model.setNumber(number);
                    arrayList.add(model);
                    phoneCursor.close();
                }
            }
            cursor.close();
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CallAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == requestcode && grantResults.length > 0 &&
                grantResults[0]
                        + grantResults[1]
                        + grantResults[2]
                        + grantResults[3]
                        + grantResults[4]
                        + grantResults[5]
                        + grantResults[6]
                        + grantResults[7]
                        == PackageManager.PERMISSION_GRANTED) {
            getContactlist();
            getMyPhoneNO();


        } else {
            Toast.makeText(MainActivity.this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            checkPermission();
        }
    }


}


