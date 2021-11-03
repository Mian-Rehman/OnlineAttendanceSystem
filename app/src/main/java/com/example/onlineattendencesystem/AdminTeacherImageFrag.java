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




    @SuppressWarnings("deprecation")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLARY_PIC_REQUEST&&resultCode== getActivity().RESULT_OK){
            imageUri=data.getData();
            Picasso.with(getActivity()).load(imageUri).into(imageFromGallary);
            setToFireStorage(imageUri);
        }
        
    }

    @SuppressWarnings("deprecation")
    public void openGallery(View view)
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, GALLARY_PIC_REQUEST);
    }

    @SuppressWarnings("deprecation")
    public void openCamera(View view)
    {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
    }


    private void setToFireStorage(Uri imageUri) {

        SharedPreferences sp=getActivity().getSharedPreferences("Teacher_Key", Context.MODE_PRIVATE);
        String key=sp.getString("teacherID","");


        StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("teacherImage");
        final StorageReference ImageReference=storageReference.child("112233");
        ImageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ImageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        DatabaseReference db= FirebaseDatabase.getInstance().getReference("teacherData")
                                .child("teacherId");
                        db.child("teacherImage").setValue(uri.toString());
                        Toast.makeText(getActivity(), "Successfully added to real time", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



}