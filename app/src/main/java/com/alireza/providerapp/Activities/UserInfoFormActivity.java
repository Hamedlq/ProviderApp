package com.alireza.providerapp.Activities;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.alireza.providerapp.Fragments.CodeVerifyFragment;
import com.alireza.providerapp.Fragments.LoginFragment;
import com.alireza.providerapp.Helpers.Constants;
import com.alireza.providerapp.Interfaces.LoginApiInterface;
import com.alireza.providerapp.Interfaces.UserApiInterface;
import com.alireza.providerapp.Models.LoginResponseModel;
import com.alireza.providerapp.Models.ReceiveTokenModel;
import com.alireza.providerapp.Models.ResponseModel;
import com.alireza.providerapp.Models.UserModel;
import com.alireza.providerapp.R;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alireza on 3/26/18.
 */

public class UserInfoFormActivity extends AppCompatActivity {

    @BindView(R.id.name)
    protected AutoCompleteTextView name;
    @BindView(R.id.family)
    protected AutoCompleteTextView family;
    @BindView(R.id.address)
    protected AutoCompleteTextView address;
    @BindView(R.id.shopname)
    protected AutoCompleteTextView shopname;
    @BindView(R.id.shopphone)
    protected AutoCompleteTextView shopphone;
    @BindView(R.id.propertytype)
    protected Spinner propertytype;
    @BindView(R.id.continue_button)
    protected Button continue_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        ButterKnife.bind(this);

        String[] arraySpinner = new String[]{
                "استیجاری", "مالک", "سرقفلی"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
        propertytype.setAdapter(adapter);

        continue_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendUserInfoToServer();
                    return true;
                }
                return false;
            }
        });

    }

    private void sendUserInfoToServer() {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.HTTP.BASE_URL)
                .build();
        UserApiInterface userInfoService =
                retrofit.create(UserApiInterface.class);

        SharedPreferences prefs = this.getSharedPreferences(
                Constants.GlobalConstants.APP_DOMAIN, Context.MODE_PRIVATE);
        String authToken = prefs.getString(Constants.GlobalConstants.TOKEN, "");

        final String name_st = name.getText().toString();
        String family_st = family.getText().toString();
        String address_st = address.getText().toString();
        String shopname_st = shopname.getText().toString();
        String shopphone_st = shopphone.getText().toString();
        String propertytype_st = propertytype.getSelectedItem().toString();
        showProgress();
        Call<ResponseModel<UserModel>> call =
                userInfoService.sendUserInfoToServer(authToken, name_st, family_st, address_st, shopname_st, shopphone_st, propertytype_st);

        call.enqueue(new Callback<ResponseModel<UserModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserModel>> call, Response<ResponseModel<UserModel>> response) {
                int code = response.code();
                if (code == 200) {
                    ResponseModel<UserModel> i = response.body();
                    if(i.getMessage().name.contains(name_st)){
                        Toast.makeText(UserInfoFormActivity.this, R.string.success, Toast.LENGTH_LONG).show();
                        goToMainActivity();
                    }else {
                        Toast.makeText(UserInfoFormActivity.this, R.string.failed, Toast.LENGTH_LONG).show();
                    }

                }
                hideProgress();
            }

            @Override
            public void onFailure(Call<ResponseModel<UserModel>> call, Throwable t) {
                Toast.makeText(UserInfoFormActivity.this, "failure message", Toast.LENGTH_SHORT).show();
                hideProgress();
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getText(R.string.please_wait));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        return dialog;
    }


    /**
     * Hide progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void hideProgress() {
        dismissDialog(0);
    }

    /**
     * Show progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void showProgress() {
        showDialog(0);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
