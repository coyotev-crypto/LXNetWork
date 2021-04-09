package cc.turbosnail.lrhnethttp.mvp.contract;

import cc.turbosnail.lrhannotation.Ignore;
import cc.turbosnail.lrhannotation.LXModel;
import cc.turbosnail.lrhannotation.LXModelImpl;
import cc.turbosnail.lrhlibrary.BaseObserver;
import cc.turbosnail.lrhnethttp.api.MedicalGuideApi;
import cc.turbosnail.lrhnethttp.mvp.model.MedicalGuideModel;
import cc.turbosnail.lrhnethttp.network.MedicalGuideNetwork;
import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @ProjectName: LXNetHttp
 * @Package: cc.turbosnail.lrhnethttp.mvp.contract
 * @ClassName: MedicalGuideContract
 * @Description: 模拟自定义
 * @Author: 李儒浩
 * @CreateDate: 2021/4/9 10:48
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/4/9 10:48
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface MedicalGuideContract {


    @LXModel(value = "MedicalGuideModel", networkEngine = MedicalGuideNetwork.class, networkService = MedicalGuideApi.class)
    @LXModelImpl(MedicalGuideModel.class) //被忽略的实现
    interface Model {

        void getGuideListByCategoryId(RequestBody requestBody, BaseObserver baseObserver);

        Observable<String> getGuideDetDetails(Integer id);

        //如果你这个请求不是使用MedicalGuideNetwork发起的请求或者想自己实现请添加@Ignore注解
        @Ignore
        void bingIndex(BaseObserver observer);
    }

    interface View {
    }

    interface Presenter {
    }
}
