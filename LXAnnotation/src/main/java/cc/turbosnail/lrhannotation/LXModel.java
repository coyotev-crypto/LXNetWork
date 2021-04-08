package cc.turbosnail.lrhannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LXModel {
    String value() default "";  //model 名称
    Class networkEngine() default NetworkEngine.class;  //网络引擎默认使用 LrhHttp
    Class networkService();   //网络接口
}
