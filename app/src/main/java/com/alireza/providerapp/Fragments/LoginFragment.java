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
import com.alireza.providerapp.R;

/**
 * Created by alireza on 3/26/18.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {

    EditText userPhoneNumberEdittext;
    EditText referCodeEdittext;
    Button continueButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, null, false);

        userPhoneNumberEdittext = view.findViewById(R.id.user_phone_edittext);
        referCodeEdittext = view.findViewById(R.id.refer_code_edittext);
        continueButton = view.findViewById(R.id.continue_button);


        continueButton.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.continue_button:
                long phoneNumber = Long.parseLong(userPhoneNumberEdittext.getText().toString());
                String phoneNumberString = userPhoneNumberEdittext.getText().toString();
                int referCode = Integer.parseInt(referCodeEdittext.getText().toString());


                ((LoginActivity) getActivity())
                        .sendUserPhoneNumberToServer(phoneNumberString,referCode);

                break;




        }


    }
}
