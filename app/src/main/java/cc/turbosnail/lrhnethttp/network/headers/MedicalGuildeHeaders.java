package cc.turbosnail.lrhnethttp.network.headers;

import java.util.Random;

/**
 * @ProjectName: voice-android
 * @Package: com.voice_android.voicerecords.utils
 * @ClassName: MedicalGuildeHeaders
 * @Description: 生成请求头
 * @Author: 李儒浩
 * @CreateDate: 2021/3/5 17:27
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/3/5 17:27
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MedicalGuildeHeaders {
    public String XUserToken;
    public String XSecurityId;
    public String XTraceId;

    public void setXSecurityId(String XSecurityId) {
        this.XSecurityId = XSecurityId;
    }

    public void setXTraceId(String XTraceId) {
        this.XTraceId = XTraceId;
    }

    public void setXUserToken(String XUserToken) {
        this.XUserToken = XUserToken;
    }

    public String getXUserToken() {
        return getTokenString(456);
    }

    public String getXSecurityId() {
        StringBuffer sb = new StringBuffer();
        sb.append(getRandomString(8))
                .append("-")
                .append(getRandomString(4))
                .append("-")
                .append(getRandomString(4))
                .append("-")
                .append(getRandomString(4))
                .append("-")
                .append(getRandomString(12));
        return sb.toString();
    }

    public String getXTraceId() {
        StringBuffer sb = new StringBuffer();
        sb.append(getRandomString(8))
                .append("-")
                .append(getRandomString(4))
                .append("-")
                .append(getRandomString(4))
                .append("-")
                .append(getRandomString(4))
                .append("-")
                .append(getRandomString(12));
        return sb.toString();
    }


    public String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    public String getTokenString(int length) {
        String str = "ABCDEF123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(15);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
