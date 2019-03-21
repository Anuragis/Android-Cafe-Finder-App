package com.example.anuragshinde.lab2cafeteria;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RecyclerViewActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private HashMap<String, Double> unsorted;
    private HashMap<String, Double> sorted;
    private HashMap<String, String> iconMap;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        setContentView(R.layout.activity_recycler_view);
        setUpToolBar();
        iconMap  = (HashMap<String,String>) getIntent().getSerializableExtra("Icons");
        unsorted  = (HashMap<String,Double>) getIntent().getSerializableExtra("Sorted_Data");
        sorted = sortByValue(unsorted);

        Log.d(TAG, "onCreate: started.");

        initImageBitmaps();
    }




    public void setUpToolBar(){
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout1);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        linearLayout = (LinearLayout)findViewById(R.id.linearLayout1);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(RecyclerViewActivity.this, drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(linearLayout)) {
                    drawerLayout.closeDrawer(linearLayout);
                } else {
                    drawerLayout.openDrawer(linearLayout);
                }
            }
        });
        actionBarDrawerToggle.syncState();

        TextView backToSearch = (TextView) findViewById(R.id.backToSearch);
        backToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(RecyclerViewActivity.this, MapActivity.class);
                startActivity(intent);*/
                //moveTaskToBack(true);
                onBackPressed();
            }
        });


        TextView newSearch = (TextView) findViewById(R.id.newSearch);
        newSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecyclerViewActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });


    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        //this is only needed if you have specific things
        //that you want to do when the user presses the back button.
        /* your specific things...*/
        super.onBackPressed();
    }

    public HashMap<String, Double> sortByValue(HashMap<String, Double> hm)
    {
        // Create a list from elements of HashMap
        List list = new LinkedList(hm.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue())
                        .compareTo(((Map.Entry) (o1)).getValue());
            }
        });

        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }

        return sortedHashMap;
    }

    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        for (Map.Entry<String, Double> entry : sorted.entrySet()) {
            mNames.add(entry.getKey()+"\n"+"Rating \u2605"+entry.getValue());
            mImageUrls.add(iconMap.get(entry.getKey()));
        }

        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
