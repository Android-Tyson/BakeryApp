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
import com.urbangirlbakeryandroidapp.alignstech.controller.PostOrderGiftDetails;
import com.urbangirlbakeryandroidapp.alignstech.utils.Apis;
import com.urbangirlbakeryandroidapp.alignstech.utils.MyUtils;

import java.util.ArrayList;

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

    private ArrayList<String> userPostDetails = new ArrayList<>();

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
                PostOrderGiftDetails.postOrderUserDetails(Apis.cake_order_details, getActivity(), userPostDetails);
                getDialog().dismiss();
            }else {
                MyUtils.showToast(getActivity(), "Some Fields are empty...");
            }
        }

    }

    public boolean fieldsAreNotEmpty(){

        String sender_name = sender.getText().toString();
        String receiver_name = receiver.getText().toString();
        String sender_addr = senderAddress.getText().toString();
        String receiver_addr = receiverAddress.getText().toString();
        String short_message = message.getText().toString();

        if(!sender_name.isEmpty() && !receiver_name.isEmpty() &&
                !sender_addr.isEmpty() && !receiver_addr.isEmpty() &&
                !short_message.isEmpty()){

            userPostDetails.add(sender_name);
            userPostDetails.add(receiver_name);
            userPostDetails.add(sender_addr);
            userPostDetails.add(receiver_addr);
            userPostDetails.add(short_message);



            return true;
        }else{
            MyUtils.showToast(getActivity() , "Please Fill all the fields..");
            return false;
        }

    }
}
