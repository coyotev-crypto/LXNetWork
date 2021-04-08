package cc.turbosnail.lrhlibrary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * base/TestUrl
 * 测试路径
 *
 * @author 李儒浩
 * @version V0.1.0
 * @editor 修改人
 * @team TurboSnail
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface TestUrl {
    String value() default "";
}
