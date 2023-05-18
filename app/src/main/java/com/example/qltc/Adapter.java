package com.example.qltc;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.Serializable;
import java.util.ArrayList;

public class Adapter extends BaseAdapter implements Serializable {
    Activity activity;
    ArrayList<ThuChi> data;
    ArrayList<ThuChi> oldData;
    LayoutInflater inflater;

    public Adapter(Activity activity, ArrayList<ThuChi> data){
        this.activity = activity;
        this.data = data;
        this.oldData = data;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return data.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            v = inflater.inflate(R.layout.item, null);
            ConstraintLayout layout = v.findViewById(R.id.idLayout);
            TextView txtType = v.findViewById(R.id.txtType);
            if(data.get(i).isThuChi() == 1){
                layout.setBackgroundColor(Color.RED);
                txtType.setText("Thu");
            }
            if(data.get(i).isThuChi() == 0){
                layout.setBackgroundColor(Color.BLUE);
                txtType.setText("Chi");
            }
            TextView txtName = v.findViewById(R.id.txtName);
            txtName.setText(data.get(i).getName());
            TextView txtDate = v.findViewById(R.id.txtDate);
            txtDate.setText(data.get(i).getDate());
            TextView txtCost = v.findViewById(R.id.txtCost);
            txtCost.setText(String.valueOf(data.get(i).getCost()));
        }
        return v;
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if (search.isEmpty()) {
                    data = oldData;
                } else {
                    ArrayList<ThuChi> list = new ArrayList<>();
                    for (ThuChi x : oldData) {
                        if (x.getName().toLowerCase().contains(search.toLowerCase())) {
                            list.add(x);
                        }
                    }
                    data = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = data;
                filterResults.count = data.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data = (ArrayList<ThuChi>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
