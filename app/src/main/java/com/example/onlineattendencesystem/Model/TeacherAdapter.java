package com.example.onlineattendencesystem.Model;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.Map;

public class TeacherAdapter extends FirebaseRecyclerAdapter<TeacherClassData,TeacherAdapter.MyViewHolderTeacher>
{

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TeacherAdapter(@NonNull FirebaseRecyclerOptions<TeacherClassData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolderTeacher holder, int position, @NonNull TeacherClassData model) {

        holder.tv_teacherID.setText("Teacher ID: " + model.getTeacherId());
        holder.tv_teacherName.setText("Teacher Name: " + model.getTeacherName());
        holder.tv_teacherDOB.setText("Teacher DOB: " + model.getTeacherDateOfBirth());
        holder.tv_teacherQua.setText("Teacher DOB: " + model.getTeacherQualification());
        holder.tv_teacherPassword.setText("Teacher password: " + model.getTeacherPassword());


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogPlus dialogPlus= DialogPlus.newDialog(holder.tv_teacherID.getContext())
                        .setContentHolder(new ViewHolder(R.layout.teacherdialog))
                        .setExpanded(true,1100)
                        .create();

                View myview=dialogPlus.getHolderView();
                final EditText name=myview.findViewById(R.id.tech_name);
                final EditText DOB=myview.findViewById(R.id.tech_dob);
                final EditText Qualification=myview.findViewById(R.id.tech_qua);
                final EditText password=myview.findViewById(R.id.tech_pass);
                Button submit=myview.findViewById(R.id.tech_submit);

                name.setText(model.getTeacherName());
                DOB.setText(model.getTeacherDateOfBirth());
                Qualification.setText(model.getTeacherQualification());
                password.setText(model.getTeacherPassword());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String,Object> map=new HashMap<>();
                        map.put("teacherName",name.getText().toString());
                        map.put("teacherDOB",DOB.getText().toString());
                        map.put("teacherQualification",Qualification.getText().toString());
                        map.put("teacherPassword",password.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("teacherData")
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


                AlertDialog.Builder builder=new AlertDialog.Builder(holder.tv_teacherID.getContext());
                builder.setTitle("Delete Teacher Data");
                builder.setMessage("Press Yes or No?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("teacherData")
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
    public MyViewHolderTeacher onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_list,parent,false);
        return new MyViewHolderTeacher(view);
    }




    public class MyViewHolderTeacher extends RecyclerView.ViewHolder {

        TextView tv_teacherID,tv_teacherName,tv_teacherDOB,tv_teacherQua,tv_teacherPassword;
        ImageView edit,delete;

        public MyViewHolderTeacher(@NonNull View itemView) {
            super(itemView);

            tv_teacherID=itemView.findViewById(R.id.tv_teacherID);
            tv_teacherName=itemView.findViewById(R.id.tv_teacherName);
            tv_teacherDOB=itemView.findViewById(R.id.tv_teacherDOB);
            tv_teacherQua=itemView.findViewById(R.id.tv_teacherQua);
            tv_teacherPassword=itemView.findViewById(R.id.tv_teacherPassword);

            edit=itemView.findViewById(R.id.img_update_tech);
            delete=itemView.findViewById(R.id.img_delete_tech);


        }
    }

}
