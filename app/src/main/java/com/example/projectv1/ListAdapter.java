package com.example.projectv1;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    public ArrayList<PlumberItems> item;
    int count;
    Context ctx;
    LayoutInflater inflator;
    int[] checkeditems = Plumber.checkeditems;

    public ListAdapter(Context ctx, ArrayList<PlumberItems> items, int count) {
        this.ctx = ctx;
        this.count = count;
        this.item = items;
        inflator = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder mainViewholder = null;

        if (view == null) {
            view = inflator.inflate(R.layout.plumber_items, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.description = view.findViewById(R.id.description);
            viewHolder.title = view.findViewById(R.id.title);
            viewHolder.price = view.findViewById(R.id.price);
            viewHolder.value = view.findViewById(R.id.value);
            viewHolder.adbtn = view.findViewById(R.id.addbtn);
            viewHolder.removebtn = view.findViewById(R.id.removebtn);
            viewHolder.description.setText(item.get(i).description);
            viewHolder.title.setText(item.get(i).name);
            viewHolder.price.setText(item.get(i).price);


            view.setTag(viewHolder);
        }

        mainViewholder = (ViewHolder) view.getTag();
        ViewHolder finalMainViewholder = mainViewholder;


        View finalView = view;

        mainViewholder.adbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p = finalMainViewholder.value.getText().toString();
                int t = Integer.parseInt(p);
                t++;
                finalMainViewholder.value.setText(t + "");

                String price = finalMainViewholder.price.getText().toString();

                int total = Integer.parseInt(Plumber.total.getText().toString());
                total+= Integer.parseInt(price);

                Plumber.total.setText(total+"");
                checkeditems[i] +=1;
            }
        });

        mainViewholder.removebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p = finalMainViewholder.value.getText().toString();
                int t = Integer.parseInt(p);
                if (t != 0) {

                    t--;
                    finalMainViewholder.value.setText(t + "");
                    String price = finalMainViewholder.price.getText().toString();

                    int total = Integer.parseInt(Plumber.total.getText().toString());
                    total-= Integer.parseInt(price);

                    Plumber.total.setText(total+"");
                    checkeditems[i] -=1;
                }

            }
        });


        return view;


    }



    public class ViewHolder {

        TextView title;
        TextView price;
        TextView value;
        TextView description;
        ImageButton adbtn;
        ImageButton removebtn;

    }



    }


