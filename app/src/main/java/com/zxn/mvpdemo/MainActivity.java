package com.zxn.mvpdemo;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends MvpActivity {

    @BindView(R.id.rv)
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //ButterKnife.bind(this);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        MainAdapter adapter = new MainAdapter();
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("this is item:" + i);
        }
        adapter.setNewData(data);

        rv.setAdapter(adapter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }
}
