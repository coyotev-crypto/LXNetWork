package cc.turbosnail.lrhnethttp.network;

import cc.turbosnail.lrhlibrary.base.LXHttp;
import cc.turbosnail.lrhnethttp.network.interceptor.MedicalGuideInterceptor;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;

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
public class MedicalGuideNetwork extends LXHttp {

    private static final MedicalGuideNetwork appNetWorkApi = new MedicalGuideNetwork();

    public static MedicalGuideNetwork getInstance() {
        return appNetWorkApi;
    }

    @Override
    public Interceptor createInterceptor() {
        return new MedicalGuideInterceptor();
    }

}
