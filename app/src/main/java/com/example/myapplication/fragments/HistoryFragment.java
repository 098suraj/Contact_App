package com.example.myapplication.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CallLog;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.adapter.CallHisAdapter;
import com.example.myapplication.adapter.MainAdapter;
import com.example.myapplication.model.CallHisModel;
import com.example.myapplication.model.ContactModel;
import com.google.android.material.timepicker.TimeFormat;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.SimpleTimeZone;


public class HistoryFragment extends Fragment  {
    RecyclerView recyclerView;
    ArrayList<CallHisModel> arrayList = new ArrayList<CallHisModel>();
    View v;
    CallHisAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = v.findViewById(R.id.ryc_his);
        geHistorylist();
        return v;
    }
    private void geHistorylist() {
        Uri uri = CallLog.Calls.CONTENT_URI;
        String sort = CallLog.Calls.DATE + " DESC";
        Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, sort);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                String callType = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
                String name1 = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_FORMATTED_NUMBER));
                String dirk = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NORMALIZED_NUMBER));
                String date = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE
                ));
                Date date1=new Date(Long.valueOf(date));


                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm");
                String dateString = formatter.format(date1);
                CallHisModel model = new CallHisModel();
                String dir = null;
                int dircode = Integer.parseInt(callType);
                switch (dircode){
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "Outgoing";
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "Incoming";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        dir = "Missed";
                        break;
                }

                   if(name.isEmpty()){
                       model.setName(name1);
                   }else {
                       model.setName(name);
                   }
                   model.setDir(dirk);
                    model.setDuration(dir+"  "+duration+" Seconds");
                    model.setDate(dateString);

                    arrayList.add(model);


            }
            cursor.close();
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CallHisAdapter(getContext(), arrayList);
        recyclerView.setAdapter(adapter);
    }


}