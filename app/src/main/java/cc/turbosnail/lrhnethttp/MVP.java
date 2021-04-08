package cc.turbosnail.lrhnethttp;


import cc.turbosnail.lrhannotation.Ignore;
import cc.turbosnail.lrhannotation.LXModel;
import cc.turbosnail.lrhlibrary.BaseObserver;
import cc.turbosnail.lrhlibrary.LrhHttp;
import io.reactivex.Observable;

/**
 * @ProjectName: LrhNetHttp
 * @Package: cc.turbosnail.xpqnethttp
 * @ClassName: Model
 * @Description: java类作用描述
 * @Author: 李儒浩
 * @CreateDate: 2021/4/2 15:43
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/4/2 15:43
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */


public interface MVP {

    @LXModel(value = "TestModel1", networkService = baidu.class)
    interface Model{

        @Ignore
        Observable<String> driverStart(BaseObserver baseObserver);

//        void driverStart(BaseObserver baseObserver);
    }
}
