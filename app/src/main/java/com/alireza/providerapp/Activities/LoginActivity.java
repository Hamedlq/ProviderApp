package com.alireza.providerapp.Activities;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.alireza.providerapp.Fragments.CodeVerifyFragment;
import com.alireza.providerapp.Fragments.LoginFragment;
import com.alireza.providerapp.Helpers.Constants;
import com.alireza.providerapp.Interfaces.LoginApiInterface;
import com.alireza.providerapp.Models.LoginResponseModel;
import com.alireza.providerapp.Models.ReceiveTokenModel;
import com.alireza.providerapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alireza on 3/26/18.
 */

public class LoginActivity extends AppCompatActivity {
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.please_be_patient));
        progressDialog.setCancelable(false);


        try {
            SharedPreferences prefs =
                    getSharedPreferences(Constants.GlobalConstants.MY_SHARED_PREFERENCES, MODE_PRIVATE);
            String token = prefs.getString(Constants.GlobalConstants.TOKEN, "null");
            String name = prefs.getString(Constants.GlobalConstants.USER_NAME, "null");
            if (!token.equals("null")) {
                if (!name.equals("null")) {
                    finish();
                    goToMainActivity();
                }else {
                    finish();
                    goToUserInfoForm();
                }
            }
        } catch (Exception e) {
            SharedPreferences.Editor prefs =
                    getSharedPreferences(Constants.GlobalConstants.MY_SHARED_PREFERENCES, MODE_PRIVATE).edit();

        }


        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.holder, new LoginFragment())
                .commit();


    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, ItemsActivity.class);
        finish();
        startActivity(intent);
    }

    private void goToUserInfoForm() {
        Intent intent = new Intent(this, UserInfoFormActivity.class);
        finish();
        startActivity(intent);
    }


    public void sendUserPhoneNumberToServer(final String phoneNumber, int referCode) {
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.HTTP.BASE_URL)
                .build();
        LoginApiInterface loginApiService =
                retrofit.create(LoginApiInterface.class);

        Call<LoginResponseModel> call =
                loginApiService.sendUserPhoneNumber(phoneNumber, referCode);

        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                int code = response.code();
                progressDialog.hide();
                if (code == 200) {
                    LoginResponseModel i = response.body();
                    if(i.getError()!=null){
                        Toast.makeText(LoginActivity.this, i.getError(), Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(LoginActivity.this, i.getMessage(), Toast.LENGTH_LONG).show();
                        sendConfirmCode(phoneNumber);
                    }
                }
                if (code == 500) {
                    Toast.makeText(LoginActivity.this, "duplicate number", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "failure message", Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        });


    }

    public void addCodeVerifyFragment(String phoneNumber) {
        CodeVerifyFragment codeVerifyFragment = new CodeVerifyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.GlobalConstants.MOBILE_NUMBER_TAG, phoneNumber);

        codeVerifyFragment.setArguments(bundle);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.holder, codeVerifyFragment)
                .commit();
    }

    public void sendConfirmCode(final String phoneNumber) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.HTTP.BASE_URL)
                .build();

        LoginApiInterface apiInterface =
                retrofit.create(LoginApiInterface.class);

        Call<LoginResponseModel> call =
                apiInterface.sendConfirmCode(phoneNumber, 1);

        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                int code = response.code();
                progressDialog.hide();
                if (code == 200) {
                    Toast.makeText(LoginActivity.this, "successful", Toast.LENGTH_LONG).show();
                    addCodeVerifyFragment(phoneNumber);
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_LONG).show();
                progressDialog.hide();

            }
        });
    }

    public void sendConfirmSmsCode(String phoneNumber, int verificationCode) {
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.HTTP.BASE_URL)
                .build();

        LoginApiInterface apiInterface =
                retrofit.create(LoginApiInterface.class);

        Call<ReceiveTokenModel> call =
                apiInterface.sendConfirmCodeBackToServer(phoneNumber,
                        verificationCode);

        call.enqueue(new Callback<ReceiveTokenModel>() {
            @Override
            public void onResponse(Call<ReceiveTokenModel> call, Response<ReceiveTokenModel> response) {
                Toast.makeText(LoginActivity.this, "successful", Toast.LENGTH_LONG).show();
                progressDialog.hide();
                int code = response.code();

                if (code == 200) {
                    Toast.makeText(LoginActivity.this, R.string.success, Toast.LENGTH_LONG).show();
                    ReceiveTokenModel model = response.body();
                    String token = model.getToken();

                    SharedPreferences.Editor prefs =
                            getSharedPreferences(Constants.GlobalConstants.MY_SHARED_PREFERENCES, MODE_PRIVATE).edit();
                    prefs.putString(Constants.GlobalConstants.TOKEN, token);
                    prefs.apply();
                    if (model.isUserRegistered()) {
                        goToMainActivity();
                    } else {
                        goToUserInfoForm();
                    }
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ReceiveTokenModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_LONG).show();
                progressDialog.hide();


            }
        });


    }

}
