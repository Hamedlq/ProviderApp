package com.alireza.providerapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alireza.providerapp.Adapters.ItemsListAdapter;
import com.alireza.providerapp.Helpers.Constants;
import com.alireza.providerapp.R;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ImageView imgDrawer = (ImageView) toolbar.findViewById(R.id.imgDrawer);
        imgDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);


        final SharedPreferences prefs = this.getSharedPreferences(
                Constants.GlobalConstants.MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String mobile = prefs.getString(Constants.GlobalConstants.MOBILE_NUMBER_TAG, "");
        String name = prefs.getString(Constants.GlobalConstants.USER_NAME, "");

        TextView nav_mobile=(TextView)header.findViewById(R.id.nav_user_mobile);
        nav_mobile.setText(mobile);
        TextView nav_name=(TextView)header.findViewById(R.id.nav_user_name);
        nav_name.setText(name);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.providers_list) {
            Intent intent = new Intent(this,ProvidersListActivity.class );
            //finish();
            startActivity(intent);
        } else if (id == R.id.add_supplier) {
            Intent intent = new Intent(this,SupplierAddActivity.class );
            startActivity(intent);
        } else if (id == R.id.items_list) {
            Intent intent = new Intent(this,ItemsActivity.class );
            //finish();
            startActivity(intent);
        } else if (id == R.id.orders_list) {
            Intent intent = new Intent(this,OrdersListActivity.class );
            //finish();
            startActivity(intent);
        }
        return false;
    }

    public void setToolbarTitle(String title){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView titleView = (TextView) toolbar.findViewById(R.id.txtTitle);
        titleView.setText(title);
    }


}
