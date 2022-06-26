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

public class MainMenu extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    TextView phone;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        phone = findViewById(R.id.userPhone1);





        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        FirebaseUser user = mAuth.getCurrentUser();
        String UID = user.getUid();
        SharedPreferences sp = getSharedPreferences("User_Details",MODE_PRIVATE);
        SharedPreferences.Editor speditor = sp.edit();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(UID)) {
                    String p = snapshot.child(UID).child("Phone").getValue().toString();


                    phone.setText(p);
                    if(p != ""){
                        speditor.putString("phone",p);
                        speditor.commit();

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(getApplicationContext(), "Couldn't find user in Database.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void plumber(View view) {

            /*while(phone.getText().toString() == ""){
                FirebaseUser user = mAuth.getCurrentUser();
                String UID = user.getUid();
                SharedPreferences sp = getSharedPreferences("User_Details",MODE_PRIVATE);
                SharedPreferences.Editor speditor = sp.edit();

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(UID)) {
                            String p = snapshot.child(UID).child("Phone").getValue().toString();


                            phone.setText(p);
                            if(p != ""){
                                speditor.putString("phone",p);

                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Couldn't find user in Database.", Toast.LENGTH_SHORT).show();
                    }
                });*/
                Intent i = new Intent(this, Plumber.class);

                i.putExtra("phone",phone.getText().toString());
                startActivity(i);



    }

    public void userActivity(View view) {

        Intent i = new Intent(this, UserActivity.class);
        startActivity(i);
    }
}