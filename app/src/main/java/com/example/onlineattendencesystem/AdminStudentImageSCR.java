package com.example.onlineattendencesystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.onlineattendencesystem.Model.StudentImage;
import com.example.onlineattendencesystem.Model.TeacherImage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AdminStudentImageSCR extends AppCompatActivity {

    ImageView img_student,admin_studentImage_back;
    Button btn_gallery_student,btn_upload_student;

    Bitmap galbitmap_st;
    Uri imageuri2;

    String sImage2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_student_image_s_c_r);

        btn_gallery_student = findViewById(R.id.btn_gallery_student);
        btn_upload_student = findViewById(R.id.btn_upload_student);

        img_student = findViewById(R.id.img_student);
        admin_studentImage_back = findViewById(R.id.admin_studentImage_back);


        admin_studentImage_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(AdminStudentImageSCR.this,AdminDashboard.class);
                startActivity(backIntent);
                finish();
            }
        });



        if (ContextCompat.checkSelfPermission(AdminStudentImageSCR.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AdminStudentImageSCR.this, new String[]{
                    Manifest.permission.CAMERA


            }, 100);
        }


        btn_gallery_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 16);

                btn_upload_student.setVisibility(View.VISIBLE);

            }
        });





        btn_upload_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences=getSharedPreferences("STUDENT_IMAGE_KEY",MODE_PRIVATE);
                String studentKey=preferences.getString("STUDENT_ID_IMAGE","");
                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("studentImage");

               reference.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {

                       StudentImage studentImage=new StudentImage(studentKey,sImage2);
                       reference.child(studentKey).setValue(studentImage);
                       Toast.makeText(AdminStudentImageSCR.this, "Uploaded Complete", Toast.LENGTH_SHORT).show();
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });


            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==16 && resultCode==RESULT_OK && data!=null) {
            imageuri2 = data.getData();
            try {
                galbitmap_st = MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri2);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                galbitmap_st.compress(Bitmap.CompressFormat.JPEG,100,stream);
                byte[] bytes = stream.toByteArray();
                sImage2 = Base64.encodeToString(bytes,Base64.DEFAULT);
                img_student.setImageBitmap(galbitmap_st);

            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }

}