package com.example.onlineattendencesystem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.onlineattendencesystem.Model.StudentDataClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class TeacherAddStudentFrag extends Fragment {




    EditText date,ed_name_st,ed_Id_st,ed_password_t,ed_tech_qual,ed_confirm_pass_t;
    Button btn_Save;

    ImageView img_student;

    FirebaseDatabase database;
    DatabaseReference myRef;

    Fragment temp=null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_teacher_add_student, container, false);

        //EditText type casting
        date = v.findViewById(R.id.ed_DOB_Teacher);
        ed_name_st = v.findViewById(R.id.ed_name_t);
        ed_Id_st = v.findViewById(R.id.ed_Id_t);
        ed_password_t = v.findViewById(R.id.ed_password_t);
        ed_tech_qual = v.findViewById(R.id.ed_tech_qual);
        ed_confirm_pass_t = v.findViewById(R.id.ed_confirm_pass_t);

        //Button typecasting
        btn_Save = v.findViewById(R.id.btn_Save);



        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("studentData");


        date.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);

                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    date.setText(current);
                    date.setSelection(sel < current.length() ? sel : current.length());



                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });


        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String studentId=ed_Id_st.getText().toString();
                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("studentData");

                Query query=reference.orderByChild("studentId").equalTo(studentId);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists())
                        {
                            String checkStudentId=snapshot.child(studentId).child("studentId").getValue(String.class);

                            if (checkStudentId.equals(studentId))
                            {
                                ed_Id_st.setError("Teacher ID already Exits");
                                ed_Id_st.requestFocus();
                                ed_Id_st.setText("");
                            }
                        }
                        else
                        {
                            String studentName=ed_name_st.getText().toString();
                            String studentId=ed_Id_st.getText().toString().trim();
                            String studentDateOfBirth=date.getText().toString().trim();
                            String studentQualification=ed_tech_qual.getText().toString().trim();
                            String studentPassword=ed_password_t.getText().toString().trim();

                            if (TextUtils.isEmpty(studentName))
                            {
                                ed_name_st.setError("invalid Name!");
                                ed_name_st.requestFocus();
                                ed_name_st.setText("");
                                return;
                            }

                            if (TextUtils.isEmpty(studentId))
                            {
                                ed_Id_st.setError("invalid Student ID!");
                                ed_Id_st.requestFocus();
                                ed_Id_st.setText("");
                                return;
                            }

                            if (TextUtils.isEmpty(studentDateOfBirth))
                            {
                                date.setError("invalid Date of Birth");
                                date.requestFocus();
                                date.setText("");
                                return;
                            }

                            if (TextUtils.isEmpty(studentQualification))
                            {
                                ed_tech_qual.setError("invalid Qualification");
                                ed_tech_qual.requestFocus();
                                ed_tech_qual.setText("");
                                return;
                            }

                            if (TextUtils.isEmpty(studentPassword))
                            {
                                ed_password_t.setError("invalid Password");
                                ed_password_t.requestFocus();
                                ed_password_t.setText("");
                                return;
                            }

                            if (studentPassword.equals(ed_confirm_pass_t.getText().toString()))
                            {
                                StudentDataClass dataClass=new StudentDataClass(studentName,studentId,studentDateOfBirth,studentQualification,studentPassword);
                                String key= myRef.child(studentId).getKey();
                                myRef.child(key).setValue(dataClass);

                                ed_name_st.setText("");
                                ed_name_st.requestFocus();
                                ed_Id_st.setText("");
                                date.setText("");
                                ed_tech_qual.setText("");
                                ed_password_t.setText("");
                                ed_confirm_pass_t.setText("");
                                Toast.makeText(getActivity(), "Profile Saved!", Toast.LENGTH_SHORT).show();
                                temp=new TeacherStudentImageFrag();
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,temp).commit();
                            }
                            else
                            {
                                ed_password_t.setError("Password Doesn't Match");
                                ed_password_t.requestFocus();
                                ed_password_t.setText("");
                                ed_confirm_pass_t.setText("");
                            }

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    return v;
    }
}