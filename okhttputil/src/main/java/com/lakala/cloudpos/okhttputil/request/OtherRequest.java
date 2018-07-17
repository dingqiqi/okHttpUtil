package com.lakala.cloudpos.okhttputil.request;

import android.text.TextUtils;

import com.lakala.cloudpos.okhttputil.Method;
import com.lakala.cloudpos.okhttputil.OkHttpUtil;
import com.lakala.cloudpos.okhttputil.body.ProgressBody;
import com.lakala.cloudpos.okhttputil.call.RequestCall;
import com.lakala.cloudpos.okhttputil.inter.CallBack;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by dingqq on 2018/7/13.
 */

public class OtherRequest extends OkHttpRequest {

    private final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");

    private String content;

    private MediaType mediaType;

    private Method method;

    public OtherRequest(String url, Object tag, Map<String, String> heads, Map<String, String> params, String content, MediaType mediaType, Method method) {
        super(url, tag, heads, params);
        this.content = content;
        this.mediaType = mediaType;
        this.method = method;

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
    public RequestBody buildRequestBody() {
        if (mediaType == null) {
            mediaType = JSON_TYPE;
        }
        return RequestBody.create(mediaType, content);
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
    public Request buildRequest(RequestBody body) {
        if (Method.GET == method) {
            return request.get().build();
        } else if (Method.POST == method) {
            return request.post(body).build();
        } else if (Method.PUT == method) {
            return request.put(body).build();
        } else if (Method.DELETE == method) {
            if (body == null) {
                return request.delete().build();
            }
            return request.delete(body).build();
        }

        return request.post(body).build();
    }
}
