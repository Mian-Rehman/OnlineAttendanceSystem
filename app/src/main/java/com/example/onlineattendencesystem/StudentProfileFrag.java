package com.example.onlineattendencesystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class StudentProfileFrag extends Fragment {



    TextView tv_student_name,tv_student_Id,tv_student_DOB,tv_student_Qual,tv_student_pass;



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

        SharedPreferences sp=getActivity().getSharedPreferences("TEACHER_DATA", Context.MODE_PRIVATE);

        tv_student_name.setText(sp.getString("studentName",""));
        tv_student_Id.setText(sp.getString("studentID",""));
        tv_student_DOB.setText(sp.getString("studentDOB",""));
        tv_student_Qual.setText(sp.getString("studentQualification",""));
        tv_student_pass.setText(sp.getString("studentPassword",""));




   return v;
    }
}