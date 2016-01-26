package com.urbangirlbakeryandroidapp.alignstech.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.bus.CheckBoxEventBus;
import com.urbangirlbakeryandroidapp.alignstech.bus.CheckBoxFalseEventBus;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyBus;

import java.util.List;

/**
 * Created by Dell on 1/12/2016.
 */
public class CustomHorizontalAccessoriesAdapter extends RecyclerView.Adapter<CustomHorizontalAccessoriesAdapter.AccessoriesViewHolder> {

    public List<String> accessoryNameList;
    private Context context;

    public CustomHorizontalAccessoriesAdapter(Context context, List<String> accessoryNameList) {
        this.accessoryNameList = accessoryNameList;
        this.context = context;
    }

    @Override
    public AccessoriesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_row_accessories_view, viewGroup, false);
        AccessoriesViewHolder urgentCakeViewHolder = new AccessoriesViewHolder(v);
        return urgentCakeViewHolder;
    }

    @Override
    public void onBindViewHolder(AccessoriesViewHolder personViewHolder, final int i) {

        personViewHolder.accessoriesName.setText(accessoryNameList.get(i));
        personViewHolder.accessorisCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(compoundButton.isChecked())
                MyBus.getInstance().post(new CheckBoxEventBus(i));

                if(!compoundButton.isChecked())
                    MyBus.getInstance().post(new CheckBoxFalseEventBus(i));
            }
        });


    }

    @Override
    public int getItemCount() {
        return accessoryNameList.size();
    }


    public class AccessoriesViewHolder extends RecyclerView.ViewHolder {

        TextView accessoriesName;
        CheckBox accessorisCheckBox;

        public AccessoriesViewHolder(View itemView) {
            super(itemView);
            accessoriesName = (TextView) itemView.findViewById(R.id.accessory_name);
            accessorisCheckBox = (CheckBox) itemView.findViewById(R.id.accessory_checkbox);

        }
    }
}
