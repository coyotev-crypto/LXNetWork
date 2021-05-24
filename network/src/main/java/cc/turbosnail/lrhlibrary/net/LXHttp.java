package cc.turbosnail.lrhlibrary.net;

/**
 * @ProjectName: LXNetHttp
 * @Package: cc.turbosnail.lrhlibrary.base
 * @ClassName: LXHttp
 * @Description: java类作用描述
 * @Author: 李儒浩
 * @CreateDate: 2021/4/9 14:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/4/9 14:36
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LXHttp{

    private static HttpClient httpClient = HttpClient.getInstance();

    public static HttpClient getInstance() {
        return httpClient;
    }

}
