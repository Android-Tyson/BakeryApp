package com.urbangirlbakeryandroidapp.alignstech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.model.Product;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;

import java.util.List;

/**
 * Created by Dell on 1/7/2016.
 */
public class CustomListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Product> productList;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(i);
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
            convertView = inflater.inflate(R.layout.single_row_tiem_view , null);

        if(imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView productImageView = (NetworkImageView) convertView.findViewById(R.id.imageViewProduct);
        TextView productTitle = (TextView) convertView.findViewById(R.id.textViewProduct);

        Product product = productList.get(position);

        productImageView.setImageUrl(product.getProductImageUrl() , imageLoader);
        productTitle.setText(product.getProductName());

        return convertView;
    }
}
