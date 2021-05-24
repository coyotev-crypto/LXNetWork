package cc.turbosnail.lrhlibrary.netinterface;

import okhttp3.OkHttpClient;

/**
 * 创建OkHttpClient对象
 */
public interface OkHttpClientInterface {
    OkHttpClient createOkHttpClient();
}
