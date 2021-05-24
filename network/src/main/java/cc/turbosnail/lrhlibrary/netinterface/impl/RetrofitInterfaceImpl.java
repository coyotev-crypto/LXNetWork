package cc.turbosnail.lrhlibrary.netinterface.impl;

import com.google.gson.GsonBuilder;
import java.util.HashMap;
import cc.turbosnail.lrhlibrary.factory.adapter.MyTypeAdapterFactory;
import cc.turbosnail.lrhlibrary.netinterface.OkHttpClientInterface;
import cc.turbosnail.lrhlibrary.netinterface.ParsingAnnotationInterface;
import cc.turbosnail.lrhlibrary.netinterface.RetrofitInterface;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 创建Retrofit对象
 */
public class RetrofitInterfaceImpl implements RetrofitInterface {
    /**
     * 构建OkHttp对象的
     */
    protected OkHttpClientInterface mOkHttpClient;
    protected ParsingAnnotationInterface mParsingAnnotationInterface;

    private static HashMap<String, Retrofit> retroFitHashMap = new HashMap<>();  //Retrofit对象管理
    public RetrofitInterfaceImpl(OkHttpClientInterface mOkHttpClient, ParsingAnnotationInterface parsingAnnotationInterface) {
        this.mOkHttpClient = mOkHttpClient;
        this.mParsingAnnotationInterface = parsingAnnotationInterface;
    }


    @Override
    public Retrofit createRetrofit(Class service) {
        if (retroFitHashMap.get(mParsingAnnotationInterface.url + service.getName()) != null) {
            return retroFitHashMap.get(mParsingAnnotationInterface.url + service.getName());
        }
        OkHttpClient mOkHttpClient = this.mOkHttpClient.createOkHttpClient();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(mParsingAnnotationInterface.url)
                .client(mOkHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapterFactory(new MyTypeAdapterFactory<>()).create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retroFitHashMap.put(mParsingAnnotationInterface.url + service.getName(), mRetrofit);
        return mRetrofit;
    }
}
