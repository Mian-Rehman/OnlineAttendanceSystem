package com.example.onlineattendencesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class ConfirmScreen extends AppCompatActivity {

    ImageView admin,teacher,student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_screen);

        admin=findViewById(R.id.admin);
        teacher=findViewById(R.id.teacher);
        student=findViewById(R.id.student);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences sp=getSharedPreferences("USERDATA",MODE_PRIVATE);
        SharedPreferences.Editor ed=sp.edit();

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed.putString("AdminAccess","AdminAccess");
                ed.putString("Admin_Username","admin");
                ed.putString("Admin_Password","admin123");
                ed.apply();
                Intent admin_intent=new Intent(ConfirmScreen.this,AdminLoginScreen.class);
                startActivity(admin_intent);
            }
        });


        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed.putString("Teacheraceess","Teacheraceess");
                ed.apply();
                Intent teacher_intent=new Intent(ConfirmScreen.this,TeacherLoginScreen.class);
                startActivity(teacher_intent);

            }
        });

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed.putString("studentAccess","studentAccess");
                ed.apply();
                Intent student_intent=new Intent(ConfirmScreen.this,StudentLoginScreen.class);
                startActivity(student_intent);

            }
        });

    }
}