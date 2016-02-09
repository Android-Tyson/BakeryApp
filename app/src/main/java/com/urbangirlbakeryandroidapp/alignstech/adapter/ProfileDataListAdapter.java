package com.urbangirlbakeryandroidapp.alignstech.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.urbangirlbakeryandroidapp.alignstech.R;

import java.util.List;

/**
 * Created by Dell on 2/9/2016.
 */
public class ProfileDataListAdapter extends RecyclerView.Adapter<ProfileDataListAdapter.ViewHolder> {

    public List<String> titleList;
    public List<String> infoList;
    private Context context;

    public ProfileDataListAdapter(Context context, List<String> titleList, List<String> infoList) {
        this.context = context;
        this.titleList = titleList;
        this.infoList = infoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_row_profile, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder personViewHolder, final int i) {

        personViewHolder.title.setText(titleList.get(i));
        personViewHolder.userInfo.setText(infoList.get(i));


    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView userInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            userInfo = (TextView) itemView.findViewById(R.id.userInfo);

        }
    }
}
