package cc.turbosnail.lrhnethttp.mvp.model;

import cc.turbosnail.lrhlibrary.BaseObserver;
import cc.turbosnail.lrhlibrary.base.LXHttp;
import cc.turbosnail.lrhnethttp.api.BingApi;

/**
 * @ProjectName: LXNetHttp
 * @Package: cc.turbosnail.lrhnethttp.mvp.model
 * @ClassName: MedicalGuideModel
 * @Description: java类作用描述
 * @Author: 李儒浩
 * @CreateDate: 2021/4/9 10:48
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/4/9 10:48
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MedicalGuideModel {
    //添加@Ignore忽略的实现
    public int bingIndex(BaseObserver baseObserver){
        LXHttp.getInstance().createService(BingApi.class)
                .bingIndex()
                .compose(LXHttp.getInstance().applySchedulers(baseObserver));
        return 1;
    }
}
