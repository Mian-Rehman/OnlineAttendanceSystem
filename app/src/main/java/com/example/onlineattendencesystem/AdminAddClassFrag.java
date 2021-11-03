package com.example.onlineattendencesystem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlineattendencesystem.Model.AddClassData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminAddClassFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminAddClassFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminAddClassFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminAddClassFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminAddClassFrag newInstance(String param1, String param2) {
        AdminAddClassFrag fragment = new AdminAddClassFrag();
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


 //====================================================================================
 //====================================================================================
 //====================================================================================

    EditText ed_ClassSession,ed_addClass;
    Button btn_Save;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_admin_add_class, container, false);

        //EditTxt Typecasting
        ed_ClassSession=v.findViewById(R.id.ed_ClassSession);
        ed_addClass=v.findViewById(R.id.ed_addClass);

        //Button typecasting
        btn_Save=v.findViewById(R.id.btn_Save);

        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("classesName");


        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String checkClassName=ed_addClass.getText().toString();
                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("classesName");

                Query query=reference.orderByChild("addClass").equalTo(checkClassName);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists())
                        {

                            String checkName=snapshot.child(checkClassName).child("addClass").getValue(String.class);

                            if (checkName.equals(checkClassName))
                            {
                                Toast.makeText(getActivity(), "Class ALready Added", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                        {
                            String classYear=ed_ClassSession.getText().toString();
                            String addClass=ed_addClass.getText().toString();


                            AddClassData data=new AddClassData(classYear,addClass);
                            String key= myRef.child(addClass).getKey();
                            myRef.child(key).setValue(data);


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });


  return  v;

    }
}