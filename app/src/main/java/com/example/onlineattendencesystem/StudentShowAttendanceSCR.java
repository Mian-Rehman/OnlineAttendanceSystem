package com.example.onlineattendencesystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.onlineattendencesystem.Model.AddClassData;
import com.example.onlineattendencesystem.Model.AttendanceAdapter;
import com.example.onlineattendencesystem.Model.SrudentAdapter;
import com.example.onlineattendencesystem.Model.StudentAttendaceClass;
import com.example.onlineattendencesystem.Model.StudentDataClass;
import com.example.onlineattendencesystem.Model.UserAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class StudentShowAttendanceSCR extends AppCompatActivity {


    private RecyclerView recyclerView_showAtt;
    private AttendanceAdapter mAttendanceAdapter;
    private List<StudentAttendaceClass> mDataList;

    FirebaseDatabase database;
    DatabaseReference myRef;

    private ChildEventListener MyChildEventListener;

    @Override
    public void onDestroy() {
        super.onDestroy();
        myRef.removeEventListener(MyChildEventListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_show_attendance_s_c_r);


        mDataList=new ArrayList<>();
        recyclerView_showAtt=findViewById(R.id.recyclerView_showAtt);
        recyclerView_showAtt.setLayoutManager(new LinearLayoutManager(this));
        mAttendanceAdapter=new AttendanceAdapter(this,mDataList);
        recyclerView_showAtt.setAdapter(mAttendanceAdapter);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("attendanceStudent");


        MyChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                StudentAttendaceClass classData = snapshot.getValue(StudentAttendaceClass.class);

                mDataList.add(classData);
                mAttendanceAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        myRef.addChildEventListener(MyChildEventListener);




    }


}