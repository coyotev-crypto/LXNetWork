package cc.turbosnail.lrhlibrary.error;

import com.google.gson.JsonParseException;
import org.json.JSONException;
import java.text.ParseException;
import okhttp3.Connection;
import retrofit2.HttpException;

/**
 * @version V0.1.0
 * @ClassName: ExceptionHandle
 * @Description: 网络错误code统一处理类
 * @Author: 李儒浩
 * @editor 修改人
 * @Data 2021-1-25 21:48
 */
public class ExceptionHandle {
    /**
     * Http code
     */
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    //网络请求错误处理
    public static ResponseThrowable handleException(Throwable e) {
        ResponseThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponseThrowable(e, ERROR.HTTP_ERROR);
            switch (httpException.code()){
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.message = "网络错误";
                    break;
            }
            return ex;
        }else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ResponseThrowable(resultException,resultException.code);
            ex.message = resultException.message;
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponseThrowable(e, ERROR.PARSE_ERROR);
            ex.message = "解析错误";
            return ex;
        } else if (e instanceof Connection) {
            ex = new ResponseThrowable(e, ERROR.NETWORD_ERROR);
            ex.code = ERROR.NETWORD_ERROR;
            ex.message = "网络连接失败";
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponseThrowable(e, ERROR.SSL_ERROR);
            ex.message = "证书验证失败";
            return ex;
        } else {
            ex = new ResponseThrowable(e, ERROR.TIMEOUT_ERROR);
            ex.message="网络连接超时";
            return ex;
        }
    }

    /**
     * 约定异常
     */
    public class ERROR {
        //未知错误
        public static final int UNKNOWN = 1000;
        //解析错误
        public static final int PARSE_ERROR = 1001;
        //网络错误
        public static final int NETWORD_ERROR = 1002;
        //协议出错
        public static final int HTTP_ERROR = 1003;
        //证书出错
        public static final int SSL_ERROR = 1005;
        //连接超时
        public static final int TIMEOUT_ERROR = 1006;
    }

    public static class ResponseThrowable extends Exception {
        public int code;
        public String message;

        public ResponseThrowable(Throwable e, int httpError) {
            super(e);
            this.code = httpError;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    public static class ServerException extends RuntimeException {
        public int code;
        public String message;
    }
}
