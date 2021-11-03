package com.example.onlineattendencesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class TeacherDashboardScreen extends AppCompatActivity {

    NavigationView navMenu;
    ActionBarDrawerToggle toggle;
    DrawerLayout drayerLayout;

    Fragment temp=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard_screen);


        Toolbar toolbar=findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

        navMenu=findViewById(R.id.navMenu);
        drayerLayout=findViewById(R.id.drawerlayout);

        //    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new AdminTeacherProfileFrag()).commit();


        toggle=new ActionBarDrawerToggle(this,drayerLayout,toolbar,R.string.app_name,R.string.app_name);
        drayerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId())
                {


                    case R.id.nav_teacher_profile:
                        Toast.makeText(TeacherDashboardScreen.this, "My Profile", Toast.LENGTH_SHORT).show();
                        toolbar.setTitle("My Profile");
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_teacher_attendence:
                        temp=new TeacherAttendanceScreen();
                       getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,temp).commit();
                        toolbar.setTitle("My Attendence");
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_teacher_addStudent:
                        temp = new TeacherAddStudentFrag();
                       getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,temp).commit();
                        toolbar.setTitle("Add Student Profile");
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_teacher_stAttendence:
                        temp = new AddStudentAttFrag();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,temp).commit();
                        toolbar.setTitle("Add Student Attendence");
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;


                    case R.id.nav_logout:
                        Toast.makeText(TeacherDashboardScreen.this, "Logout", Toast.LENGTH_SHORT).show();
                        Intent logoutIntent=new Intent(TeacherDashboardScreen.this,TeacherLoginScreen.class);
                        startActivity(logoutIntent);
                        finish();
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;



                }
                return false;
            }
        });


    }
}