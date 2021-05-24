package cc.turbosnail.lrhlibrary.netinterface.impl;

import cc.turbosnail.lrhlibrary.netinterface.AppHandlerInterface;
import cc.turbosnail.lrhlibrary.netinterface.ThreadSwitchingInterface;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ThreadSwitchingImpl extends ThreadSwitchingInterface {

    public ThreadSwitchingImpl(AppHandlerInterface appHandlerInterface) {
        super(appHandlerInterface);
    }

    @Override
    public void cancelRequest(Observer... observers) {
        if (observers.length == 0){
            return;
        }
        for (Observer observer : observers) {
            Disposable disposable = mDisposable.get(observer);
            if (disposable != null){
                disposable.dispose();
            }
        }
    }

    @Override
    public <T> ObservableTransformer<T, T> applySchedulers(Observer<T> observer) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable observable = upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorResumeNext(tHttpErrorHandler);
                if (appHandlerInterface.createAppErrorHandler() != null) {
                    observable.map(appHandlerInterface.createAppErrorHandler());
                }
                observable.subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //保存 Disposable
                        Disposable disposable = mDisposable.get(observer);
                        if (disposable == null){
                            mDisposable.put(observer,d);
                        }
                        observer.onSubscribe(d);
                    }

                    @Override
                    public void onNext(T t) {
                        observer.onNext(t);
                    }

                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
                return observable;
            }
        };
    }
}
