package com.example.projectv1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CheckOutAdapter extends BaseAdapter {
    int count;
    Context ctx;
    LayoutInflater inflator;
    ArrayList<CheckOut.CheckOutItem> items;


    public CheckOutAdapter(Context ctx, ArrayList<CheckOut.CheckOutItem> items, int count) {
        this.ctx = ctx;
        this.count = count;
        inflator = LayoutInflater.from(ctx);
        this.items = items;
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
        CheckOutAdapter.ViewHolder mainViewholder = null;

        if (view == null) {
            view = inflator.inflate(R.layout.check_out_list_layout, null);

            CheckOutAdapter.ViewHolder viewHolder = new CheckOutAdapter.ViewHolder();
            viewHolder.title = view.findViewById(R.id.checkOutTitle);
            viewHolder.price = view.findViewById(R.id.checkOutPriceTag);
            viewHolder.priceValue = view.findViewById(R.id.checkOutPrice);
            viewHolder.quantity = view.findViewById(R.id.checkOutQuantityTag);
            viewHolder.quantityValue = view.findViewById(R.id.checkOutQuantity);

            viewHolder.title.setText(items.get(i).title);
            viewHolder.priceValue.setText(items.get(i).price);
            viewHolder.quantityValue.setText(items.get(i).quantity);

            //Toast.makeText(ctx.getApplicationContext(), items.get(i).title, Toast.LENGTH_SHORT).show();
            view.setTag(viewHolder);
        }

        mainViewholder = (CheckOutAdapter.ViewHolder) view.getTag();
        CheckOutAdapter.ViewHolder finalMainViewholder = mainViewholder;


        return view;


    }



    public class ViewHolder {

        TextView title;
        TextView price;
        TextView priceValue;
        TextView quantity;
        TextView quantityValue;


    }
}
