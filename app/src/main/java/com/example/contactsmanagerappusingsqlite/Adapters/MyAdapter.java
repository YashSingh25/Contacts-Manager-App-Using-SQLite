package com.example.contactsmanagerappusingsqlite.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactsmanagerappusingsqlite.MainActivity;
import com.example.contactsmanagerappusingsqlite.R;
import com.example.contactsmanagerappusingsqlite.db.entities.Contacts;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Contacts> contactsArrayList;
    private MainActivity mainActivity;

    public MyAdapter(Context context, ArrayList<Contacts> contactsArrayList,MainActivity mainActivity) {
        this.context = context;
        this.contactsArrayList = contactsArrayList;
        this.mainActivity =  mainActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_contact_list_item , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Contacts contacts= contactsArrayList.get(position);
        holder.name.setText(contacts.getName());
        holder.phoneNo.setText(contacts.getPhoneNo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.addAddEditContacts(true , contacts , position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,phoneNo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name =itemView.findViewById(R.id.tvName);
            phoneNo =itemView.findViewById(R.id.tvPhoneNo);

        }
    }
}
