package com.lakala.cloudpos.okhttputil.body;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * 上送进度
 * Created by dingqq on 2018/7/16.
 */
public class ProgressBody extends RequestBody {

    private RequestBody mRequestBody;

    private ProgressCallBack mCallBack;

    public ProgressBody(RequestBody mRequestBody, ProgressCallBack mCallBack) {
        this.mRequestBody = mRequestBody;
        this.mCallBack = mCallBack;
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        sink = Okio.buffer(new CustomSkin(sink));

        mRequestBody.writeTo(sink);

        sink.flush();
    }

    private class CustomSkin extends ForwardingSink {

        private long mCount = 0;

        CustomSkin(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);

            mCount += byteCount;

            if (mCallBack != null) {
                mCallBack.progress(mCount, contentLength());
            }
        }
    }

    public interface ProgressCallBack {
        void progress(long progress, long total);
    }
}

