package com.example.onlineattendencesystem.Model;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineattendencesystem.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SrudentAdapter extends FirebaseRecyclerAdapter<StudentDataClass,SrudentAdapter.MyViewHolderStudent> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public SrudentAdapter(@NonNull FirebaseRecyclerOptions<StudentDataClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolderStudent holder, int position, @NonNull StudentDataClass model) {


        holder.tv_studentID.setText("Student ID: " + model.getStudentId());
        holder.tv_studentName.setText("Student Name: " + model.getStudentName());
        holder.tv_studentClass.setText("Student Class: " + model.getClassName());
        holder.tv_studentDOB.setText("Student DOB: " + model.getStudentDateOfBirth());
        holder.tv_studentPassword.setText("Student Password: " + model.getStudentPassword());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogPlus dialogPlus= DialogPlus.newDialog(holder.tv_studentID.getContext())
                        .setContentHolder(new ViewHolder(R.layout.editdialog))
                        .setExpanded(true,1100)
                        .create();

                View myview=dialogPlus.getHolderView();
                final EditText name=myview.findViewById(R.id.st_name);
                final EditText DOB=myview.findViewById(R.id.st_dob);
                final EditText password=myview.findViewById(R.id.st_pass);
                Button submit=myview.findViewById(R.id.st_submit);

                name.setText(model.getStudentName());
                DOB.setText(model.getStudentDateOfBirth());
                password.setText(model.getStudentPassword());

                dialogPlus.show();



                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String,Object> map=new HashMap<>();
                        map.put("studentName",name.getText().toString());
                        map.put("studentDOB",DOB.getText().toString());
                        map.put("studentPassword",password.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("studentData")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });

                    }
                });

            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder=new AlertDialog.Builder(holder.tv_studentID.getContext());
                builder.setTitle("Delete Student Data");
                builder.setMessage("Press Yes or No?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("studentData")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public MyViewHolderStudent onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list,parent,false);
        return new MyViewHolderStudent(view);
    }

    public class MyViewHolderStudent extends RecyclerView.ViewHolder
    {

        TextView tv_studentID,tv_studentName,tv_studentClass,tv_studentDOB,tv_studentPassword;

        ImageView edit,delete;
        public MyViewHolderStudent(@NonNull View itemView) {
            super(itemView);

            tv_studentID=itemView.findViewById(R.id.tv_studentID);
            tv_studentName=itemView.findViewById(R.id.tv_studentName);
            tv_studentClass=itemView.findViewById(R.id.tv_studentClass);
            tv_studentDOB=itemView.findViewById(R.id.tv_studentDOB);
            tv_studentPassword=itemView.findViewById(R.id.tv_studentPassword);
            edit=itemView.findViewById(R.id.img_update_st);
            delete=itemView.findViewById(R.id.img_delete_st);

        }
    }

}
