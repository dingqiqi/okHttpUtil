package com.lakala.cloudpos.okhttputil.call;

import com.lakala.cloudpos.okhttputil.OkHttpUtil;
import com.lakala.cloudpos.okhttputil.inter.CallBack;
import com.lakala.cloudpos.okhttputil.mode.HttpResponse;
import com.lakala.cloudpos.okhttputil.request.OkHttpRequest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by dingqq on 2018/7/13.
 */

public class RequestCall {
    //默认超时时间
    private static final long DEFAULT_SECONDS = 20;

    private OkHttpRequest mOkHttpRequest;

    private Call mCall;

    private long readTimeOut;
    private long writeTimeOut;
    private long connectTimeOut;

    private SSLSocketFactory sSlSocketFactory;

    private HostnameVerifier hostNameVerify;

    public void sSlSocketFactory(SSLSocketFactory sSlSocketFactory) {
        this.sSlSocketFactory = sSlSocketFactory;
    }

    public void hostNameVerify(HostnameVerifier hostNameVerify) {
        this.hostNameVerify = hostNameVerify;
    }

    public RequestCall readTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;
        return this;
    }

    public RequestCall writeTimeOut(long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return this;
    }

    public RequestCall connectTimeOut(long connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
        return this;
    }

    public OkHttpRequest getOkHttpRequest() {
        return mOkHttpRequest;
    }

    public Call getCall() {
        return mCall;
    }

    public RequestCall(OkHttpRequest request) {
        this.mOkHttpRequest = request;
    }

    private void initBuilder(CallBack callBack) {
        OkHttpClient okHttpClient = OkHttpUtil.getInstance().getOkHttpClient();

        boolean mIsRun = false;

        if (okHttpClient == null) {
            mIsRun = true;
        } else {
            if (readTimeOut != 0 || writeTimeOut != 0 || connectTimeOut != 0) {
                mIsRun = true;
            }
        }

        if (mIsRun) {
            readTimeOut = (readTimeOut != 0) ? readTimeOut : DEFAULT_SECONDS;
            writeTimeOut = (writeTimeOut != 0) ? writeTimeOut : DEFAULT_SECONDS;
            connectTimeOut = (connectTimeOut != 0) ? connectTimeOut : DEFAULT_SECONDS;

            OkHttpClient.Builder builder = (okHttpClient == null) ? new OkHttpClient.Builder() : okHttpClient.newBuilder();

            builder.readTimeout(readTimeOut, TimeUnit.SECONDS)
                    .writeTimeout(writeTimeOut, TimeUnit.SECONDS)
                    .writeTimeout(connectTimeOut, TimeUnit.SECONDS);

            if (sSlSocketFactory != null) {
                builder.sslSocketFactory(sSlSocketFactory);
            }

            if (hostNameVerify != null) {
                builder.hostnameVerifier(hostNameVerify);
            }

            okHttpClient = builder.build();
            OkHttpUtil.getInstance().setOkHttpClient(okHttpClient);
        }

        mCall = okHttpClient.newCall(mOkHttpRequest.buildRequest(mOkHttpRequest.prepareProgress(mOkHttpRequest.buildRequestBody(), callBack)));
    }

    public void execute(final CallBack callBack) {
        if (callBack == null) {
            throw new IllegalArgumentException("callBack is null");
        }

        initBuilder(callBack);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (!call.isCanceled()) {
                    failCallback(callBack, 0, e.getMessage(), e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!call.isCanceled()) {

                    if (response.isSuccessful()) {
                        successCallback(callBack, callBack.parseResponse(response));

                    } else {
                        failCallback(callBack, response.code(), response.message(), new IllegalArgumentException("response code is not ok!"));
                    }
                }
            }
        });
    }

    public HttpResponse execute() throws IOException {
        initBuilder(null);

        return getResponse(mCall.execute());
    }

    private void failCallback(final CallBack callBack, final int code, final String msg, final Exception e) {

        OkHttpUtil.getInstance().getHandler().post(new Runnable() {
            @Override
            public void run() {
                callBack.onFail(code, msg, e);
            }
        });

    }

    private void successCallback(final CallBack callBack, final Object response) {

        OkHttpUtil.getInstance().getHandler().post(new Runnable() {
            @Override
            public void run() {
                callBack.onSuccess(response);
            }
        });

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

    public void cancel() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
