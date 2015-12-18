package com.buyi.fakeapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityResult extends AppCompatActivity {

    @Bind(com.buyi.fakeapp.R.id.opt)
    Button opt;
    int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.buyi.fakeapp.R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(com.buyi.fakeapp.R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(com.buyi.fakeapp.R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    // 模拟抢红包界面
    @OnClick(com.buyi.fakeapp.R.id.opt)
    public void submit(View view) {
        if (flag == 0) {
            opt.setText("拆红包");
            flag = 1;
        } else {
            opt.setText("自动领取完毕");
            Toast.makeText(this, "红包已进入你银行账号", Toast.LENGTH_LONG).show();
        }
    }

}
