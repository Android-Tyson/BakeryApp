package com.urbangirlbakeryandroidapp.alignstech.fragment_dialog;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.urbangirlbakeryandroidapp.alignstech.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class About_Us extends DialogFragment implements View.OnClickListener {

    @InjectView(R.id.close_button)
    TextView close_button;

    public static About_Us newInstance() {

        About_Us welcome_screen = new About_Us();
        return welcome_screen;

    }

    public About_Us() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment__about__us, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        close_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        getDialog().dismiss();

    }
}
