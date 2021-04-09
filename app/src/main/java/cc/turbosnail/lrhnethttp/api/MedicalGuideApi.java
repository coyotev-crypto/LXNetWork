package cc.turbosnail.lrhnethttp.api;

import cc.turbosnail.lrhlibrary.annotation.BaseUrl;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @ProjectName: voice-android
 * @Package: com.voice_android.voicerecords.api
 * @ClassName: MedicalGuideApi
 * @Description: 医学指南接口
 * @Author: 李儒浩
 * @CreateDate: 2021/3/2 16:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/3/2 16:36
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@BaseUrl(ApiConstants.MEDICAL_GUIDE_URL)
public interface MedicalGuideApi {

    /**
     * https://epocket.xingshulin.com/guide/getGuideListByCategoryId
     * 获取列表数据
     * @param requestBody
     * @return
     */
    @POST("/guide/getGuideListByCategoryId")
    Observable<String> getGuideListByCategoryId(@Body RequestBody requestBody);

    /**
     * https://epocket.xingshulin.com/guide/v2/36375
     * 详情
     * @param id
     * @return
     */
    @GET("guide/v2/{id}")
    Observable<String> getGuideDetDetails(@Path("id") Integer id);

}
