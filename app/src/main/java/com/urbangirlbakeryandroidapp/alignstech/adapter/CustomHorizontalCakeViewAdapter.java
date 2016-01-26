package com.urbangirlbakeryandroidapp.alignstech.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.activity.SingleItemDetails;
import com.urbangirlbakeryandroidapp.alignstech.fragments.HomeFragment;
import com.urbangirlbakeryandroidapp.alignstech.model.RecyclerViewModel;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.AppController;

import java.util.List;

/**
 * Created by Dell on 1/12/2016.
 */
public class CustomHorizontalCakeViewAdapter extends RecyclerView.Adapter<CustomHorizontalCakeViewAdapter.UrgentCakeViewHolder> {

    private List<RecyclerViewModel> urgentCakeList;
    private Context context;

    public CustomHorizontalCakeViewAdapter(Context context , List<RecyclerViewModel> urgentCakeList ) {
        this.urgentCakeList = urgentCakeList;
        this.context = context;
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
        personViewHolder.cakePhoto.setImageUrl(urgentCakeList.get(i).cakePhoto , imageLoader);
    }

    @Override
    public int getItemCount() {
        return urgentCakeList.size();
    }

    public class UrgentCakeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView cakeName;
        NetworkImageView cakePhoto;

        public UrgentCakeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cakeName = (TextView) itemView.findViewById(R.id.cake_title);
            cakePhoto = (NetworkImageView) itemView.findViewById(R.id.cake_photo);
        }

        @Override
        public void onClick(View view) {

            String product_id = HomeFragment.urgentCakeIdList.get(getAdapterPosition());
            String product_name = HomeFragment.urgentCakeTitleList.get(getAdapterPosition());
            String api_name = Apis.BASE_URL + "api/product-details/" + product_id;

            Intent intent = new Intent(context , SingleItemDetails.class);
            intent.putExtra("TITLE_NAME" , product_name);
            intent.putExtra("API_NAME" , api_name);
            context.startActivity(intent);


        }
    }
}
