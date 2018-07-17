package com.lakala.cloudpos.okhttputil.build;

import com.lakala.cloudpos.okhttputil.request.PostFileRequest;
import com.lakala.cloudpos.okhttputil.call.RequestCall;

import java.io.File;

import okhttp3.MediaType;

/**
 * Created by dingqq on 2018/7/13.
 */

public class PostFileBuilder extends OkHttpBuilder<PostFileBuilder> {

    private File file;

    private MediaType mediaType;

    @Override
    public RequestCall build() {
        return new PostFileRequest(url, tag, heads, params, file, mediaType).build();
    }

    public PostFileBuilder file(File file) {
        this.file = file;
        return this;
    }

    public PostFileBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

}
