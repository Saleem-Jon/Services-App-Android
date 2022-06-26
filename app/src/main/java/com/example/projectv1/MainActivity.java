package com.example.projectv1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    LottieAnimationView splash;
    SharedPreferences sp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        splash = findViewById(R.id.splash);
        /*Intent i = new Intent(getApplicationContext(),LogIn.class);
        startActivity(i);
        finish();*/
        sp = getSharedPreferences("User_Details",MODE_PRIVATE);
        String e = sp.getString("email","not found");
        String p = sp.getString("password","not found");
        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!e.equals("not found") && !p.equals("not found")){
                    login(e,p);

                }
                else {
                    Intent i = new Intent(getApplicationContext(), LogIn.class);
                    startActivity(i);
                    finish();
                }
            }
        },3000);



        }

        public void login(String e, String p){
            mAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //Intent i = new Intent(getApplicationContext(), MapsActivity.class);
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