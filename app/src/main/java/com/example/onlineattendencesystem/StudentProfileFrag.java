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


public class StudentProfileFrag extends Fragment {

    String studimage;
    public Bitmap prf_bitmap;

    TextView tv_student_name,tv_student_Id,tv_student_DOB,tv_student_Qual,tv_student_pass;

    ImageView img_prof_stu;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_student_profile, container, false);


        tv_student_name=v.findViewById(R.id.tv_student_name);
        tv_student_Id=v.findViewById(R.id.tv_student_Id);
        tv_student_DOB=v.findViewById(R.id.tv_student_DOB);
        tv_student_Qual=v.findViewById(R.id.tv_student_Qual);
        tv_student_pass=v.findViewById(R.id.tv_student_pass);

        img_prof_stu=v.findViewById(R.id.img_prof_stu);

        SharedPreferences sp=getActivity().getSharedPreferences("STUDENT_DATA", Context.MODE_PRIVATE);

        tv_student_name.setText(sp.getString("studentName",""));
        tv_student_Id.setText(sp.getString("studentID",""));
        tv_student_DOB.setText(sp.getString("studentDOB",""));
        tv_student_Qual.setText(sp.getString("studentQualification",""));
        tv_student_pass.setText(sp.getString("studentPassword",""));

        retrieveimage();


   return v;
    }

    private void retrieveimage()
    {
        SharedPreferences sp=getActivity().getSharedPreferences("STUDENT_DATA",Context.MODE_PRIVATE);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("studentImage");
        String studentId= sp.getString("studentID","");

        //listner for retrive image from database
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                studimage = snapshot.child(studentId).child("newImage").getValue(String.class);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bytes = byteArrayOutputStream.toByteArray();
                bytes= Base64.decode(studimage, Base64.DEFAULT);
                prf_bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                img_prof_stu.setImageBitmap(prf_bitmap);

                SharedPreferences preferences=getActivity().getSharedPreferences("STD_ATT_IMF_URL",Context.MODE_PRIVATE);
                SharedPreferences.Editor ed=preferences.edit();
                ed.putString("STDATT_LINK",studimage);
                ed.apply();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }
}