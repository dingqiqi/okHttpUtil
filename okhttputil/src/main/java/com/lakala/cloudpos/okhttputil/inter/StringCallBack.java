package com.lakala.cloudpos.okhttputil.inter;

import java.io.IOException;
import okhttp3.Response;

/**
 * Created by dingqq on 2018/7/13.
 */

public abstract class StringCallBack extends CallBack<String> {

    @Override
    public String parseResponse(Response response) throws IOException {

        return response.body().string();
    }

}
