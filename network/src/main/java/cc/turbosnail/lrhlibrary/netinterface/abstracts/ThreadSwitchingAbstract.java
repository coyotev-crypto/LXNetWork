package cc.turbosnail.lrhlibrary.netinterface.abstracts;

import java.util.HashMap;
import java.util.Map;

import cc.turbosnail.lrhlibrary.error.HttpErrorHandler;
import cc.turbosnail.lrhlibrary.netinterface.AppHandlerInterface;
import cc.turbosnail.lrhlibrary.netinterface.ThreadSwitching;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 线程切换
 * 并保存请求对象
 */
public abstract class ThreadSwitchingAbstract implements ThreadSwitching {
    protected HttpErrorHandler tHttpErrorHandler = new HttpErrorHandler<>();
    protected AppHandlerInterface appHandlerInterface;
    protected Map<Observer, Disposable> mDisposable = new HashMap<>();

    public ThreadSwitchingAbstract(AppHandlerInterface appHandlerInterface) {
        this.appHandlerInterface = appHandlerInterface;
    }


}
