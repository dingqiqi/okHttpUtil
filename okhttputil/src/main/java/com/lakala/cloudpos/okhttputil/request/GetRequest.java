package com.lakala.cloudpos.okhttputil.request;

import android.text.TextUtils;

import com.lakala.cloudpos.okhttputil.OkHttpUtil;
import com.lakala.cloudpos.okhttputil.body.ProgressBody;
import com.lakala.cloudpos.okhttputil.call.RequestCall;
import com.lakala.cloudpos.okhttputil.inter.CallBack;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by dingqq on 2018/7/13.
 */

public class GetRequest extends OkHttpRequest {

    public GetRequest(String url, Object tag, Map<String, String> heads) {
        super(url, tag, heads, null);

        initBuilder();
    }

    private void initBuilder() {
        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException("url is null");
        }

        request.url(url).tag(tag);
        addHeader();
    }

    private void addHeader() {
        if (heads != null) {
            for (String key : heads.keySet()) {
                request.addHeader(key, heads.get(key));
            }
        }
    }

    @Override
    public RequestCall build() {
        return new RequestCall(this);
    }

    @Override
    public RequestBody prepareProgress(RequestBody body, final CallBack callBack) {
        if (body == null) {
            return null;
        }

        if (callBack == null) {
            return body;
        }

        return new ProgressBody(body, new ProgressBody.ProgressCallBack() {
            @Override
            public void progress(final long progress, final long total) {
                OkHttpUtil.getInstance().getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.progress(progress / total, total);
                    }
                });
            }
        });
    }

    @Override
    public RequestBody buildRequestBody() {
        return null;
    }

    @Override
    public Request buildRequest(RequestBody body) {
        return request.get().build();
    }
}
