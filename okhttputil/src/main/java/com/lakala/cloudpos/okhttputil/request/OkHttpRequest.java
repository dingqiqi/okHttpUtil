package com.lakala.cloudpos.okhttputil.request;

import com.lakala.cloudpos.okhttputil.call.RequestCall;
import com.lakala.cloudpos.okhttputil.inter.CallBack;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by dingqq on 2018/7/13.
 */

public abstract class OkHttpRequest {

    protected String url;
    protected Object tag;
    protected Map<String, String> heads;
    protected Map<String, String> params;

    protected Request.Builder request = new Request.Builder();

    public OkHttpRequest(String url, Object tag, Map<String, String> heads, Map<String, String> params) {
        this.url = url;
        this.tag = tag;
        this.heads = heads;
        this.params = params;
    }

    public abstract RequestCall build();

    //创建RequestBody
    public abstract RequestBody buildRequestBody();

    //监听上送进度
    public abstract RequestBody prepareProgress(RequestBody body, CallBack callBack);

    //生成请求
    public abstract Request buildRequest(RequestBody body);

}
