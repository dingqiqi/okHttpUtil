package com.lakala.cloudpos.okhttputil.inter;

import com.lakala.cloudpos.okhttputil.OkHttpUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by dingqq on 2018/7/13.
 */

public abstract class FileCallBack extends CallBack<File> {

    private String filePath;

    public FileCallBack(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public File parseResponse(Response response) throws IOException {
        return saveFile(response);
    }

    private File saveFile(Response response) throws IOException {
        ResponseBody body = response.body();
        final long total = body.contentLength();

        InputStream is = body.byteStream();
        byte[] buf = new byte[2048];
        int len;
        long sum = 0;
        FileOutputStream fos = null;

        File file = new File(filePath);
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            fos = new FileOutputStream(file);

            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);

                final long finalSum = sum;
                OkHttpUtil.getInstance().getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        progress(finalSum * 1.0f / total, total);
                    }
                });

            }
            fos.flush();

            return file;
        } finally {
            try {
                body.close();
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
