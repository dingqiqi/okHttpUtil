package com.lakala.cloudpos.okhttputil.request;

import android.text.TextUtils;

import com.lakala.cloudpos.okhttputil.OkHttpUtil;
import com.lakala.cloudpos.okhttputil.body.ProgressBody;
import com.lakala.cloudpos.okhttputil.call.RequestCall;
import com.lakala.cloudpos.okhttputil.inter.CallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by dingqq on 2018/7/13.
 */

public class PostFromRequest extends OkHttpRequest {

    private static MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    public List<File> files = new ArrayList<>();

    public PostFromRequest(String url, Object tag, Map<String, String> heads, Map<String, String> params, List<File> files) {
        super(url, tag, heads, params);
        this.files = files;

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

        if (files != null && files.size() > 0) {
            MultipartBody.Builder builder = new MultipartBody.Builder();

            if (params != null) {
                for (String key : params.keySet()) {
                    builder.addFormDataPart(key, params.get(key));
                }
            }

            for (File file : files) {
                builder.addFormDataPart("file", file.getName(), RequestBody.create(MEDIA_TYPE_STREAM, file));
            }

            return builder.build();
        } else {
            FormBody.Builder builder = new FormBody.Builder();

            if (params != null) {
                for (String key : params.keySet()) {
                    builder.addEncoded(key, params.get(key));
                }
            }

            return builder.build();
        }
    }

    @Override
    public Request buildRequest(RequestBody body) {
        return request.post(body).build();
    }
}
