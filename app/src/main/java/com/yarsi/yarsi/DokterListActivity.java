package com.yarsi.yarsi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yarsi.yarsi.model.Dokter;
import com.yarsi.yarsi.model.ListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DokterListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    final String URL_GET_DATA = "http://101.50.2.85:9095/api/dokter/listbyunitperawatan?unitPerawatan=2";
    private RecyclerView recyclerView;
    private DokterListAdapter adapter;
    private ArrayList<Dokter> dokterList;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokter_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSearchView = (SearchView) findViewById(R.id.search_view_dokter);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadHeroes();

        setupSearchView();

    }

    private void loadHeroes() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            dokterList = new ArrayList<Dokter>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                Dokter item = new Dokter();
                                item.setDokter(obj.getString("id").toString(),
                                        obj.getString("namaDepan").toString(),
                                        obj.getString("namaBelakang").toString(),
                                        obj.getString("spesialis").toString(),
                                        obj.getString("foto").toString(),
                                        obj.getString("unitPerawatan").toString(),
                                        obj.getString("jabatan").toString()
                                );
                                dokterList.add(item);
                            }
                            adapter = new DokterListAdapter(DokterListActivity.this, dokterList);
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

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");
    }

    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}
