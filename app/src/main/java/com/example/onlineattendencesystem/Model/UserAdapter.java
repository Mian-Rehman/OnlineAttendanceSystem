package com.example.onlineattendencesystem.Model;


import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.example.onlineattendencesystem.AdminAddStudentProf;
import com.example.onlineattendencesystem.AdminShowStudents;
import com.example.onlineattendencesystem.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{

    private Context context;
    private List<AddClassData> mDatalist;

    public UserAdapter(Context context, List<AddClassData> mDatalist) {
        this.context = context;
        this.mDatalist = mDatalist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview= LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);



        return new MyViewHolder(myview);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        AddClassData user=mDatalist.get(position);

        SharedPreferences preferences=context.getSharedPreferences("SHOW_STUDENT",Context.MODE_PRIVATE);
        String stdCheck= preferences.getString("SHOW_LIST","");

        String clasName=user.getAddClass();



        holder.tv_className.setText("Class Session: "+user.getClassYear());
        holder.tv_classYear.setText("Class Name: "+user.getAddClass());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Intent intent=new Intent(context, AdminAddStudentProf.class);
                    intent.putExtra("ClassName",user.getAddClass());
                    context.startActivity(intent);
                    Toast.makeText(context, "" + clasName, Toast.LENGTH_SHORT).show();



            }
        });


    }

    @Override
    public int getItemCount() {
        return mDatalist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_className,tv_classYear;
        View view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            tv_className=itemView.findViewById(R.id.tv_className);
            tv_classYear=itemView.findViewById(R.id.tv_classYear);
            view=itemView;


        }
    }
}
