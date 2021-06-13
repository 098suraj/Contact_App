package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.CallHisModel;
import com.example.myapplication.model.ContactModel;

import java.util.ArrayList;

public class CallHisAdapter extends RecyclerView.Adapter<CallHisAdapter.Viewholder> {
    Context context;
    ArrayList<CallHisModel> arrayList;
    public CallHisAdapter(Context context, ArrayList<CallHisModel>arrayList){
        this.context=context;
        this.arrayList=arrayList;
        notifyDataSetChanged();
    }

    @Override
    public Viewholder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder( Viewholder holder, int position) {
        CallHisModel model =arrayList.get(position);
        holder.name.setText(model.getName());
        holder.duration.setText(model.getDuration());
        holder.date.setText(model.getDate());
        holder.callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallHisModel temp = arrayList.get(position);
                Intent j =new Intent(Intent.ACTION_CALL);
                String s ="tel:"+temp.getDir();
                j.setData(Uri.parse(s));
                j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(j);
            }

        });
        holder.smsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallHisModel temp = arrayList.get(position);
                Intent j =new Intent(Intent.ACTION_SENDTO);
                String s ="smsto:"+temp.getDir();
                j.setData(Uri.parse(s));
                j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(j);
            }
        });




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView name,duration,date;
        ImageButton callbtn,smsbtn;
        public Viewholder( View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.his_name);
            duration=itemView.findViewById(R.id.his_duration);
            date=itemView.findViewById(R.id.his_date);
            callbtn=itemView.findViewById(R.id.hiscallbtn);
            smsbtn=itemView.findViewById(R.id.hissmsbtn);


        }
    }
}
