package com.urbangirlbakeryandroidapp.alignstech.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.model.RecyclerViewModel;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;

import java.util.List;

/**
 * Created by Dell on 1/12/2016.
 */
public class CustomHorizontalCakeViewAdapter extends RecyclerView.Adapter<CustomHorizontalCakeViewAdapter.UrgentCakeViewHolder> {

    public List<RecyclerViewModel> urgentCakeList;

    public CustomHorizontalCakeViewAdapter(List<RecyclerViewModel> urgentCakeList) {
        this.urgentCakeList = urgentCakeList;
    }

    @Override
    public UrgentCakeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_row_recycler_view, viewGroup, false);
        UrgentCakeViewHolder urgentCakeViewHolder = new UrgentCakeViewHolder(v);
        return urgentCakeViewHolder;
    }

    @Override
    public void onBindViewHolder(UrgentCakeViewHolder personViewHolder, int i) {

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        personViewHolder.cakeName.setText(urgentCakeList.get(i).cakeName);
//        personViewHolder.cakePhoto.setImageResource(urgentCakeList.get(i).cakePhoto);
        personViewHolder.cakePhoto.setImageUrl(urgentCakeList.get(i).cakePhoto , imageLoader);
    }

    @Override
    public int getItemCount() {
        return urgentCakeList.size();
    }

    public class UrgentCakeViewHolder extends RecyclerView.ViewHolder {

        TextView cakeName;
        NetworkImageView cakePhoto;

        public UrgentCakeViewHolder(View itemView) {
            super(itemView);
            cakeName = (TextView) itemView.findViewById(R.id.cake_title);
            cakePhoto = (NetworkImageView) itemView.findViewById(R.id.cake_photo);
        }

    }
}