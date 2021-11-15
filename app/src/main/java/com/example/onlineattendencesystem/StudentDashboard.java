package com.example.onlineattendencesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class StudentDashboard extends AppCompatActivity {

    NavigationView navMenu_Student;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerlayout_student;

    Fragment temp=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);


        Toolbar toolbar_Student=findViewById(R.id.toolbar_Student);
        setSupportActionBar(toolbar_Student);

        navMenu_Student=findViewById(R.id.navMenu_Student);
        drawerlayout_student=findViewById(R.id.drawerlayout_student);

    //   getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new StudentDashHomeFrag()).commit();

       temp=new StudentProfileFrag();
       getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_Student,temp).commit();

        toggle=new ActionBarDrawerToggle(this,drawerlayout_student,toolbar_Student,R.string.app_name,R.string.app_name);
        drawerlayout_student.addDrawerListener(toggle);
        toggle.syncState();


        navMenu_Student.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {

                    case R.id.nav_student_profile:
                        temp=new StudentProfileFrag();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_Student,temp).commit();
                        toolbar_Student.setTitle("My Profile");
                        drawerlayout_student.closeDrawer(GravityCompat.START);
                        break;


                    case R.id.nav_student_attendence:

                        Intent studentAttIntent=new Intent(StudentDashboard.this,StudentAttendanceSCR.class);
                        startActivity(studentAttIntent);
                        toolbar_Student.setTitle("My Attendance");
                        drawerlayout_student.closeDrawer(GravityCompat.START);
                        break;


                    case R.id.nav_student_MonthAtten:

                        Intent showIntent=new Intent(StudentDashboard.this,StudentShowAttendanceSCR.class);
                        startActivity(showIntent);

                      //  temp=new StudentProfileFrag();
                      //  getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_Student,temp).commit();
                        toolbar_Student.setTitle("Monthly Attendance");
                        drawerlayout_student.closeDrawer(GravityCompat.START);
                        break;


                    case R.id.nav_Studentlogout:
                        drawerlayout_student.closeDrawer(GravityCompat.START);
                        Intent logout_intent=new Intent(StudentDashboard.this,ConfirmScreen.class);
                        startActivity(logout_intent);
                        finish();
                        break;




                }



                return false;
            }
        });

         LayoutInflater inflater=getLayoutInflater();
         View view=inflater.inflate(R.layout.drawer_header,null);
        TextView pro_name_set=view.findViewById(R.id.tv_dash_profile);
        pro_name_set.setText("Name");


    }
}