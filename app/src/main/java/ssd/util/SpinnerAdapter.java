package ssd.util;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.zip.Inflater;

import ssd.sale.R;

/**
 * Created by Administrator on 2014/11/29.
 */
public class SpinnerAdapter extends ArrayAdapter<String[]> {

    private Context context;
    private String[][] array;

    public SpinnerAdapter(Context context, int textViewResourceId, String[][] myArray) {
        super(context, textViewResourceId, myArray);
        this.context = context;
        this.array = myArray;
    }

    public int getCount(){
        return array.length;
    }

    public String[] getItem(int position){
        return array[position];
    }

    public long getItemId(int position){
        return position;
    }

    public String getID(int position) {
        return array[position][0];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setText(array[position][1]);      // 0: value, 1: label
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.spinner_item, parent, false);
        }

        TextView label = (TextView) convertView.findViewById(R.id.label);
        label.setText(array[position][1]);

        return convertView;
    }
}
