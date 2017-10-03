package com.codepath.example.masterdetailmanual;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    private final Context context;
    private final ArrayList<Item> values;

    public CustomAdapter(Context context, ArrayList<Item> values) {
        super(context, android.R.layout.simple_list_item_activated_1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.fragment_items_list, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.txItems);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imgItems);
        textView.setText(values.get(position).getTitle());
        imageView.setImageResource(R.drawable.coffee);

        return rowView;
    }
}