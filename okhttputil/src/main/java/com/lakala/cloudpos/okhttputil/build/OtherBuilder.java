package com.lakala.cloudpos.okhttputil.build;

import android.net.Uri;

import com.lakala.cloudpos.okhttputil.Method;
import com.lakala.cloudpos.okhttputil.call.RequestCall;
import com.lakala.cloudpos.okhttputil.request.OtherRequest;

import java.net.URI;

import okhttp3.MediaType;

/**
 * Created by dingqq on 2018/7/13.
 */

public class OtherBuilder extends OkHttpBuilder<OtherBuilder> {

    private String content;

    private MediaType mediaType;

    private Method method;

    @Override
    public RequestCall build() {
        //get 拼接参数
        if (Method.GET == method) {
            buildParams();
        }

        return new OtherRequest(url, tag, heads, params, content, mediaType, method).build();
    }

    private void buildParams() {
        if (params != null) {
            Uri.Builder builder = Uri.parse(url).buildUpon();

            for (String key : params.keySet()) {
                builder.appendQueryParameter(key, params.get(key));
            }

            url = builder.build().toString();
        }
    }

    public void method(Method method) {
        this.method = method;
    }

    public OtherBuilder content(String content) {
        this.content = content;
        return this;
    }

    public OtherBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

}
