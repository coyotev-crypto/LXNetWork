package cc.turbosnail.lrhlibrary.error;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * @version V0.1.0
 * @ClassName: 网络请求错误：404，500等
 * @Description: java类作用描述
 * @Author: 李儒浩
 * @editor 修改人
 * @Data 2021-1-25 21:49
 */
public class HttpErrorHandler<T> implements Function<Throwable, Observable<T>> {
    @Override
    public Observable<T> apply(Throwable throwable) throws Exception {
        return Observable.error(ExceptionHandle.handleException(throwable));
    }
}
