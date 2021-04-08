package cc.turbosnail.lrhnethttp;

import cc.turbosnail.lrhlibrary.BaseObserver;

/**
 * @ProjectName: LXNetHttp
 * @Package: cc.turbosnail.lrhnethttp
 * @ClassName: TestModel
 * @Description: java类作用描述
 * @Author: 李儒浩
 * @CreateDate: 2021/4/8 17:16
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/4/8 17:16
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TestModel implements MVP.Model{

    private ModelImpl model;

    public TestModel() {
        this.model = new ModelImpl();
    }

    @Override
    public void driverStart(BaseObserver baseObserver) {
        model.driverStart(baseObserver);
    }
}
