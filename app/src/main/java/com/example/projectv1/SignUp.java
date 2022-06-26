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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    EditText email;
    EditText password;
    EditText phone;
    EditText signUpname;
    String number; // stores the edit text value


    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.signUpEmail);
        password = findViewById(R.id.signUpPassword);
        phone = findViewById(R.id.signUpPhone);
        signUpname= findViewById(R.id.signUpName);
        database = FirebaseDatabase.getInstance();


    }

    public void signUp(View view) {


        if(email.getText().toString().equals("")|| password.getText().toString().equals("") || phone.getText().toString().equals("")|| signUpname.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Please Fill all the Fields", Toast.LENGTH_SHORT).show();
        }else {

            String e = email.getText().toString();
            String p = password.getText().toString();
            mAuth.createUserWithEmailAndPassword(e, p)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                String UID = user.getUid();
                                myRef = database.getReference("Users").child(UID);
                                myRef.child("Phone").setValue(phone.getText().toString());
                                myRef.child("Email").setValue(e);
                                myRef.child("Name").setValue(signUpname.getText().toString());

                                Toast.makeText(getApplicationContext(), "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                SharedPreferences sp = getSharedPreferences("User_Details", MODE_PRIVATE);
                                SharedPreferences.Editor speditor = sp.edit();
                                speditor.putString("email", e);
                                speditor.putString("password", p);
                                speditor.putString("phone", phone.getText().toString());
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