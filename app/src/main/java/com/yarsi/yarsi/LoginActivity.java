package com.yarsi.yarsi;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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

    private boolean status = false;

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
                Login login = new Login();
                login.execute("http://101.50.2.85:10010/oauth/token");
            }
        });
    }

    private class Login extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            status = false;
            tv_Message.setText("Progress...");
        }

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                String client_id = "aplikasijs";
                String client_secret = "aplikasi123";
//                byte[] valueDecoded = Base64.decode((client_id + ":" + client_secret).getBytes("UTF-8"), Base64.DEFAULT);
                String valueDecoded = Base64.encodeToString((client_id + ":" + client_secret).getBytes(), Base64.NO_WRAP);

                String basicheader = valueDecoded.toString();

                String basicAuthHeader = "Basic " + basicheader;

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Authorization", basicAuthHeader);
                urlConnection.setRequestMethod("POST");

                //Building URI
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", "admin")
                        .appendQueryParameter("password", "admin");

                //Getting object of OutputStream from urlConnection to write some data to stream
                DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("grant_type", "password");
                jsonParam.put("username","aristio.rizki@gmail.com");
                jsonParam.put("password","123");
                outputStream.write(jsonParam.toString().getBytes());
                outputStream.flush();

                //Writer to write data to OutputStream
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                bufferedWriter.write(builder.build().getEncodedQuery());
                bufferedWriter.flush();
                bufferedWriter.close();

                outputStream.close();
                urlConnection.connect();

                //Getting inputstream from connection, that is response which we got from server
//                InputStream inputStream = urlConnection.getInputStream();

                //Reading the response
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String s = bufferedReader.readLine();
                bufferedReader.close();

                if (s != null) {
                    status = true;
                }
                //Returning the response message to onPostExecute method
                return s;
            } catch (JSONException e) {
                Log.e("Error JSON : ", e.getMessage(), e);
            } catch (IOException e) {
                Log.e("Error IO: ", e.getMessage(), e);
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (status) {
                tv_Message.setText("OK");
            } else {
                tv_Message.setText("Failed!");
            }
        }
    }
}