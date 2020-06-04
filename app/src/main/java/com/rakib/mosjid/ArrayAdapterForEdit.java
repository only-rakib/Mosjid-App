package com.rakib.mosjid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class ArrayAdapterForEdit extends ArrayAdapter<MonthCollectionAndDetailsInfo> {
    int layout;
    public ArrayAdapterForEdit(Context context, int resource, @NonNull ArrayList<MonthCollectionAndDetailsInfo> objects) {
        super(context, resource, objects);
        this.layout = resource;
    }
    private static class ViewHolder{
        TextView textView1;
        EditText editText;
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
            viewHolder.editText = convertView.findViewById(R.id.text2);
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.textView1.setText(user.getMonthName());
        viewHolder.editText.setText(user.getAmount());


        return convertView;
    }
}

