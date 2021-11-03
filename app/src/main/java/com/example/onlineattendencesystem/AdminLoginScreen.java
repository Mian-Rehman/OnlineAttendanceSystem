package com.example.onlineattendencesystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineattendencesystem.Model.AdminAccess;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginScreen extends AppCompatActivity {

    ImageView admin_back;
    TextView tv_forgot_password;
    EditText ed_user,ed_password;
    Button btn_Login;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_screen);

        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("AdminAccess");


        //Type casting Button
        btn_Login=findViewById(R.id.btn_Login);


        //Type Casting EditText
        ed_user=findViewById(R.id.ed_user);
        ed_password=findViewById(R.id.ed_password);

        //Type casting Textview
        tv_forgot_password=findViewById(R.id.tv_forgot_password);

        //image type casting
        admin_back=findViewById(R.id.admin_back);



        admin_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminLoginScreen.this,ConfirmScreen.class);
                startActivity(intent);
                finish();
            }
        });

        SharedPreferences sp=getSharedPreferences("USERDATA",MODE_PRIVATE);





     btn_Login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

      myRef.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {

              String checkuser=snapshot.child("adminUsername").getValue(String.class);
              String checkPass=snapshot.child("adminPassword").getValue(String.class);

              if (ed_user.getText().toString().equals(checkuser))
              {
                  if (ed_password.getText().toString().equals(checkPass))
                  {
                      Intent dashIntent=new Intent(AdminLoginScreen.this,AdminDashboard.class);
                      startActivity(dashIntent);
                      finish();
                  }
                  else
                  {
                     ed_password.setError("Invalid password!");
                     ed_password.requestFocus();
                     ed_password.setText("");
                     return;
                  }
              }
              else
              {
                  ed_user.setError("invalid Admin");
                  ed_user.requestFocus();
                  ed_user.setText("");
                  return;
              }

          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });

        }
    });


     tv_forgot_password.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             AlertDialog.Builder builder=new AlertDialog.Builder(AdminLoginScreen.this);
             View mycustomview=getLayoutInflater().inflate(R.layout.customdialogbox,null);

             Button btn_confirm=mycustomview.findViewById(R.id.btn_confirm);
             EditText ed_cuurent_password=mycustomview.findViewById(R.id.ed_current_Password);
             EditText ed_new_password=mycustomview.findViewById(R.id.ed_new_password);
             EditText ed_new_confirm_password=mycustomview.findViewById(R.id.ed_new_confirm_password);

             builder.setView(mycustomview);
             AlertDialog myalter=builder.create();
             myalter.setCancelable(true);

             btn_confirm.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {


                     String checkCurrent=ed_cuurent_password.getText().toString().trim();
                     String checkPass=ed_new_password.getText().toString().trim();
                     String checkConfirm=ed_new_confirm_password.getText().toString().trim();

                     if (TextUtils.isEmpty(checkCurrent))
                     {
                         ed_cuurent_password.setError("Enter current password");
                         ed_cuurent_password.requestFocus();
                         ed_cuurent_password.setText("");
                         return;
                     }
                     if (TextUtils.isEmpty(checkPass))
                     {
                         ed_new_password.setError("Enter New password");
                         ed_new_password.requestFocus();
                         ed_new_password.setText("");
                         return;
                     }

                     if (TextUtils.isEmpty(checkConfirm))
                     {
                         ed_new_confirm_password.setError("Please Confirm password");
                         ed_new_confirm_password.requestFocus();
                         ed_new_confirm_password.setText("");
                         return;
                     }

                     myRef.addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot snapshot) {

                           String user=snapshot.child("adminUsername").getValue(String.class);
                             if (user.equals(ed_cuurent_password.getText().toString().trim()))
                             {

                                 if (ed_new_password.getText().toString().trim().equals(ed_new_confirm_password.getText().toString().trim()
                                 ))
                                 {


                                     myRef.child("adminPassword").setValue(ed_new_password.getText().toString());
                                     Toast.makeText(AdminLoginScreen.this, "Password Changed", Toast.LENGTH_SHORT).show();


                                 }
                                 else
                                 {
                                     ed_new_password.setError("Password Not match");
                                     ed_new_password.requestFocus();
                                     ed_new_password.setText("");
                                     ed_new_confirm_password.setText("");
                                     return;
                                 }

                             }
                             else
                             {
                                 ed_cuurent_password.setError("Invalid password");
                                 ed_cuurent_password.requestFocus();
                                 ed_cuurent_password.setText("");
                                 return;
                             }

                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError error) {

                         }
                     });



                 }
             });
             myalter.show();

         }
     });


    }
}