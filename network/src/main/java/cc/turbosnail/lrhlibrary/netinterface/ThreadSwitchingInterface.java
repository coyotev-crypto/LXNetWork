package cc.turbosnail.lrhlibrary.netinterface;

import java.util.HashMap;
import java.util.Map;

import cc.turbosnail.lrhlibrary.error.HttpErrorHandler;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 线程切换
 * 并保存请求对象
 */
public abstract class ThreadSwitchingInterface {
    protected HttpErrorHandler tHttpErrorHandler = new HttpErrorHandler<>();
    protected AppHandlerInterface appHandlerInterface;
    protected Map<Observer, Disposable> mDisposable = new HashMap<>();
    public ThreadSwitchingInterface(AppHandlerInterface appHandlerInterface) {
        this.appHandlerInterface = appHandlerInterface;
    }

    /**
     * 取消网络请求
     */
    public abstract void cancelRequest(Observer... observer);

    public abstract <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer);
}
