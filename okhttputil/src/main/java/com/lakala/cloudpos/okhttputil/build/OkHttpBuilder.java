package com.lakala.cloudpos.okhttputil.build;

import com.lakala.cloudpos.okhttputil.call.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by dingqq on 2018/7/13.
 */

public abstract class OkHttpBuilder<T extends OkHttpBuilder> {

    protected String url;
    protected Object tag;
    protected Map<String, String> heads;
    protected Map<String, String> params;

    public T url(String url) {
        this.url = url;
        return (T) this;
    }

    public T tag(Object tag) {
        this.tag = tag;
        return (T) this;
    }

    public T addHead(String key, String value) {
        if (heads == null) {
            heads = new LinkedHashMap<>();
        }
        this.heads.put(key, value);
        return (T) this;
    }

    public T heads(Map<String, String> heads) {
        this.heads = heads;
        return (T) this;
    }

    public abstract RequestCall build();
}
