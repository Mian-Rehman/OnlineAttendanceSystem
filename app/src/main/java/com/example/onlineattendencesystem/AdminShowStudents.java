package com.example.onlineattendencesystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineattendencesystem.Model.AddClassData;
import com.example.onlineattendencesystem.Model.SrudentAdapter;
import com.example.onlineattendencesystem.Model.StudentDataClass;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdminShowStudents extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SrudentAdapter mStudentAdapter;
    private List<AddClassData> mDatalist;

    FirebaseDatabase database;
    DatabaseReference myRef;

    ImageView admin_studentlist_back;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_show_students);
        setTitle("");

        admin_studentlist_back=findViewById(R.id.admin_studentlist_back);

        admin_studentlist_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent=new Intent(AdminShowStudents.this,AdminDashboard.class);
                startActivity(backIntent);
                finish();
            }
        });



        mDatalist=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView_student);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



            showAllStudent();



    }



    private void showAllStudent()
    {
        FirebaseRecyclerOptions<StudentDataClass> options =
                new FirebaseRecyclerOptions.Builder<StudentDataClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("studentData"),StudentDataClass.class)
                        .build();

        mStudentAdapter=new SrudentAdapter(options);
        recyclerView.setAdapter(mStudentAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mStudentAdapter.startListening();
    }


    @Override
    protected void onStop() {
        super.onStop();
        mStudentAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem item=menu.findItem(R.id.search);



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {



        switch (item.getItemId())
        {
            case R.id.search:
                SearchView searchView=(SearchView)item.getActionView();

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        processsearch(s);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        processsearch(s);
                        return false;
                    }
                });
                break;

        }
        return true;
    }



    private void processsearch(String s)
    {
        FirebaseRecyclerOptions<StudentDataClass> options =
                new FirebaseRecyclerOptions.Builder<StudentDataClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("studentData").orderByChild("studentName").startAt(s).endAt(s+"\uf8ff"), StudentDataClass.class)
                        .build();

        mStudentAdapter=new SrudentAdapter(options);
        mStudentAdapter.startListening();
        recyclerView.setAdapter(mStudentAdapter);

    }
}