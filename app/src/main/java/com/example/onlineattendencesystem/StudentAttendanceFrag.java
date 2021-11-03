package com.example.onlineattendencesystem;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class StudentAttendanceFrag extends Fragment {

    ImageView StudentImageFirebase;
    Button btn_Student_verify;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_student_attendance, container, false);

        StudentImageFirebase=v.findViewById(R.id.StudentImageFirebase);
        btn_Student_verify=v.findViewById(R.id.btn_Student_verify);


        return v;
    }
}