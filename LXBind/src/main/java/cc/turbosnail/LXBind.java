package cc.turbosnail;

import java.lang.reflect.Method;

import cc.turbosnail.lrhannotation.LXModel;

/**
 * @ProjectName: LrhNetHttp
 * @Package: cc.turbosnail.lxbindmodel
 * @ClassName: LXBindModel
 * @Description: bind model
 * @Author: lrh
 * @CreateDate: 2021/4/6 10:29
 * @UpdateDate: 2021/4/6 10:29
 * @Version: 1.0
 */
public class LXBind {
    public static <T> T bind(Class clazz) {
        LXModel annotation = (LXModel) clazz.getAnnotation(LXModel.class);
        String packageName = clazz.getPackage().getName();
        String modelName = packageName + "." + annotation.value();
        try {
            Class<?> aClass = Class.forName(modelName);
            return (T) aClass.newInstance();
        } catch (ClassNotFoundException e) {
            Class enclosingClass = clazz.getEnclosingClass();
            String name = enclosingClass.getSimpleName();
            modelName = packageName + "." + name + clazz.getSimpleName() + "_BindModel";
            try {
                return (T) Class.forName(modelName).newInstance();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }


}
