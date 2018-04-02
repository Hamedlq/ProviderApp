package com.alireza.providerapp.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.alireza.providerapp.Activities.LoginActivity;
import com.alireza.providerapp.Helpers.Constants;
import com.alireza.providerapp.R;

/**
 * Created by alireza on 3/27/18.
 */

public class CodeVerifyFragment extends Fragment {

    EditText codeVerifyEdittext;
    Button continueButton;
    String phoneNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        phoneNumber = bundle.getString(Constants.GlobalConstants.MOBILE_NUMBER_TAG);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_code_verify,null,false);

        codeVerifyEdittext = view.findViewById(R.id.verify_code_edittext);
        continueButton = view.findViewById(R.id.continue_button);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int verificationCode =0;
                try {
                    verificationCode = Integer.parseInt(codeVerifyEdittext.getText().toString());
                }
                catch (NumberFormatException e ) {
                }
                ((LoginActivity) getActivity())
                        .sendConfirmSmsCode(phoneNumber, verificationCode);
            }
        });


        return view;
    }
}
