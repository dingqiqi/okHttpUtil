package com.lakala.cloudpos.okhttputil.build;

import com.lakala.cloudpos.okhttputil.request.PostStringRequest;
import com.lakala.cloudpos.okhttputil.call.RequestCall;

import okhttp3.MediaType;

/**
 * Created by dingqq on 2018/7/13.
 */

public class PostStringBuilder extends OkHttpBuilder<PostStringBuilder> {

    private String content;

    private MediaType mediaType;

    @Override
    public RequestCall build() {
        return new PostStringRequest(url, tag, heads, params, content, mediaType).build();
    }

    public PostStringBuilder content(String content) {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

}
