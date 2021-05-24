package cc.turbosnail.lrhlibrary.netinterface.impl;

import cc.turbosnail.lrhlibrary.netinterface.AppHandlerInterface;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;

public class AppHandlerInterfaceImpl implements AppHandlerInterface {
    @Override
    public Interceptor[] createInterceptors() {
        return null;
    }

    @Override
    public <T> Function<T, T> createAppErrorHandler() {
        return null;
    }
}
