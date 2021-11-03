package com.example.onlineattendencesystem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlineattendencesystem.Model.TeacherClassData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminTeacherProfileFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminTeacherProfileFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminTeacherProfileFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminTeacherProfileFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminTeacherProfileFrag newInstance(String param1, String param2) {
        AdminTeacherProfileFrag fragment = new AdminTeacherProfileFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


//==========================================================================================
//==========================================================================================
//==========================================================================================

    EditText date,ed_name_t,ed_Id_t,ed_Email_t,ed_password_t,ed_tech_qual,ed_confirm_pass_t;
    Button btn_Save;

    FirebaseDatabase database;
    DatabaseReference myRef;

    Fragment temp=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_admin_teacher_profile, container, false);

       //EditText type casting
        date = v.findViewById(R.id.ed_DOB_Teacher);
        ed_name_t = v.findViewById(R.id.ed_name_t);
        ed_Id_t = v.findViewById(R.id.ed_Id_t);
        ed_Email_t = v.findViewById(R.id.ed_Email_t);
        ed_password_t = v.findViewById(R.id.ed_password_t);
        ed_tech_qual = v.findViewById(R.id.ed_tech_qual);
        ed_confirm_pass_t = v.findViewById(R.id.ed_confirm_pass_t);

        //Button Typecasting
        btn_Save = v.findViewById(R.id.btn_Save);

        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("teacherData");


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

                String teacherId=ed_Id_t.getText().toString();
                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("teacherData");

                Query query=reference.orderByChild("teacherId").equalTo(teacherId);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        if (snapshot.exists())
                        {

                            String checkTeacherId=snapshot.child(teacherId).child("teacherId").getValue(String.class);

                            if (checkTeacherId.equals(teacherId))
                            {
                                ed_Id_t.setError("Teacher ID already Exits");
                                ed_Id_t.requestFocus();
                                ed_Id_t.setText("");
                            }

                        }
                        else
                        {

                            String teacherName=ed_name_t.getText().toString();
                            String teacherId=ed_Id_t.getText().toString().trim();
                            String teacherEmail=ed_Email_t.getText().toString().trim();
                            String teacherDateOfBirth=date.getText().toString().trim();
                            String teacherQualification=ed_tech_qual.getText().toString().trim();
                            String teacherPassword=ed_password_t.getText().toString().trim();


                            if (TextUtils.isEmpty(teacherName))
                            {
                                ed_name_t.setError("invalid Name!");
                                ed_name_t.requestFocus();
                                ed_name_t.setText("");
                                return;
                            }

                            if (TextUtils.isEmpty(teacherId))
                            {
                                ed_Id_t.setError("invalid Teacher ID!");
                                ed_Id_t.requestFocus();
                                ed_Id_t.setText("");
                                return;
                            }

                            if (TextUtils.isEmpty(teacherEmail))
                            {

                                if (!Patterns.EMAIL_ADDRESS.matcher(teacherEmail).matches())
                                {
                                    ed_Email_t.setError("Enter Valid Email!");
                                    ed_Email_t.requestFocus();
                                    ed_Email_t.setText("");
                                    return;
                                }
                                else
                                {
                                    ed_Email_t.setError("invalid Email!");
                                    ed_Email_t.requestFocus();
                                    ed_Email_t.setText("");
                                    return;
                                }

                            }

                            if (TextUtils.isEmpty(teacherDateOfBirth))
                            {
                                date.setError("invalid Date of Birth");
                                date.requestFocus();
                                date.setText("");
                                return;
                            }

                            if (TextUtils.isEmpty(teacherQualification))
                            {
                                ed_tech_qual.setError("invalid Qualification");
                                ed_tech_qual.requestFocus();
                                ed_tech_qual.setText("");
                                return;
                            }

                            if (TextUtils.isEmpty(teacherPassword))
                            {
                                ed_password_t.setError("invalid Password");
                                ed_password_t.requestFocus();
                                ed_password_t.setText("");
                                return;
                            }

                            if (teacherPassword.equals(ed_confirm_pass_t.getText().toString()))
                            {
                                TeacherClassData access=new TeacherClassData(teacherName,teacherId,teacherEmail,teacherDateOfBirth,teacherQualification,teacherPassword);
                                String key= myRef.child(teacherId).getKey();
                                myRef.child(key).setValue(access);
                                ed_name_t.setText("");
                                ed_name_t.requestFocus();
                                ed_Id_t.setText("");
                                ed_Email_t.setText("");
                                date.setText("");
                                ed_tech_qual.setText("");
                                ed_password_t.setText("");
                                ed_confirm_pass_t.setText("");
                                Toast.makeText(getActivity(), "Profile Saved!", Toast.LENGTH_SHORT).show();
                                temp=new AdminTeacherImageFrag();
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