package com.lakala.cloudpos.okhttputil.build;

import com.lakala.cloudpos.okhttputil.inter.InterParams;
import com.lakala.cloudpos.okhttputil.request.PostFromRequest;
import com.lakala.cloudpos.okhttputil.call.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dingqq on 2018/7/13.
 */

public class PostFormBuilder extends OkHttpBuilder<PostFormBuilder> implements InterParams {

    private List<File> files = new ArrayList<>();

    @Override
    public RequestCall build() {
        return new PostFromRequest(url, tag, heads, params, files).build();
    }

    @Override
    public PostFormBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public PostFormBuilder addParam(String key, String value) {
        if (params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, value);

        return this;
    }

    public PostFormBuilder files(List<File> files) {
        this.files = files;
        return this;
    }

    public PostFormBuilder addFile(File files) {
        this.files.add(files);
        return this;
    }
}
