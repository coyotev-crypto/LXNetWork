package cc.turbosnail.lrhnethttp.network;

import cc.turbosnail.lrhlibrary.net.HttpClient;
import cc.turbosnail.lrhlibrary.netinterface.HttpConfig;
import okhttp3.OkHttpClient;

/**
 * @ProjectName: LXNetHttp
 * @Package: cc.turbosnail.lrhnethttp.network
 * @ClassName: MedicalGuideNetwork
 * @Description: 医学指南具体网络请求
 * @Author: 李儒浩
 * @CreateDate: 2021/4/9 9:33
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/4/9 9:33
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MedicalGuideNetwork {

    private static HttpClient mHttpClient = new HttpClient();

    public static HttpClient getInstance() {
        return mHttpClient
                .newBuilder()
                .setAppHandlerInterface(new MedicalGuideAppHandler())
                .builder();
    }
}
