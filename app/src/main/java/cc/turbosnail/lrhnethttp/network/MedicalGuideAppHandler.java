package cc.turbosnail.lrhnethttp.network;

import cc.turbosnail.lrhlibrary.netinterface.AppHandlerInterface;
import cc.turbosnail.lrhnethttp.network.interceptor.MedicalGuideInterceptor;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;

public class MedicalGuideAppHandler implements AppHandlerInterface {

    @Override
    public Interceptor[] createInterceptors() {
        return new Interceptor[]{new MedicalGuideInterceptor()};
    }

    @Override
    public <T> Function<T, T> createAppErrorHandler() {
        return null;
    }
}
