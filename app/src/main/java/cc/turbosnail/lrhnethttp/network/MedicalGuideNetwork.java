package cc.turbosnail.lrhnethttp.network;

import cc.turbosnail.lrhlibrary.LrhHttp;
import cc.turbosnail.lrhlibrary.base.BaseHttp;
import cc.turbosnail.lrhnethttp.api.MedicalGuideApi;
import cc.turbosnail.lrhnethttp.mvp.contract.MedicalGuideContract;
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
public class MedicalGuideNetwork extends LrhHttp {

    private static final MedicalGuideNetwork appNetWorkApi = new MedicalGuideNetwork();

    public static MedicalGuideNetwork getInstance() {
        return appNetWorkApi;
    }


    @Override
    protected Interceptor getInterceptor() {
        //添加头文件
        return new MedicalGuideInterceptor();
    }


    /**
     * 错误处理
     * @param <T>
     * @return
     */
    @Override
    protected <T> Function<T, T> getAppErrorHandler() {
        return null;
    }
}
