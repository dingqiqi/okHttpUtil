package com.lakala.cloudpos.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lakala.cloudpos.okhttputil.OkHttpUtil;
import com.lakala.cloudpos.okhttputil.inter.ResponseCallBack;
import com.lakala.cloudpos.okhttputil.inter.StringCallBack;
import com.lakala.cloudpos.okhttputil.mode.HttpResponse;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        OkHttpUtil.get().url("http://wallpaper.apc.360.cn/index.php?c=WallPaper&a=search&start=0&count=99&kw=%E6%AF%95%E4%B8%9A&start=0&count=99")
                .build().execute(new ResponseCallBack() {
            @Override
            public void onSuccess(HttpResponse response) {
                Log.i("aaa", "onSuccess:" + response.getBody());
            }

            @Override
            public void onFail(int code, String msg, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void progress(float progress, long total) {

            }
        });

    }
}
