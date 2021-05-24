package cc.turbosnail.lrhnethttp.mvp.contract;

import cc.turbosnail.lrhannotation.LXModel;
import cc.turbosnail.lrhlibrary.BaseObserver;
import cc.turbosnail.lrhnethttp.api.BingApi;

/**
 * @ProjectName: LXNetHttp
 * @Package: cc.turbosnail.lrhnethttp.mvp.contract
 * @ClassName: BingContract
 * @Description: java类作用描述
 * @Author: 李儒浩
 * @CreateDate: 2021/4/9 9:42
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/4/9 9:42
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface BingContract {

    /**
     * value 生成文件的名称
     * networkService 请求接口定义的接口
     * BaseObserver 封装好的回调会请求回来会切回ui线程
     * 注意：model里面定义的必须与BingApi一样
     * 如果存在返回值请勿添加BaseObserver参数
     */
    @LXModel(networkService = BingApi.class)
    interface Model {
        void bingIndex(BaseObserver observer);
    }

    interface View {
        void onSuccess();
        void onError(String message);
    }

}
