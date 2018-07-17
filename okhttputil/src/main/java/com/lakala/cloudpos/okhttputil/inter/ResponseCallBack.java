package com.lakala.cloudpos.okhttputil.inter;

import com.lakala.cloudpos.okhttputil.mode.HttpResponse;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by dingqq on 2018/7/13.
 */

public abstract class ResponseCallBack extends CallBack<HttpResponse> {

    @Override
    public HttpResponse parseResponse(Response response) {
        return getResponse(response);
    }

    private HttpResponse getResponse(Response response) {
        if (response == null) {
            return null;
        }

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setCode(response.code());
        httpResponse.setMessage(response.message());

        ResponseBody body = response.body();
        if (body != null) {
            try {
                httpResponse.setBody(body.string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return httpResponse;
    }
}
