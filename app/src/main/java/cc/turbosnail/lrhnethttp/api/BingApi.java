package cc.turbosnail.lrhnethttp.api;

import cc.turbosnail.lrhlibrary.annotation.BaseUrl;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @ProjectName: LXNetHttp
 * @Package: cc.turbosnail.lrhnethttp.api
 * @ClassName: BaiduApi
 * @Description: 必应
 * @Author: 李儒浩
 * @CreateDate: 2021/4/9 9:32
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/4/9 9:32
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@BaseUrl("http://192.168.1.105:8080")
public interface BingApi {
    @POST("/test")
    Observable<String> bingIndex();
}
