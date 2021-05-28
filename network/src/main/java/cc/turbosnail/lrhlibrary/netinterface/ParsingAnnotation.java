package cc.turbosnail.lrhlibrary.netinterface;

/**
 * @ProjectName LXNetHttp
 * @Package cc.turbosnail.lrhlibrary.netinterface
 * @Describe 描述
 * @Author wolf king
 * @editor 修改人
 * @CreateDate 2021/5/28
 * @UpdateDate 2021/5/28 更新时间
 */
public interface ParsingAnnotation {
    boolean parsingAnnotation(Class clazz);   //解析当前类注解

    void parsingInterfacesAnnotation(Class clazz);  //解析父类注解

    void parsing(Class clazz);  //统一解析入口
}
