package com.example.projectv1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CheckOut extends AppCompatActivity {
    long num = -1; //stores Number of orders
    Boolean addressCheck;
    String phone;
    String emailItems= "";
    Button addbtn;
    int[] checkeditems;
    ListView lv;
    CheckOutAdapter adapter;
    String[] names;
    String[] prices;
    int size = 0;
    TextView totalPrice;
    DatabaseReference myRef;
    ArrayList<CheckOutItem> checkOutItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_check_out);

        addressCheck = false;
        addbtn = findViewById(R.id.addressbtn);
        lv = findViewById(R.id.checkOutLV);
        totalPrice = findViewById(R.id.checkOutTotalPrice);
        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        checkeditems = extras.getIntArray("checkedNumbers");

        phone = intent.getStringExtra("phone");


        String total = extras.getString("totalprice");
        totalPrice.setText(total);
//-------------------------- Get number of order-------------------------------
        FirebaseDatabase database;
        database = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String UID = user.getUid();

        myRef = database.getReference("Users").child(UID).child("Orders");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("Order 0")) {
                    num = snapshot.getChildrenCount();
                }




            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Toast.makeText(getApplicationContext(), "Couldn't find user in Database.", Toast.LENGTH_SHORT).show();
            }
        });


//-----------------------------------------------------------------------------
        for (int j = 0; j < checkeditems.length; j++) //To determine the size of the arrays
        {
            if(checkeditems[j]!=0){
                size++;

            }
        }

        names = new String[size];
        prices = new String[size];

        String[] plumberItemName = getResources().getStringArray(R.array.plumberItemsNames);
        String[] plumberItemPrices = getResources().getStringArray(R.array.plumberItemPrices);

        for (int j = 0; j < checkeditems.length; j++) // To add the selected items into arrays
        {
            if(checkeditems[j]!=0){
                CheckOutItem item = new CheckOutItem(plumberItemName[j],plumberItemPrices[j],checkeditems[j]+"");
                checkOutItems.add(item);

                emailItems += "Item Name: "+plumberItemName[j]+"\n"+"Item Price: "+plumberItemPrices[j]+"\n"+"Quantity: "+checkeditems[j]+"\n\n\n";
            }
        }
        adapter = new CheckOutAdapter(this, checkOutItems,size);

        lv.setAdapter(adapter);
        emailItems += "\n\nPhone number: " + phone;

    }

    public void sendOrder(View view) {

        if(addressCheck) {

        JavaMailAPI javaMailAPI = new JavaMailAPI(this, "saleem.jon19@gmail.com", "New Order for Plumber", emailItems);
        javaMailAPI.execute();

            if(num>0){
                myRef.child("Order "+num).setValue(emailItems);

            }

            else{
                    myRef.child("Order 0").setValue(emailItems);

            }
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);

            finish();
            Toast.makeText(getApplicationContext(), "Order Sent, You will get a confirmation call soon.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Choose Address", Toast.LENGTH_LONG).show();
        }

    }

    public void maps(View view) {
        Intent i = new Intent(this,MapsActivity.class);
        startActivityForResult(i,1);

    }

    public class CheckOutItem{
        String price;
        String quantity;
        String title;

        public CheckOutItem(String title, String price, String quantity) {
            this.price = price;
            this.quantity = quantity;
            this.title = title;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                String addresss = data.getStringExtra("address");
                emailItems += "\n"+addresss;
                addressCheck = true;
                //addbtn.setTextColor(Color.parseColor("#FF8BC34A"));
                addbtn.setText("Address Added \u2713");
            }
            if (resultCode == RESULT_CANCELED) {

            }

        }


    }
}

