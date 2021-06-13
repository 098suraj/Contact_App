package com.example.myapplication.fragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.database.Cursor;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.ContactDetails;
import com.example.myapplication.model.ContactModel;
import com.example.myapplication.adapter.MainAdapter;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashSet;


public class conta extends Fragment {
    RecyclerView recyclerView;
    private MainAdapter.Recyclerviewlistner listner;
    ArrayList<ContactModel> arrayList = new ArrayList<ContactModel>();
    View v;


    MainAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_conta, container, false);
        recyclerView = v.findViewById(R.id.ryc_contacts);
        getContactlist();
   return v;
    }
    private void getContactlist() {
        String[] fieldListProjection = {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER,
                ContactsContract.Contacts.HAS_PHONE_NUMBER
        };
        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";

        Cursor phones = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                        , fieldListProjection, null, null, sort);
        HashSet<String> normalizedNumbersAlreadyFound = new HashSet<>();

        if (phones != null && phones.getCount() > 0) {
            while (phones.moveToNext()) {
                String normalizedNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER));
                if (Integer.parseInt(phones.getString(phones.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    if (normalizedNumbersAlreadyFound.add(normalizedNumber)) {
                        String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                        String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.d("test", " Print all values");
                        ContactModel model = new ContactModel();
                        model.setName(name);
                        model.setNumber(phoneNumber);
                        arrayList.add(model);
                    }

                }

            }
            phones.close();
        }
       setAdapter();
    }

    private void setAdapter() {
        setOnClickListner();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MainAdapter(getContext(), arrayList,listner);
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListner() {
        listner=new MainAdapter.Recyclerviewlistner() {
            @Override
            public void onClick(View v, int position) {
                Intent intent=new Intent(getActivity(), ContactDetails.class);
                intent.putExtra("Name",arrayList.get(position).getNumber());
                startActivity(intent);
            }
        };
    }

}