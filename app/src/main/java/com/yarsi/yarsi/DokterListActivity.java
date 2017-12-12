package com.yarsi.yarsi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yarsi.yarsi.model.Dokter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DokterListActivity extends AppCompatActivity {

    final String URL_GET_DATA = "http://localhost:9095/api/dokter/listbyunitperawatan?unitPerawatan=2";
    RecyclerView recyclerView;
    DokterListAdapter adapter;
    List<Dokter> dokterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokter_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dokterList = new ArrayList<>();

        loadHeroes();

    }

    private void loadHeroes() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);

                                Dokter dokter = new Dokter(
                                        obj.getString("id"),
                                        obj.getString("namaDepan"),
                                        obj.getString("namaBelakang"),
                                        obj.getString("spesialis"),
                                        obj.getString("foto"),
                                        obj.getString("unitPerawatan"),
                                        obj.getString("jabatan")
                                );

                                dokterList.add(dokter);
                            }

                            adapter = new DokterListAdapter(dokterList, getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
