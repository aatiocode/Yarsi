package com.yarsi.yarsi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.yarsi.yarsi.services.LoginServices;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    HomeFragment homeFragment = new HomeFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, homeFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_reservasi:
                    ReservasiFragment reservasiFragment = new ReservasiFragment();
                    FragmentTransaction fragmentReservasiTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentReservasiTransaction.replace(R.id.content, reservasiFragment);
                    fragmentReservasiTransaction.commit();
                    return true;
                case R.id.navigation_inbox:
                    NotifikasiFragment notifikasiFragment = new NotifikasiFragment();
                    FragmentTransaction fragmentNotifikasiTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentNotifikasiTransaction.replace(R.id.content, notifikasiFragment);
                    fragmentNotifikasiTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v("Login Services","Main Activity");
        LoginServices login = new LoginServices();
        if (!login.checkLogin(getApplicationContext())) {
            Log.v("Login Services","Main Activity Cek Login");

            Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(myIntent);
            finish();
        }

        setContentView(R.layout.activity_main);

        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, homeFragment);
        fragmentTransaction.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FirebaseMessaging.getInstance().subscribeToTopic("anytopic");
        Log.v("MAIN TAG", "anytopic");
    }

}
