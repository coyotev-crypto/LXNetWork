package cc.turbosnail.lrhlibrary.netinterface;

/**
 * 注解解析
 */
public abstract class  ParsingAnnotationInterface {
    public String url;
    public abstract boolean parsingAnnotation(Class clazz);   //解析当前类注解
    public abstract void parsingInterfacesAnnotation(Class clazz);  //解析父类注解
    public abstract void parsing(Class clazz);  //统一解析入口
}
