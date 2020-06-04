package com.rakib.mosjid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class MyArrayAdapter extends ArrayAdapter<MonthCollectionAndDetailsInfo> {
    //ArrayList<DataStore> items;
    int layout;
    public MyArrayAdapter(@NonNull Context context,int layout, ArrayList<MonthCollectionAndDetailsInfo> users) {
        super(context,layout,users);
        this.layout=layout;

    }
    private static class ViewHolder{
        TextView textView1;
        TextView textView2;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MonthCollectionAndDetailsInfo user = getItem(position);
        //Log.d("posi",""+position);
        ViewHolder viewHolder=new ViewHolder();;
            if (convertView == null) {
                        convertView = LayoutInflater.from(getContext()).inflate(layout, parent, false);

                        viewHolder.textView1 = convertView.findViewById(R.id.text1);
                        viewHolder.textView2 = convertView.findViewById(R.id.text2);
                        convertView.setTag(viewHolder);
            } else {
                // View is being recycled, retrieve the viewHolder object from tag
                viewHolder = (ViewHolder) convertView.getTag();

            }
            viewHolder.textView1.setText(user.getMonthName());
            viewHolder.textView2.setText(user.getAmount());


        return convertView;
    }

}
