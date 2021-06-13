package com.example.myapplication.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.ContactModel;

import java.util.ArrayList;

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.Viewholder> {
    Context context;
    ContactModel model = new ContactModel();
    ArrayList<ContactModel> arrayList;
    public CallAdapter(Context context,ArrayList<ContactModel>arrayList){
        this.context=context;
        this.arrayList=arrayList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_call,parent,false);
        return new Viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        ContactModel model =arrayList.get(position);
        holder.tvName.setText(model.getName());
        holder.tvNumber.setText(model.getNumber());
        holder.callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactModel temp = arrayList.get(position);
                Intent j =new Intent(Intent.ACTION_CALL);
                String s ="tel:"+temp.getNumber();
                j.setData(Uri.parse(s));
                j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(j);
            }

        });
        holder.smsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactModel temp = arrayList.get(position);
                Intent j =new Intent(Intent.ACTION_SENDTO);
                String s ="smsto:"+temp.getNumber();
                j.setData(Uri.parse(s));
                j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(j);
            }
        });
        holder.vidobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactModel temp = arrayList.get(position);
                Intent callIntent = new Intent("com.android.phone.videocall");
                callIntent.putExtra("videocall", true);
                callIntent.setData(Uri.parse("tel:" +temp.getNumber()));

                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(callIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView tvName,tvNumber; ImageButton callbtn,smsbtn,vidobtn;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_name1);
            tvNumber=itemView.findViewById(R.id.tv_number1);
            callbtn=itemView.findViewById(R.id.callbtn);
            smsbtn=itemView.findViewById(R.id.smsbtn);
            vidobtn=itemView.findViewById(R.id.vidobtn);
        }
    }
}
