package com.yarsi.yarsi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yarsi.yarsi.model.AuthPojo;
import com.yarsi.yarsi.services.AuthClient;
import com.yarsi.yarsi.services.LoginServices;
import com.yarsi.yarsi.services.ServiceGenerator;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Y700FROG on 23/11/2017.
 */

public class LoginActivity extends Activity {
    boolean doubleBackToExitPressedOnce = false;

    // User name
    private EditText et_Username;
    // Password
    private EditText et_Password;
    // Sign In
    private Button bt_SignIn;
    // Message
    private TextView tv_Message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v("Login Services", "Login Activity Redirected!");
        setContentView(R.layout.activity_login);
        //Text to display response result
        et_Username = (EditText) findViewById(R.id.etUsername);
        et_Password = (EditText) findViewById(R.id.etPassword);
        bt_SignIn = (Button) findViewById(R.id.btnLogin);

        // TODO dev test purpose
        et_Username.setText("aristio.rizki@gmail.com");
        et_Password.setText("123");
        bt_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateField(et_Username) || !validateField(et_Password)) {
                    return;
                }
                doLoginDua(et_Username.getText().toString(), et_Password.getText().toString());
            }
        });
    }

    private boolean validateField(TextView txtView) {
        if (txtView.getText() != null && !"".equals(txtView.getText().toString())) {
            return true;
        }
        Toast.makeText(LoginActivity.this, "Field wajib diisi!", Toast.LENGTH_SHORT).show();
        txtView.setFocusableInTouchMode(true);
        txtView.requestFocus();
        return false;
    }

    public void doLoginDua(String username, String password) {
        String client_id = BuildConfig.YarsiApiClientId;
        String client_secret = BuildConfig.YarsiApiClientSecret;
        AuthClient authClient = ServiceGenerator.createService(AuthClient.class, client_id, client_secret);

        String grant_type = "password";
        RequestBody reqUser = RequestBody.create(MultipartBody.FORM, username);
        RequestBody reqPass = RequestBody.create(MultipartBody.FORM, password);
        RequestBody reqGrantType = RequestBody.create(MultipartBody.FORM, grant_type);

        Call<AuthPojo> call = authClient.login(reqUser, reqPass, reqGrantType);
        call.enqueue(new Callback<AuthPojo>() {
            @Override
            public void onResponse(Call<AuthPojo> call, Response<AuthPojo> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();

                    LoginServices login = new LoginServices();
                    login.storeLoginData(getApplicationContext(), response.body());

                    Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(myIntent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login incorrect : ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthPojo> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "error!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


}