package cc.turbosnail.lrhlibrary.netinterface;

import retrofit2.Retrofit;

/**
 * 创建Retrofit对象
 */
public interface RetrofitInterface {
    Retrofit createRetrofit(Class service);
}
