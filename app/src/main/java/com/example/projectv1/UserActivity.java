package com.example.projectv1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {

    TextView userName;
    TextView userPhone;
    TextView userEmail;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        FirebaseUser user = mAuth.getCurrentUser();
        String UID = user.getUid();

        userEmail = findViewById(R.id.userEmail);
        userName = findViewById(R.id.userName);
        userPhone = findViewById(R.id.userPhone);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(UID)){

                    String n = snapshot.child(UID).child("Name").getValue().toString();
                    String p = snapshot.child(UID).child("Phone").getValue().toString();
                    String e = snapshot.child(UID).child("Email").getValue().toString();
                    userEmail.setText(e);
                    userName.setText(n);
                    userPhone.setText(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
               // Toast.makeText(getApplicationContext(), "Couldn't find user in Database.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        SharedPreferences sp = getSharedPreferences("User_Details",MODE_PRIVATE);
        SharedPreferences.Editor speditor = sp.edit();
        speditor.putString("email","not found");
        speditor.putString("password","not found");
        speditor.putString("phone","not found");
        speditor.commit();
        finishAffinity();
        Intent i = new Intent(getApplicationContext(),LogIn.class);
        startActivity(i);
    }
}