package cc.turbosnail.lrhlibrary.netinterface;

import io.reactivex.functions.Function;
import okhttp3.Interceptor;

/**
 * 应用错误处理
 */
public interface AppHandlerInterface {
    Interceptor[] createInterceptors();        //拦截
    <T> Function<T, T> createAppErrorHandler();  //应用错误
}
