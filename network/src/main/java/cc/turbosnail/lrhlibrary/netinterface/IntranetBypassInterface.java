package cc.turbosnail.lrhlibrary.netinterface;

import okhttp3.OkHttpClient;

/**
 * 内网绕过
 */
public interface IntranetBypassInterface {
    OkHttpClient.Builder bypassHttps(OkHttpClient.Builder build); //内网绕过
}
