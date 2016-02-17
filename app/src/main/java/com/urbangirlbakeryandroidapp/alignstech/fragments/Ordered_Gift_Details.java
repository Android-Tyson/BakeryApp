package com.urbangirlbakeryandroidapp.alignstech.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.urbangirlbakeryandroidapp.alignstech.R;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ordered_Gift_Details extends DialogFragment implements View.OnClickListener {

    @InjectView(R.id.close)
    TextView closeDialog;

    @InjectView(R.id.sender)
    EditText sender;

    @InjectView(R.id.receiver)
    EditText receiver;

    @InjectView(R.id.senderAddress)
    EditText senderAddress;

    @InjectView(R.id.receiverAddress)
    EditText receiverAddress;

    @InjectView(R.id.short_message)
    EditText message;

    @InjectView(R.id.order_gift)
    Button order_gift;

    public Ordered_Gift_Details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_ordered_gift_detials, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        closeDialog.setOnClickListener(this);
        order_gift.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.close){
            getDialog().dismiss();
        }else if(view.getId() == R.id.order_gift){
            if(fieldsAreNotEmpty()) {
                MyUtils.showToast(getActivity(), "clk..");
            }
        }

    }

    public boolean fieldsAreNotEmpty(){

        if(!sender.getText().toString().isEmpty() && !receiver.getText().toString().isEmpty() &&
                !senderAddress.getText().toString().isEmpty() && !receiverAddress.getText().toString().isEmpty()){
            return true;
        }else{
            MyUtils.showToast(getActivity() , "Please Fill all the fields..");
            return false;
        }

    }
}
