package com.example.onlineattendencesystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class TeacherMyProfileFrag extends Fragment {



    TextView tv_teacher_name,tv_teacher_Id,tv_teacher_email,tv_teacher_DOB,tv_teacher_Qual,tv_teacher_pass;

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


        SharedPreferences sp=getActivity().getSharedPreferences("TEACHER_DATA", Context.MODE_PRIVATE);

        tv_teacher_name.setText(sp.getString("teacherName",""));
        tv_teacher_Id.setText(sp.getString("teacherID",""));
        tv_teacher_email.setText(sp.getString("teacherEmail",""));
        tv_teacher_DOB.setText(sp.getString("teacherDOB",""));
        tv_teacher_Qual.setText(sp.getString("teacherQualification",""));
        tv_teacher_pass.setText(sp.getString("teacherPassword",""));


  return v;

   }
}