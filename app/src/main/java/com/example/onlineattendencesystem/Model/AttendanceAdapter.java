package com.example.onlineattendencesystem.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineattendencesystem.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import android.content.Context;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyAttendanceHolder>{

    private Context context;
    private List<StudentAttendaceClass> mDataList;

    public AttendanceAdapter(Context context, List<StudentAttendaceClass> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public MyAttendanceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View myview=LayoutInflater.from(context).inflate(R.layout.attendance_list,parent,false);


        return new MyAttendanceHolder(myview);

    }

    @Override
    public void onBindViewHolder(@NonNull MyAttendanceHolder holder, int position) {


        StudentAttendaceClass classData=mDataList.get(position);

        holder.tv_att_date.setText("Date: " + classData.getCurrentDate());
        holder.tv_att_st_id.setText("Student ID: " + classData.getStudentId());
        holder.tv_att_time.setText("Time: " + classData.getCurrentTime());
        holder.tv_att.setText("Attendance: " + classData.getStudentPresent());


    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class MyAttendanceHolder extends RecyclerView.ViewHolder{

        TextView tv_att_date,tv_att_st_id,tv_att_time,tv_att;

        public MyAttendanceHolder(@NonNull View itemView) {
            super(itemView);

            tv_att_date=itemView.findViewById(R.id.tv_att_date);
            tv_att_st_id=itemView.findViewById(R.id.tv_att_st_id);
            tv_att_time=itemView.findViewById(R.id.tv_att_time);
            tv_att=itemView.findViewById(R.id.tv_att);

        }
    }

}
