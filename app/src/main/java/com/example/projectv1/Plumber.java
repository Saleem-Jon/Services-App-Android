package com.example.projectv1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Plumber extends AppCompatActivity {
    ImageButton addbtn;
    ImageButton removebtn;
    ListView listView;
    ListAdapter listAdapter;
    String phone;
    static int[] checkeditems; //stores the selected items for the checkout activity

    public static TextView total;

    ArrayList<PlumberItems> itemsArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber);
        addbtn = findViewById(R.id.addbtn);
        removebtn = findViewById(R.id.removebtn);
        listView = findViewById(R.id.list);
        total = findViewById(R.id.totalprice);
        Intent intent = getIntent();

        phone = intent.getStringExtra("phone");
        String[] plumberItemName = getResources().getStringArray(R.array.plumberItemsNames);
        String[] plumberItemPrices = getResources().getStringArray(R.array.plumberItemPrices);
        String[] plumberItemDescriptions = getResources().getStringArray(R.array.plumberItemDescriptions);

        checkeditems = new int[plumberItemName.length];

        for(int i=0; i<plumberItemName.length;i++){
            PlumberItems item = new PlumberItems(plumberItemName[i],plumberItemDescriptions[i],plumberItemPrices[i]);
            itemsArrayList.add(item);
        }

        listAdapter = new ListAdapter(this, itemsArrayList,plumberItemName.length);

        listView.setAdapter(listAdapter);
        listView.setClickable(false);




    }



    public void checkout(View view) {
        String totalprice = total.getText().toString();
        if(!totalprice.equals("0")){
            Intent i = new Intent(getApplicationContext(), CheckOut.class);

            i.putExtra("checkedNumbers", checkeditems);
            i.putExtra("phone",phone);
            i.putExtra("totalprice", totalprice);

            startActivity(i);

        }else{
            Toast.makeText(getApplicationContext(), "Please Choose at least one item.", Toast.LENGTH_SHORT).show();
        }

        }


    }



