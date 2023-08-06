package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactsapp.R;
import com.example.myapplication.model.ContactModel;


import java.util.ArrayList;
import java.util.Random;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHoloder> {
    Context context;
    ArrayList<ContactModel> arrayList;
    private Recyclerviewlistner listner;
    public MainAdapter(Context context,ArrayList<ContactModel>arrayList,Recyclerviewlistner listner){
        this.context=context;
        this.listner=listner;
        this.arrayList=arrayList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHoloder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact,parent,false);
        return new ViewHoloder(view);
    }

    @Override
    public void onBindViewHolder( MainAdapter.ViewHoloder holder, int position) {
        ContactModel model =arrayList.get(position);
        holder.tvName.setText(model.getName());
        holder.tvNumber.setText(model.getNumber());



    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class ViewHoloder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName,tvNumber; CardView q;
        public ViewHoloder(View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_name);
            tvNumber=itemView.findViewById(R.id.tv_number);
            q=itemView.findViewById(R.id.card);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listner.onClick(itemView,getAdapterPosition());
        }
    }
    public interface  Recyclerviewlistner{
        void onClick(View v,int position);
    }
}
