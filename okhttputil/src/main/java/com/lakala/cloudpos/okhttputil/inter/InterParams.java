package com.lakala.cloudpos.okhttputil.inter;

import com.lakala.cloudpos.okhttputil.build.OkHttpBuilder;

import java.util.Map;

/**
 * Created by dingqq on 2018/7/13.
 */

public interface InterParams {

    OkHttpBuilder params(Map<String, String> params);

    OkHttpBuilder addParam(String key, String value);

}
