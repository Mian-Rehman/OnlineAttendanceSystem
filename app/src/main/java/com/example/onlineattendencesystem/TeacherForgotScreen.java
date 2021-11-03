package com.example.onlineattendencesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherForgotScreen extends AppCompatActivity {


    ImageView teacher_back;
    EditText ed_user,ed_password,ed_confirm_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_forgot_screen);

        teacher_back=findViewById(R.id.teacher_back);
        ed_user=findViewById(R.id.ed_user);
        ed_password=findViewById(R.id.ed_password);
        ed_confirm_pass=findViewById(R.id.ed_confirm_pass);


        teacher_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent=new Intent(TeacherForgotScreen.this,TeacherLoginScreen.class);
                startActivity(backIntent);
                finish();
            }
        });

        SharedPreferences sp=getSharedPreferences("TEACHERDATA",MODE_PRIVATE);

        String checkteacherID=sp.getString("teacherID","");

        String pass=ed_password.getText().toString();
        String conpass=ed_confirm_pass.getText().toString();
        if (checkteacherID.equals(ed_user.getText().toString()))
        {

            if (pass.equals(conpass))
            {
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("teacherData");

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                       //code pendinf for last


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


        }


    }
}