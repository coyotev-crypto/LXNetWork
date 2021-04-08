package cc.turbosnail.compiler.utils;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import cc.turbosnail.compiler.bean.MethodInfo;

/**
 * @ProjectName: LrhNetHttp
 * @Package: cc.turbosnail.compiler
 * @ClassName: ParsingMethodInfoUtils
 * @Description: Parsing Method Info
 * @Author: lrh
 * @CreateDate: 2021/4/7 17:16
 * @Version: 1.0
 */
public class ParsingInfoUtils {

    public static List<MethodInfo> parsingMethodInfo(TypeElement element) {
        List<? extends Element> enclosedElements = element.getEnclosedElements();
        List<MethodInfo> methodInfos = new ArrayList<>();
        for (Element enclosedElement : enclosedElements) {
            MethodInfo mMethodInfo = new MethodInfo();
            ExecutableElement executableElement = (ExecutableElement) enclosedElement;
            mMethodInfo.setMethodName(executableElement.getSimpleName().toString());
            mMethodInfo.setParameters(executableElement.getParameters());
            mMethodInfo.setReturnType(executableElement.getReturnType().toString());
            mMethodInfo.setExecutableElement(executableElement);
            methodInfos.add(mMethodInfo);
        }
        return methodInfos;
    }

}
