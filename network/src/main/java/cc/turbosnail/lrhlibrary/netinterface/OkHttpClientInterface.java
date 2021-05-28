package cc.turbosnail.lrhlibrary.netinterface;

import okhttp3.OkHttpClient;

/**
 * 创建OkHttpClient对象
 */
public interface OkHttpClientInterface {

    OkHttpClient createOkHttpClient();

    void setAppHandlerInterface(AppHandlerInterface mAppHandlerInterface);

    void setIntranetBypassInterface(IntranetBypassInterface mIntranetBypassInterface);
}
