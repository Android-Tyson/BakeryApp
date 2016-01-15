package com.urbangirlbakeryandroidapp.alignstech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.urbangirlbakeryandroidapp.alignstech.R;

import java.util.List;

/**
 * Created by Dell on 1/7/2016.
 */
public class CustomListChildAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<String> childList;

    public CustomListChildAdapter(Context context, List<String> productList) {
        this.context = context;
        this.childList = productList;
    }

    @Override
    public int getCount() {
        return childList.size();
    }

    @Override
    public Object getItem(int i) {
        return childList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
            convertView = inflater.inflate(R.layout.single_row_see_all_items, null);

        TextView productTitle = (TextView) convertView.findViewById(R.id.textView);
        String product = childList.get(position);
        productTitle.setText(product);

        return convertView;
    }
}
