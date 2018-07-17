package com.lakala.cloudpos.okhttputil.inter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by dingqq on 2018/7/13.
 */

public abstract class BitmapCallBack extends CallBack<Bitmap> {

    @Override
    public Bitmap parseResponse(Response response) throws IOException {
        return BitmapFactory.decodeStream(response.body().byteStream());
    }

}
