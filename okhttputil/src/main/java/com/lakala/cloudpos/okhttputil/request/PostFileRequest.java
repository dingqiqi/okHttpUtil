package com.lakala.cloudpos.okhttputil.request;

import android.text.TextUtils;

import com.lakala.cloudpos.okhttputil.OkHttpUtil;
import com.lakala.cloudpos.okhttputil.body.ProgressBody;
import com.lakala.cloudpos.okhttputil.call.RequestCall;
import com.lakala.cloudpos.okhttputil.inter.CallBack;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by dingqq on 2018/7/13.
 */

public class PostFileRequest extends OkHttpRequest {

    private static MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    private File file;

    private MediaType mediaType;

    public PostFileRequest(String url, Object tag, Map<String, String> heads, Map<String, String> params, File file, MediaType mediaType) {
        super(url, tag, heads, params);
        this.file = file;
        this.mediaType = mediaType;

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
        if (mediaType == null) {
            mediaType = MEDIA_TYPE_STREAM;
        }
        return RequestBody.create(mediaType, file);
    }

    @Override
    public Request buildRequest(RequestBody body) {
        return request.post(body).build();
    }
}
