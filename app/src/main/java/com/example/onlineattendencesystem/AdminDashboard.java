package com.example.onlineattendencesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class AdminDashboard extends AppCompatActivity {



    NavigationView navMenu;
    ActionBarDrawerToggle toggle;
    DrawerLayout drayerLayout;

    Fragment temp=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

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
                      temp=new AdminTeacherProfileFrag();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,temp).commit();
                        toolbar.setTitle("Teacher Profile");
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_add_class:
                        temp = new AdminAddClassFrag();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,temp).commit();
                        toolbar.setTitle("Add Classes");
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_logout:
                        Toast.makeText(AdminDashboard.this, "Logout", Toast.LENGTH_SHORT).show();
                        drayerLayout.closeDrawer(GravityCompat.START);
                        Intent adminLogoutIntent=new Intent(AdminDashboard.this,AdminLoginScreen.class);
                        startActivity(adminLogoutIntent);
                        finish();
                        break;



                }
                return false;
            }
        });






    }
}