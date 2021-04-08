package cc.turbosnail.lrhnethttp;


import cc.turbosnail.lrhlibrary.annotation.BaseUrl;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @version V0.1.0
 * @ClassName: baidu
 * @Description: 接口测试
 * @Author: 李儒浩
 * @editor 修改人
 * @Data 2021-1-25 22:09
 */
@BaseUrl("https://cn.bing.com/")
public interface baidu {
//    search?form=MOZLBR&pc=MOZI&q=api
    @GET("search?form=MOZLBR&pc=MOZI&q=api")
    Observable<String> driverStart();
}
