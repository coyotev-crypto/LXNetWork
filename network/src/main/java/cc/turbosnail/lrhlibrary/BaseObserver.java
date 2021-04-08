package cc.turbosnail.lrhlibrary;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 父类 Observer
 * @author 李儒浩
 * @version V0.1.0
 * @editor 修改人
 * @team TurboSnail
 */
public abstract class BaseObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        //异常处理
        onFailure(e);
    }

    @Override
    public void onComplete() {

    }
    public abstract void onSuccess(T t); //成功
    public abstract void onFailure(Throwable e); //失败
}
