package com.yarsi.yarsi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.yarsi.yarsi.model.ListItem;

import java.util.ArrayList;

public class DokterFormActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private ArrayList<ListItem> allList;

    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokter_form);

        mSearchView = (SearchView) findViewById(R.id.search_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        setList();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyRecyclerAdapter(this, allList);
        mRecyclerView.setAdapter(adapter);

        setupSearchView();


    }


    public void setList() {

        allList = new ArrayList<ListItem>();

        ListItem item = new ListItem();
        item.setData("Google 2", "http://www.menucool.com/slider/prod/image-slider-1.jpg");
        allList.add(item);
        item = new ListItem();
        item.setData("Google 1", "http://www.menucool.com/slider/prod/image-slider-2.jpg");
        allList.add(item);

//        item = new ListItem();
//        item.setData("Apple", "USA", R.drawable.apple);
//        allList.add(item);

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
