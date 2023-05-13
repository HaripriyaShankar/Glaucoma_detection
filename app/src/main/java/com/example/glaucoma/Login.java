package com.example.glaucoma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.glaucoma.MainActivity;

public class Login extends AppCompatActivity {
    TextInputEditText editUsname,editPass;
    Button LogBut;
    TextView RegNewBut;
    DatabaseReference data = FirebaseDatabase.getInstance().getReferenceFromUrl("https://glaucoma-50541-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editUsname = findViewById(R.id.usname);
        editPass = findViewById(R.id.password);

        LogBut = findViewById(R.id.login_btn);
        RegNewBut = findViewById(R.id.regnow);

        LogBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usname,pass;
                usname = editUsname.getText().toString();
                pass = editPass.getText().toString();

                if(usname.isEmpty() || pass.isEmpty()){
                    Toast.makeText(Login.this,"Incorrect email or password",Toast.LENGTH_SHORT).show();
                }
                else{
                    data.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(usname)){
                                final String getPass = snapshot.child(usname).child("Pass").getValue(String.class);
                                if(getPass.equals(pass)){
                                    Toast.makeText(Login.this,"Login Successfull",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this, MainActivity.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(Login.this,"Incorrect Password",Toast.LENGTH_SHORT).show();

                                }
                            }
                            else{
                                Toast.makeText(Login.this,"Incorrect UserName",Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        RegNewBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Registration.class));
            }
        });
    }
}