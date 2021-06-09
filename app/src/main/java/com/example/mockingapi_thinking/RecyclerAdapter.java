package com.example.mockingapi_thinking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>{
    private ArrayList<User> listUsers;
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        RecyclerViewHolder recylerViewHolder = new RecyclerViewHolder(v);
        return recylerViewHolder;
    }


    public RecyclerAdapter(ArrayList<User> listUsers){
        this.listUsers = listUsers;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        User user = listUsers.get(position);
        holder.txtID.setText("ID: "+user.getId());
        holder.txtName.setText("Name: "+user.getName());
        holder.txtCountry.setText("Country: "+user.getCountry());
    }



    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView txtID;
        public TextView txtName;
        public TextView txtCountry;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtID = itemView.findViewById(R.id.tvID);
            txtName = itemView.findViewById(R.id.tvName);
            txtCountry = itemView.findViewById(R.id.tvCountry);
        }
    }
}
