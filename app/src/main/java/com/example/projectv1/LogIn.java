package com.example.projectv1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogIn extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email;
    EditText password;
    String phone;
    SharedPreferences sp;

// ...
// Initialize Firebase Auth
    /*@Override
    public void onStart(){
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mAuth = FirebaseAuth.getInstance();
        password = findViewById(R.id.signUpPassword);
        email = findViewById(R.id.signUpEmail);
        sp = getSharedPreferences("User_Details",MODE_PRIVATE);
        String e = sp.getString("email","not found");
        String p = sp.getString("password","not found");
        phone = "not found";

        if(!e.equals("not found") && !p.equals("not found")){
            mAuth.signInWithEmailAndPassword(e, p)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(getApplicationContext(), MainMenu.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    }

    public void signUpPage(View view) {
        Intent i = new Intent(this, SignUp.class);
        startActivity(i);
    }


    public void logIn(View view) {
        String e = email.getText().toString();
        String p = password.getText().toString();
        if(e.equals("")|| p.equals("")){
            Toast.makeText(getApplicationContext(), "Please type both Email and Password.", Toast.LENGTH_SHORT).show();
        }
        else{


            mAuth.signInWithEmailAndPassword(e, p) // To get the phone number from database
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseDatabase database =  FirebaseDatabase.getInstance();;
                                FirebaseUser user = mAuth.getCurrentUser();
                                String UID = user.getUid();
                                DatabaseReference myRef = database.getReference("Users");
                                myRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.hasChild(UID)){

                                            phone = snapshot.child(UID).child("Phone").getValue().toString();


                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        //Toast.makeText(getApplicationContext(), "Couldn't find phone in Database.", Toast.LENGTH_SHORT).show();
                                    }
                                });


                                SharedPreferences.Editor speditor = sp.edit();
                                speditor.putString("email",e);
                                speditor.putString("password",p);
                                speditor.putString("phone",phone);
                                speditor.commit();

                                Intent i = new Intent(getApplicationContext(), MainMenu.class);
                                startActivity(i);
                                finish();
                            } else {
                                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });


             }

        }

    }