package com.example.onlineattendencesystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AdminTeacherImageFrag extends Fragment {



    ImageView imageFromGallary,imageFromDatabase;
    Button btn_gallery,btn_camera,btn_upload;

    private int CAMERA_PIC_REQUEST=100;
    private int GALLARY_PIC_REQUEST=200;
    Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_admin_teacher_image, container, false);


        imageFromGallary=v.findViewById(R.id.img_student);
        imageFromDatabase=v.findViewById(R.id.imageFromDatabase);

        btn_gallery=v.findViewById(R.id.btn_gallery);
        btn_camera=v.findViewById(R.id.btn_camera);
        btn_upload=v.findViewById(R.id.btn_upload);





        return v;
    }


}