package cc.turbosnail.lrhnethttp.network.interceptor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import cc.turbosnail.lrhnethttp.network.headers.MedicalGuildeHeaders;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Internal;

/**
 * @ProjectName: LXNetHttp
 * @Package: cc.turbosnail.lrhnethttp.network.interceptor
 * @ClassName: MedicalGuideInterceptor
 * @Description: 医学指南添加请求头
 * @Author: 李儒浩
 * @CreateDate: 2021/4/9 9:34
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/4/9 9:34
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MedicalGuideInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        MedicalGuildeHeaders headers = new MedicalGuildeHeaders();
        request = request.newBuilder()
                .addHeader("X-User-Token", "7B34643831333536343631636634633263626133353637383738383935396438387D2C7B37396332353961622D303837652D336164652D623631342D6435383263316463626531347D2C7B66616C73657D2C7B307D2C7B307D2C7B38653630326366383766376134393336393933323765343431626236373631647D2C7B39623339393235643439643863323432653337386366303131633363623861637D2C7B323032312D30332D30322031353A35353A32317D2C7B372E31362E307D2C7B65706F636B65747D2C7B65706F636B65745F372E31362E302D616E64726F69645F32327D")
                .addHeader("X-User-Agent", "epocket/7.16.0 (VOG-AL00,Android 5.1.1) net/WIFI channelId/016")
                .addHeader("X-Security-Id", headers.getXSecurityId())
                .addHeader("X-Trace-Id", headers.getXTraceId())
                .addHeader("Content-Type", " application/json; charset=UTF-8")
                .build();
        return chain.proceed(request);
    }
}
