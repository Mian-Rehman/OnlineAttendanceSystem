package com.example.onlineattendencesystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.common.TensorOperator;
import org.tensorflow.lite.support.common.ops.NormalizeOp;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

public class StudentAttendanceSCR extends AppCompatActivity {


    protected Interpreter tflite;
    private  int imageSizeX;
    private  int imageSizeY;

    private static final float IMAGE_MEAN = 0.0f;
    private static final float IMAGE_STD = 1.0f;

    public Bitmap oribitmap,testbitmap;
    public static Bitmap cropped;
    Uri imageuri;

    float[][] ori_embedding = new float[1][128];
    float[][] test_embedding = new float[1][128];

    String studimage;

    TextView result_text;
    ImageView imagefromcam,imageFromDatabase,stdAtt_back;
    Button verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance_s_c_r);

        verify=findViewById(R.id.verify);
        result_text=findViewById(R.id.result);
        imagefromcam=findViewById(R.id.imagefromcam);
        imageFromDatabase=findViewById(R.id.imageFromDatabase);
        stdAtt_back=findViewById(R.id.stdAtt_back);


        stdAtt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(StudentAttendanceSCR.this,StudentDashboard.class);
                startActivity(backIntent);
                finish();
            }
        });


        SharedPreferences sp=getSharedPreferences("STUDENT_DATA",MODE_PRIVATE);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("studentImage");
        String studentID=sp.getString("studentID","");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studimage = snapshot.child(studentID).child("newImage").getValue(String.class);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bytes = byteArrayOutputStream.toByteArray();
                bytes= Base64.decode(studimage, Base64.DEFAULT);
                oribitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imageFromDatabase.setImageBitmap(oribitmap);

                SharedPreferences preferences=getSharedPreferences("STD_ATT_IMF_URL",MODE_PRIVATE);
                SharedPreferences.Editor ed=preferences.edit();
                ed.putString("STDATT_LINK",studimage);
                ed.apply();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (ContextCompat.checkSelfPermission(StudentAttendanceSCR.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(StudentAttendanceSCR.this, new String[]{
                    Manifest.permission.CAMERA

            }, 100);
        }
        try{
            tflite=new Interpreter(loadmodelfile(this));
        }catch (Exception e) {
            e.printStackTrace();
        }


        imagefromcam.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);
            }
        });

        retrieveimage();

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double distance=calculate_distance(ori_embedding,test_embedding);

                if(distance<6.0)
                {

                }

                else
                {
                    result_text.setText("Result : Same Faces");
                    Intent comp_intent=new Intent(StudentAttendanceSCR.this,StudentAttendanceCompleteSCR.class);
                    startActivity(comp_intent);

                 //   Toast.makeText(StudentAttendanceSCR.this, "Face Not Match", Toast.LENGTH_SHORT).show();
                  //  result_text.setText("Result : Face Not Match Try Again");


                    //  Intent back_intent=new Intent(StudentAttendanceSCR.this,StudentDashboard.class);
                  //  startActivity(back_intent);
                  //  finish();

                }

            }

        });

    }

    private void retrieveimage()
    {
        SharedPreferences sp=getSharedPreferences("STUDENT_DATA",MODE_PRIVATE);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("studentImage");
        String studentId= sp.getString("studentID","");

        //listner for retrive image from database
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studimage = snapshot.child(studentId).child("newImage").getValue(String.class);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bytes = byteArrayOutputStream.toByteArray();
                bytes= Base64.decode(studimage, Base64.DEFAULT);
                oribitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imageFromDatabase.setImageBitmap(oribitmap);
                face_detector(oribitmap,"original");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private double calculate_distance(float[][] ori_embedding, float[][] test_embedding) {
        double sum =0.0;
        for(int i=0;i<128;i++){
            sum=sum+Math.pow((ori_embedding[0][i]-test_embedding[0][i]),2.0);
        }
        return Math.sqrt(sum);
    }
    private MappedByteBuffer loadmodelfile(Activity activity) throws IOException {
        AssetFileDescriptor fileDescriptor=activity.getAssets().openFd("Qfacenet.tflite");
        FileInputStream inputStream=new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel=inputStream.getChannel();
        long startoffset = fileDescriptor.getStartOffset();
        long declaredLength=fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startoffset,declaredLength);
    }

    private TensorImage loadImage(final Bitmap bitmap, TensorImage inputImageBuffer ) {
        // Loads bitmap into a TensorImage.
        inputImageBuffer.load(bitmap);

        // Creates processor for the TensorImage.
        int cropSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
        // TODO(b/143564309): Fuse ops inside ImageProcessor.
        ImageProcessor imageProcessor =
                new ImageProcessor.Builder()
                        .add(new ResizeWithCropOrPadOp(cropSize, cropSize))
                        .add(new ResizeOp(imageSizeX, imageSizeY, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
                        .add(getPreprocessNormalizeOp())
                        .build();
        return imageProcessor.process(inputImageBuffer);
    }
    private TensorOperator getPreprocessNormalizeOp() {
        return new NormalizeOp(IMAGE_MEAN, IMAGE_STD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==RESULT_OK && data!=null) {
            imageuri = data.getData();
            testbitmap = (Bitmap) data.getExtras().get("data");
            imagefromcam.setImageBitmap(testbitmap);
            face_detector(testbitmap,"test");
        }
    }
    public void face_detector(final Bitmap bitmap, final String imagetype){

        final InputImage image = InputImage.fromBitmap(bitmap,0);
        FaceDetector detector = FaceDetection.getClient();
        detector.process(image)
                .addOnSuccessListener(
                        new OnSuccessListener<List<Face>>() {
                            @Override
                            public void onSuccess(List<Face> faces) {
                                // Task completed successfully
                                for (Face face : faces) {
                                    Rect bounds = face.getBoundingBox();
                                    cropped = Bitmap.createBitmap(bitmap, bounds.left, bounds.top,
                                            bounds.width(), bounds.height());
                                    get_embaddings(cropped,imagetype);
                                }
                            }

                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
    }
    public void get_embaddings(Bitmap bitmap,String imagetype){

        TensorImage inputImageBuffer;
        float[][] embedding = new float[1][128];

        int imageTensorIndex = 0;
        int[] imageShape = tflite.getInputTensor(imageTensorIndex).shape(); // {1, height, width, 3}
        imageSizeY = imageShape[1];
        imageSizeX = imageShape[2];
        DataType imageDataType = tflite.getInputTensor(imageTensorIndex).dataType();

        inputImageBuffer = new TensorImage(imageDataType);

        inputImageBuffer = loadImage(bitmap,inputImageBuffer);

        tflite.run(inputImageBuffer.getBuffer(),embedding);

        if(imagetype.equals("original"))
            ori_embedding=embedding;
        else if (imagetype.equals("test"))
            test_embedding=embedding;
    }
}