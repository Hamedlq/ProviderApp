package com.alireza.providerapp.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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

import com.alireza.providerapp.Helpers.Constants;
import com.alireza.providerapp.Interfaces.UserApiInterface;
import com.alireza.providerapp.Models.ResponseModel;
import com.alireza.providerapp.Models.UserModel;
import com.alireza.providerapp.R;

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

public class SupplierAddActivity extends AppCompatActivity {

    @BindView(R.id.introduce_code)
    protected AutoCompleteTextView introduce_code;
    @BindView(R.id.continue_button)
    protected Button continue_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_add);

        ButterKnife.bind(this);

        continue_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendCodeToServer();
                    return true;
                }
                return false;
            }
        });

    }

    private void sendCodeToServer() {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.HTTP.BASE_URL)
                .build();
        UserApiInterface userInfoService =
                retrofit.create(UserApiInterface.class);

        final SharedPreferences prefs = this.getSharedPreferences(
                Constants.GlobalConstants.MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String authToken = prefs.getString(Constants.GlobalConstants.TOKEN, "");

        final String introduce_code_st = introduce_code.getText().toString();
        showProgress();
        Call<ResponseModel<UserModel>> call =
                userInfoService.sendIntroduce_code(authToken, introduce_code_st);

        call.enqueue(new Callback<ResponseModel<UserModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserModel>> call, Response<ResponseModel<UserModel>> response) {
                int code = response.code();
                hideProgress();
                if (code == 200) {
                    ResponseModel<UserModel> i = response.body();

                    if(i.getError()!=null){
                        Toast.makeText(SupplierAddActivity.this, i.getError(), Toast.LENGTH_LONG).show();
                        goToSupplierActivity();
                    }else{
                        Toast.makeText(SupplierAddActivity.this, i.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<UserModel>> call, Throwable t) {
                Toast.makeText(SupplierAddActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
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
    private void goToSupplierActivity() {
        Intent intent = new Intent(this, ProvidersListActivity.class);
        startActivity(intent);
    }

}
