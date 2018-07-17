package com.lakala.cloudpos.okhttputil.build;

import android.net.Uri;

import com.lakala.cloudpos.okhttputil.request.GetRequest;
import com.lakala.cloudpos.okhttputil.call.RequestCall;

/**
 * Created by dingqq on 2018/7/13.
 */

public class GetBuilder extends OkHttpBuilder<GetBuilder> {

    @Override
    public RequestCall build() {

        appendParams();

        return new GetRequest(url, tag, heads).build();
    }

    //url 后面追加参数
    private void appendParams() {
        if (params != null) {
            Uri.Builder builder = Uri.parse(url).buildUpon();

            for (String key : params.keySet()) {
                builder.appendQueryParameter(key, params.get(key));
            }

            url = builder.build().toString();
        }
    }

}
