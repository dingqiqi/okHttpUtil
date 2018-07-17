package com.lakala.cloudpos.okhttputil.inter;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by dingqq on 2018/7/13.
 */

public abstract class CallBack<T> {

    abstract public void onSuccess(T response);

    abstract public void onFail(int code, String msg, Exception e);

    abstract public T parseResponse(Response response) throws IOException;

    abstract public void progress(float progress, long total);
}
