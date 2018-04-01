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
import com.alireza.providerapp.Interfaces.ProviderApiInterface;
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

public class ItemFormActivity extends AppCompatActivity {

    @BindView(R.id.item_name)
    protected AutoCompleteTextView item_name;
    @BindView(R.id.item_brand)
    protected AutoCompleteTextView item_brand;
    @BindView(R.id.item_price)
    protected AutoCompleteTextView item_price;
    @BindView(R.id.item_description)
    protected AutoCompleteTextView item_description;
    @BindView(R.id.continue_button)
    protected Button continue_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        ButterKnife.bind(this);

        continue_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendItemInfoToServer();
                    return true;
                }
                return false;
            }
        });

    }

    private void sendItemInfoToServer() {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.HTTP.BASE_URL)
                .build();
        ProviderApiInterface providerApi =
                retrofit.create(ProviderApiInterface.class);

        SharedPreferences prefs = this.getSharedPreferences(
                Constants.GlobalConstants.MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String authToken = prefs.getString(Constants.GlobalConstants.TOKEN, "");

        final String item_name_st = item_name.getText().toString();
        String item_brand_st = item_brand.getText().toString();
        String item_price_st = item_price.getText().toString();
        String item_description_st = item_description.getText().toString();
        showProgress();
        Call<ResponseModel<String>> call =
                providerApi.insertItem(authToken, item_name_st, item_brand_st, item_price_st, item_description_st);

        call.enqueue(new Callback<ResponseModel<String>>() {
            @Override
            public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                int code = response.code();
                if (code == 200) {
                    ResponseModel<String> i = response.body();
                        Toast.makeText(ItemFormActivity.this, i.getMessage(), Toast.LENGTH_LONG).show();
                        goToMainActivity();
                    }else {
                        Toast.makeText(ItemFormActivity.this, R.string.failed, Toast.LENGTH_LONG).show();
                    }

                hideProgress();
            }

            @Override
            public void onFailure(Call<ResponseModel<String>> call, Throwable t) {
                Toast.makeText(ItemFormActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
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
