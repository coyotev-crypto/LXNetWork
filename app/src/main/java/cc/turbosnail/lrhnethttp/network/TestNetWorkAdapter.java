package cc.turbosnail.lrhnethttp.network;

import cc.turbosnail.lrhlibrary.adapter.NetworkAdapter;
import cc.turbosnail.lrhlibrary.net.HttpClient;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @ProjectName LXNetHttp
 * @Package cc.turbosnail.lrhnethttp.network
 * @Describe 描述
 * @Author wolf king
 * @editor 修改人
 * @CreateDate 2021/5/28
 * @UpdateDate 2021/5/28 更新时间
 */
public class TestNetWorkAdapter extends NetworkAdapter {

    @Override
    public OkHttpClient createOkHttpClient() {
        return new OkHttpClient();
    }

    @Override
    public Retrofit createRetrofit(Class service) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(createOkHttpClient())
                .baseUrl(parsingAnnotation.url)
                .build();
        return retrofit;
    }
}
