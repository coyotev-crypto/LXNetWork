package cc.turbosnail.lrhlibrary.netinterface;

import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;

/**
 * 提供应用层使用接口
 * 创建请求api对象
 * 线程切换
 * 取消网络请求
 */
public interface ServiceInterface{
    <T> T createService(Class<T> service);
    <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer);
    void cancelRequest(Observer... observer);
}
