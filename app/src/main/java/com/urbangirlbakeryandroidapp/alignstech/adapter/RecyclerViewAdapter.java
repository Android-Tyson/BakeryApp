package com.urbangirlbakeryandroidapp.alignstech.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.model.RecyclerViewModel;
import java.util.List;

/**
 * Created by Dell on 1/12/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.UrgentCakeViewHolder> {

    public List<RecyclerViewModel> urgentCakeList;

    public RecyclerViewAdapter(List<RecyclerViewModel> urgentCakeList) {
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
        personViewHolder.cakeName.setText(urgentCakeList.get(i).cakeName);
        personViewHolder.cakePhoto.setImageResource(urgentCakeList.get(i).cakePhoto);

    }

    @Override
    public int getItemCount() {
        return urgentCakeList.size();
    }

    public class UrgentCakeViewHolder extends RecyclerView.ViewHolder {

        TextView cakeName;
        ImageView cakePhoto;

        public UrgentCakeViewHolder(View itemView) {
            super(itemView);
            cakeName = (TextView) itemView.findViewById(R.id.cake_title);
            cakePhoto = (ImageView) itemView.findViewById(R.id.cake_photo);
        }

    }
}
