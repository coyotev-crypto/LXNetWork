package cc.turbosnail.lrhlibrary.base;

import cc.turbosnail.lrhlibrary.annotation.BaseUrl;
import cc.turbosnail.lrhlibrary.annotation.TestUrl;
import cc.turbosnail.lrhlibrary.error.HttpErrorHandler;
import cc.turbosnail.lrhlibrary.netinterface.INetWorkRequest;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @version V0.1.0
 * @ClassName: BaseHttp
 * @Description: 网络请求基类
 * @Author: 李儒浩
 * @editor 修改人
 * @Data 2021-1-25 21:18
 */
public abstract class BaseHttp implements INetWorkRequest {
    protected static INetworkRequiredInfo mINetworkRequiredInf;  //是否输出日志
    protected String mBaseUrl = null;
    public static boolean isNeiWaiNetWork = false;  //true 为内网 false为外网
    protected HttpErrorHandler tHttpErrorHandler = new HttpErrorHandler<>();

    /***
     *  启动应用就初始化
     * @param iNetworkRequiredInf
     */
    public static void init(INetworkRequiredInfo iNetworkRequiredInf) {
        mINetworkRequiredInf = iNetworkRequiredInf;
    }

    @Override
    public <T> ObservableTransformer<T, T> applySchedulers(Observer<T> observer) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable observable = upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorResumeNext(tHttpErrorHandler);
                if (createAppErrorHandler() != null){
                    observable.map(createAppErrorHandler());
                }
                observable.subscribe(observer);
                return observable;
            }
        };
    }


    @Override
    public <T> T createService(Class<T> service) {
        //先判断BaseUrl是否存在
        if (null == mBaseUrl) {
            if (parsingAnnotation(service)) {
                //如果不存在那就查找父类是否存在该注解
                parsingInterfacesAnnotation(service);
            }
        }
        //初始化请求对象
        if (mBaseUrl == null) {
            throw new NullPointerException("BaseUrl is null, the solution is to add @BaseUrl or @TestUrl to the class");
        }

        isNeiWaiNetWork = mBaseUrl.contains("192.168.");  //内网
        return createRetrofit(service).create(service);
    }

    /**
     * 注解解析
     */
    public boolean parsingAnnotation(Class clazz) {
        BaseUrl baseAnnotation = (BaseUrl) clazz.getAnnotation(BaseUrl.class);  //外网
        TestUrl testAnnotation = (TestUrl) clazz.getAnnotation(TestUrl.class); //内网
        if (baseAnnotation != null) {
            mBaseUrl = baseAnnotation.value();
            return true;
        } else if (testAnnotation != null) {
            mBaseUrl = testAnnotation.value();
            return true;
        }
        return false;
    }

    public void parsingInterfacesAnnotation(Class clazz) {
        Class[] interfaces = clazz.getInterfaces();
        for (Class anInterface : interfaces) {
            if (parsingAnnotation(anInterface))
                return;
        }
    }
}
