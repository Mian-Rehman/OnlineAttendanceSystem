package com.example.onlineattendencesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.onlineattendencesystem.Model.StudentAttendaceClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StudentAttendanceCompleteSCR extends AppCompatActivity {

    Button btn_com_dash,btn_com_logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance_complete_s_c_r);


        btn_com_dash=findViewById(R.id.btn_com_dash);
        btn_com_logout=findViewById(R.id.btn_com_logout);


        btn_com_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent=new Intent(StudentAttendanceCompleteSCR.this,ConfirmScreen.class);
                startActivity(backIntent);
                finish();
            }
        });

        btn_com_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dashIntent= new Intent(StudentAttendanceCompleteSCR.this,StudentDashboard.class);
                startActivity(dashIntent);
                finish();
            }
        });

        uploadAttendaceToDatabase();



    }

    private String getTimeWithAmPm()
    {
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }

    private String getCurrentdate()
    {
        return new SimpleDateFormat("dd/LLL/yyyy", Locale.getDefault()).format(new Date());
    }

    private String getCurrentdateOnly()
    {
        return new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
    }

    private String getdateWithMonth()
    {
        return new SimpleDateFormat("LLL", Locale.getDefault()).format(new Date());
    }

    private void uploadAttendaceToDatabase()
    {

        SharedPreferences sp=getSharedPreferences("STUDENT_DATA",MODE_PRIVATE);

        String studentId=sp.getString("studentID","");

        String currentMonth=getdateWithMonth();
        String currentDate = getCurrentdate();
        String currentTime= getTimeWithAmPm();
        String studentPresent="Present";
        String date=getdateWithMonth()+" "+ getCurrentdateOnly();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("attendanceStudent");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                StudentAttendaceClass classData=new StudentAttendaceClass(currentMonth,currentTime,studentId,currentDate,studentPresent,date);
                String key_value=reference.child(currentMonth).getKey();
                reference.child(date).child(studentId).setValue(classData);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}