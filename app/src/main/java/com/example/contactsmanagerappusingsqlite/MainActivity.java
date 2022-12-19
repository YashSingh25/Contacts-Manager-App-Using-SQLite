package com.example.contactsmanagerappusingsqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contactsmanagerappusingsqlite.Adapters.MyAdapter;
import com.example.contactsmanagerappusingsqlite.db.db.MyDatabaseHelper;
import com.example.contactsmanagerappusingsqlite.db.entities.Contacts;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper db;
    private ArrayList<Contacts> contactsArrayList;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My FAV Contacts");

        //RecyclerView
        recyclerView=findViewById(R.id.recyclerView);
        db = new MyDatabaseHelper(this);

        //Contacts List
        contactsArrayList=db.getAllContacts();

        myAdapter =new MyAdapter(this , contactsArrayList , MainActivity.this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);

        FloatingActionButton fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAddEditContacts(false , null , -1);
            }
        });
    }


    public void addAddEditContacts(final boolean isUpdated,final Contacts contacts,final int position) {
        View view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.sample_add_contact , null);

        AlertDialog.Builder builder=new AlertDialog.Builder(this );
        builder.setView(view);

        TextView contactTitle=view.findViewById(R.id.contactTitle);
        EditText cName=view.findViewById(R.id.etName);
        EditText cPhoneNo=view.findViewById(R.id.etPhoneNo);
        contactTitle.setText(!isUpdated ? "Add New Contact" : "Edit Contact");

        if(isUpdated & contacts !=null){
            cName.setText(contacts.getName());
            cPhoneNo.setText(contacts.getPhoneNo());
        }
        builder.setCancelable(false)
                .setPositiveButton(isUpdated ? "Update" : "Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                } ) .setNegativeButton(isUpdated ? "Delete" : "Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(isUpdated){
                                    deleteContact(contacts , position);
                                }
                                else {
                                    dialogInterface.cancel();
                                }
                            }
                        });

        AlertDialog alertDialog= builder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(cName.getText().toString()) || TextUtils.isEmpty(cPhoneNo.getText().toString())){
                    if(TextUtils.isEmpty(cName.getText().toString()))
                        Toast.makeText(MainActivity.this, "Please Enter a Name", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    alertDialog.dismiss();
                }

                if(isUpdated & contacts !=null){
                    updateContact(cName.getText().toString() , cPhoneNo.getText().toString() , position);
                }else {
                    createContact(cName.getText().toString() , cPhoneNo.getText().toString());
                }
            }
        });

    }

    private void deleteContact(Contacts contacts, int position) {
        contactsArrayList.remove(position);
        db.deleteContacts(contacts);
        myAdapter.notifyDataSetChanged();

    }

    private void updateContact(String name,String phoneNo, int position){
        Contacts contacts=contactsArrayList.get(position);
        contacts.setName(name);
        contacts.setPhoneNo(phoneNo);

        db.updateContact(contacts);

        contactsArrayList.set(position , contacts);
        myAdapter.notifyDataSetChanged();

    }

    private void createContact(String name,String phoneNo){
        long id= db.insertContacts(name , phoneNo);
        Contacts contacts=db.getContact(id);
        if(contacts != null){
            contactsArrayList.add(0 , contacts);
            myAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}