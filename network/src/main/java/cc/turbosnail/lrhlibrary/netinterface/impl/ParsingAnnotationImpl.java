package cc.turbosnail.lrhlibrary.netinterface.impl;

import cc.turbosnail.lrhlibrary.annotation.BaseUrl;
import cc.turbosnail.lrhlibrary.annotation.TestUrl;
import cc.turbosnail.lrhlibrary.netinterface.ParsingAnnotationInterface;

public class ParsingAnnotationImpl extends ParsingAnnotationInterface {
    public ParsingAnnotationImpl() {
    }


    @Override
    public boolean parsingAnnotation(Class clazz) {
        BaseUrl baseAnnotation = (BaseUrl) clazz.getAnnotation(BaseUrl.class);  //外网
        TestUrl testAnnotation = (TestUrl) clazz.getAnnotation(TestUrl.class); //内网
        if (baseAnnotation != null) {
            url = baseAnnotation.value();
            return true;
        } else if (testAnnotation != null) {
            url = testAnnotation.value();
            return true;
        }
        return false;
    }

    @Override
    public void parsingInterfacesAnnotation(Class clazz) {
        Class[] interfaces = clazz.getInterfaces();
        for (Class anInterface : interfaces) {
            if (parsingAnnotation(anInterface))
                return;
        }
    }

    public void parsing(Class clazz) {
        if (null == url) {
            if (parsingAnnotation(clazz)) {
                //如果不存在那就查找父类是否存在该注解
                parsingInterfacesAnnotation(clazz);
            }
        }
        //初始化请求对象
        if (url == null) {
            throw new NullPointerException("BaseUrl is null, the solution is to add @BaseUrl or @TestUrl to the class");
        }
    }
}
