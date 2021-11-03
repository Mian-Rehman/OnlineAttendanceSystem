package com.example.onlineattendencesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onlineattendencesystem.Model.TeacherClassData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class StudentLoginScreen extends AppCompatActivity {


    Button btn_Login;
    TextView tv_forgot_password;
    EditText ed_user,ed_password;

    ImageView Teacher_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login_screen);

        //Type casting Button
        btn_Login=findViewById(R.id.btn_Login);

        //Image type casting
        Teacher_back=findViewById(R.id.Teacher_back);


        //Type Casting EditText
        ed_user=findViewById(R.id.ed_user);
        ed_password=findViewById(R.id.ed_password);

        //Type casting Textview
        tv_forgot_password=findViewById(R.id.tv_forgot_password);

        Teacher_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent=new Intent(StudentLoginScreen.this,ConfirmScreen.class);
                startActivity(backIntent);
                finish();
            }
        });


        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("studentData");
                String login_StudentId=ed_user.getText().toString().trim();
                String login_student_password=ed_password.getText().toString().trim();

                Query checkuser=reference.orderByChild("studentId").equalTo(login_StudentId);


                checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {



                        if (snapshot.exists())
                        {
                            String checkteacherId=snapshot.child(login_StudentId).child("studentId").getValue(String.class);
                            String checkpassword=snapshot.child(login_StudentId).child("studentPassword").getValue(String.class);


                            if (checkteacherId.equals(login_StudentId))
                            {
                                if (checkpassword.equals(login_student_password))
                                {
                                    TeacherClassData data=snapshot.child(login_StudentId).getValue(TeacherClassData.class);

                                    //Save Current Teacher Details
                                    SharedPreferences sp=getSharedPreferences("STUDENT_DATA",MODE_PRIVATE);
                                    SharedPreferences.Editor ed=sp.edit();

                                    ed.putString("studentName",data.getTeacherName());
                                    ed.putString("studentID",data.getTeacherId());
                                    ed.putString("studentEmail",data.getTeacherEmail());
                                    ed.putString("studentDOB",data.getTeacherDateOfBirth());
                                    ed.putString("studentQualification",data.getTeacherQualification());
                                    ed.putString("studentPassword",data.getTeacherPassword());
                                    ed.apply();

                                    Intent dashIntent=new Intent(StudentLoginScreen.this,StudentDashboard.class);
                                    startActivity(dashIntent);
                                    finish();
                                }
                                else
                                {
                                    ed_password.setError("incorrect password");
                                    ed_password.requestFocus();
                                    ed_password.setText("");
                                    return;
                                }
                            }
                            else
                            {
                                ed_user.setError("incorrect Teacher ID");
                                ed_user.requestFocus();
                                ed_user.setText("");
                                return;
                            }


                        }
                        else
                        {
                            ed_user.setError("Student ID Doesn't exist");
                            ed_user.requestFocus();
                            ed_user.setText("");
                            return;
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });



    }
}