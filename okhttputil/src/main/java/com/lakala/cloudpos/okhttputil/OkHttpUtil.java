package com.lakala.cloudpos.okhttputil;

import android.os.Handler;
import android.os.Looper;

import com.lakala.cloudpos.okhttputil.build.GetBuilder;
import com.lakala.cloudpos.okhttputil.build.OtherBuilder;
import com.lakala.cloudpos.okhttputil.build.PostFileBuilder;
import com.lakala.cloudpos.okhttputil.build.PostFormBuilder;
import com.lakala.cloudpos.okhttputil.build.PostStringBuilder;

import okhttp3.OkHttpClient;

/**
 * Created by dingqq on 2018/7/13.
 */

public class OkHttpUtil {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private OkHttpClient mOkHttpClient;

    private OkHttpUtil() {
    }

    public static OkHttpUtil getInstance() {
        return OkHttpInstance.mInstance;
    }

    public static void initOkHttpClient(OkHttpClient okHttpClient) {
        if (okHttpClient != null) {
            getInstance().setOkHttpClient(okHttpClient);
        }
    }

    public Handler getHandler() {
        return mHandler;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public void setOkHttpClient(OkHttpClient mOkHttpClient) {
        this.mOkHttpClient = mOkHttpClient;
    }

    private static class OkHttpInstance {
        private static OkHttpUtil mInstance = new OkHttpUtil();
    }

    //get请求
    public static GetBuilder get() {
        return new GetBuilder();
    }

    //发送表单及文件数据
    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }

    //发送表单 key value形式
    public static PostFormBuilder postForm() {
        return new PostFormBuilder();
    }

    //发送文件
    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }

    //post请求 发送json字符串
    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    //其他请求
    public static OtherBuilder otherRequest() {
        return new OtherBuilder();
    }

}
