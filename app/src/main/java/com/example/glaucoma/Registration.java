package com.example.glaucoma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registration extends AppCompatActivity {
    TextInputEditText editTextName,editTextEmail,editTextNo,editTextUsname,editTextPass,editTextRepass;
    Button regBut;
    TextView LoginBtn;
    DatabaseReference data = FirebaseDatabase.getInstance().getReferenceFromUrl("https://glaucoma-50541-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editTextName = findViewById(R.id.name);
        editTextEmail = findViewById(R.id.regemail);
        editTextNo = findViewById(R.id.phone);
        editTextPass = findViewById(R.id.pass);
        editTextUsname = findViewById(R.id.uname);
        editTextRepass = findViewById(R.id.reenter);
        regBut = findViewById(R.id.btn_signup);
        LoginBtn = findViewById(R.id.loginnow);

        regBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = editTextName.getText().toString();
                final String email = editTextEmail.getText().toString();
                final String phno = editTextNo.getText().toString();
                final String username = editTextUsname.getText().toString();
                final String pass = editTextPass.getText().toString();
                final String renter = editTextRepass.getText().toString();

                if(name.isEmpty()||email.isEmpty()||username.isEmpty()||phno.isEmpty()||pass.isEmpty()||renter.isEmpty()){
                    Toast.makeText(Registration.this,"Please fill all the fields",Toast.LENGTH_SHORT).show();
                }
                else if(!pass.equals(renter)){
                    Toast.makeText(Registration.this,"Password doesn't match",Toast.LENGTH_SHORT).show();
                }
                else{
                    data.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(username)){
                                Toast.makeText(Registration.this,"Username exists. Try again ",Toast.LENGTH_SHORT).show();

                            }
                            else{
                                data.child("users").child(username).child("Name").setValue(name);
                                data.child("users").child(username).child("Email").setValue(email);
                                data.child("users").child(username).child("Mobno").setValue(phno);
                                data.child("users").child(username).child("Pass").setValue(pass);
                                data.child("users").child(username).child("Repass").setValue(renter);
                                Toast.makeText(Registration.this,"User Registered",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });
        LoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();

            }


    });


    }
}