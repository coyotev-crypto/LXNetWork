package cc.turbosnail.lrhlibrary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * base/BaseUrl
 * 父Url
 *
 * @author 李儒浩
 * @version V0.1.0
 * @editor 修改人
 * @team TurboSnail
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface BaseUrl {
    String value() default "";
}
