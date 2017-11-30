package com.yarsi.yarsi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yarsi.yarsi.model.AuthPojo;
import com.yarsi.yarsi.services.AuthClient;
import com.yarsi.yarsi.services.ServiceGenerator;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Y700FROG on 23/11/2017.
 */

public class LoginActivity extends Activity {

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

        setContentView(R.layout.activity_login);
        //Text to display response result
        et_Username = (EditText) findViewById(R.id.etUsername);
        et_Password = (EditText) findViewById(R.id.etPassword);
        bt_SignIn = (Button) findViewById(R.id.btnLogin);
        tv_Message = (TextView) findViewById(R.id.tvStatus);

        bt_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                doPost("http://101.50.2.85:10010/oauth/token");
//                Login login = new Login();
//                login.execute("http://101.50.2.85:10010/oauth/token");

//                makeRequestWithOkHttp("http://101.50.2.85:10010/oauth/token");

                doLoginDua();
            }
        });
    }

    public void doLoginDua() {
        String client_id = BuildConfig.YarsiApiClientId;
        String client_secret = BuildConfig.YarsiApiClientSecret;
        AuthClient authClient = ServiceGenerator.createService(AuthClient.class, client_id, client_secret);

        String username = "aristio.rizki@gmail.com";
        String password = "123";
        String grant_type = "password";
        RequestBody reqUser = RequestBody.create(MultipartBody.FORM, username);
        RequestBody reqPass = RequestBody.create(MultipartBody.FORM, password);
        RequestBody reqGrantType = RequestBody.create(MultipartBody.FORM, grant_type);

        MultipartBody reqBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", username)
                .addFormDataPart("password", password)
                .addFormDataPart("grant_type", grant_type)
                .build();

        Call<AuthPojo> call = authClient.login(reqUser, reqPass, reqGrantType);
        call.enqueue(new Callback<AuthPojo>() {
            @Override
            public void onResponse(Call<AuthPojo> call, Response<AuthPojo> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Success : " + response.body().getAccess_token(), Toast.LENGTH_SHORT).show();
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

//    private void makeRequestWithOkHttp(String url) {
//        String client_id = "aplikasijs";
//        String client_secret = "aplikasi123";
//        String valueDecoded = Base64.encodeToString((client_id + ":" + client_secret).getBytes(), Base64.NO_WRAP);
//        String basicHeader = valueDecoded.toString();
//        String basicAuthHeader = "Basic " + basicHeader;
//
////        JSONObject postdata = new JSONObject();
////        try {
////            postdata.put("username", "aristio.rizki@gmail.com");
////            postdata.put("password", "123");
////            postdata.put("grant_type", "password");
////        } catch (JSONException e) {
////            Log.v("JSON Exception", e.toString());
////            // TODO Auto-generated catch block
////            e.printStackTrace();
////        }
////        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postdata.toString());
//
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("username", "aristio.rizki@gmail.com")
//                .addFormDataPart("password", "123")
//                .addFormDataPart("grant_type", "password")
//                .build();
//
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request
//                .Builder()
//                .header("Authorization", basicAuthHeader)
//                .post(requestBody)
//                .url(url)
//                .build();
//
//        System.out.println(request);
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final String result = response.body().string();
//                try {
//                    JSONObject json = new JSONObject(result);
//                    System.out.println("Access Token > " + json.getString("access_token"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }


}