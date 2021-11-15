package com.example.onlineattendencesystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;


public class TeacherMyProfileFrag extends Fragment {

    public Bitmap tec_prof_bitmap;
    String teacimage;
    TextView tv_teacher_name,tv_teacher_Id,tv_teacher_email,tv_teacher_DOB,tv_teacher_Qual,tv_teacher_pass;
    ImageView img_prof_tech;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_teacher_my_profile, container, false);


        tv_teacher_name=v.findViewById(R.id.tv_teacher_name);
        tv_teacher_Id=v.findViewById(R.id.tv_teacher_Id);
        tv_teacher_email=v.findViewById(R.id.tv_teacher_email);
        tv_teacher_DOB=v.findViewById(R.id.tv_teacher_DOB);
        tv_teacher_Qual=v.findViewById(R.id.tv_teacher_Qual);
        tv_teacher_pass=v.findViewById(R.id.tv_teacher_pass);

        img_prof_tech=v.findViewById(R.id.img_prof_tech);


        SharedPreferences sp=getActivity().getSharedPreferences("TEACHER_DATA", Context.MODE_PRIVATE);

        tv_teacher_name.setText(sp.getString("teacherName",""));
        tv_teacher_Id.setText(sp.getString("teacherID",""));
        tv_teacher_email.setText(sp.getString("teacherEmail",""));
        tv_teacher_DOB.setText(sp.getString("teacherDOB",""));
        tv_teacher_Qual.setText(sp.getString("teacherQualification",""));
        tv_teacher_pass.setText(sp.getString("teacherPassword",""));

        retrieveimage();

  return v;

   }

    private void retrieveimage()
    {
        SharedPreferences sp=getActivity().getSharedPreferences("TEACHER_DATA",Context.MODE_PRIVATE);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("teacherImage");
        String teacherId= sp.getString("teacherID","");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                teacimage = snapshot.child(teacherId).child("newImage").getValue(String.class);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bytes = byteArrayOutputStream.toByteArray();
                bytes= Base64.decode(teacimage, Base64.DEFAULT);
                tec_prof_bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                img_prof_tech.setImageBitmap(tec_prof_bitmap);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}