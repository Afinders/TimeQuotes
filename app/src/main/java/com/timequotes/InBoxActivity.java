package com.timequotes;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

/**
 *该类是与服务器通讯类，用于接收服务器发送的信件
 */

public class InBoxActivity extends AppCompatActivity {

    private RecyclerView InBoxRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_box);

        Toolbar toolbar = findViewById(R.id.in_box_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        InBoxRecyclerView = findViewById(R.id.in_box_recycler_view);

        SwipeRefreshLayout swipeRefresh = findViewById(R.id.in_box_swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    // TODO: 2019/2/26 刷新页面
    private void refresh(){

    }
}
