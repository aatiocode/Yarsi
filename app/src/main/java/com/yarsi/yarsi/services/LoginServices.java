package com.yarsi.yarsi.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.yarsi.yarsi.model.AuthPojo;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Y700FROG on 08/12/2017.
 */

public class LoginServices {

    boolean isLoggedIn = false;

    public boolean checkLogin(Context ctx) {
        isLoggedIn = false;

        SharedPreferences pref = ctx.getSharedPreferences("YarsiPref", 0); // 0 - for private mode
        if (pref != null) {
            long exp = pref.getLong("token_expires", 0);// getting Long
            if (exp != 0) {
                Calendar calExp = Calendar.getInstance();
                calExp.setTimeInMillis(exp);

                Date dtExp = calExp.getTime();
                Date dtNow = new Date();
                Log.v("Login Services","Main Activity Cek Login" + dtExp.toString());
                Log.v("Login Services","Main Activity Cek Login" + dtNow.toString());
                if (dtExp.getTime() < dtNow.getTime()){
                    isLoggedIn = false;
                } else {
                    isLoggedIn = true;
                }
                Log.v("Login Services","Main Activity Cek Login" + isLoggedIn);

            }
        }

        return isLoggedIn;
    }

    public void storeLoginData(Context ctx, AuthPojo pojo) {
        SharedPreferences pref = ctx.getSharedPreferences("YarsiPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("access_token", pojo.getAccess_token());
        editor.putString("token_type", pojo.getToken_type());
        editor.putString("expires_in", pojo.getExpires_in());
        editor.putString("scope", pojo.getScope());

        int expSecs = Integer.parseInt(pojo.getExpires_in());
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.SECOND, expSecs);
        editor.putLong("token_expires", cal.getTimeInMillis());

        editor.commit();
        Log.i("Login Services","Login Data Stored");
    }

    public void logoutProcess(Context ctx) {
        SharedPreferences pref = ctx.getSharedPreferences("YarsiPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}
