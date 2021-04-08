package cc.turbosnail.compiler.utils;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

import cc.turbosnail.compiler.bean.LXModelImplInfo;
import cc.turbosnail.compiler.bean.LXModelInfo;
import cc.turbosnail.compiler.bean.MethodInfo;
import cc.turbosnail.lrhannotation.LXModel;
import cc.turbosnail.lrhannotation.LXModelImpl;

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


    public static LXModelImplInfo parsingLXModelImplInfo(TypeElement element){
        LXModelImplInfo lxModelImplInfo = new LXModelImplInfo();
        LXModelImpl annotation = element.getAnnotation(LXModelImpl.class);
        if (annotation == null){
            return null;
        }
        try{
            Class aClass = annotation.value();
            lxModelImplInfo.setName(aClass.getCanonicalName());
            lxModelImplInfo.setSimpleName(aClass.getSimpleName());
            lxModelImplInfo.setTypeElement(element);
        }catch (MirroredTypeException e){
            DeclaredType declaredType = (DeclaredType) e.getTypeMirror();
            TypeElement typeElement = (TypeElement) declaredType.asElement();
            lxModelImplInfo.setName(declaredType.toString());
            lxModelImplInfo.setSimpleName(typeElement.getSimpleName().toString());
            lxModelImplInfo.setTypeElement(typeElement);
        }
        return lxModelImplInfo;
    }

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

    public static LXModelInfo parsingLXModelInfo(TypeElement typeElement) {
        LXModel lxModel = typeElement.getAnnotation(LXModel.class);
        LXModelInfo lxModelInfo = new LXModelInfo();
        lxModelInfo.setTypeElement(typeElement);
        lxModelInfo.setLxModel(lxModel);
        lxModelInfo.setModelImplInfo(parsingLXModelImplInfo(typeElement));
        try {
            Class aClass = lxModel.networkEngine();
            lxModelInfo.setNetworkEnginePackage(aClass.getCanonicalName());
            lxModelInfo.setNetworkEngineSimpleName(aClass.getSimpleName());
        } catch (MirroredTypeException mte) {

            DeclaredType declaredType = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) declaredType.asElement();
            lxModelInfo.setNetworkEnginePackage(declaredType.toString());
            lxModelInfo.setNetworkEngineSimpleName(classTypeElement.getSimpleName().toString());
        }

        try {
            Class aClass = lxModel.networkService();
            lxModelInfo.setNetworkServicePackage(aClass.getCanonicalName());
            lxModelInfo.setNetworkServiceSimpleName(aClass.getSimpleName());
        } catch (MirroredTypeException mte) {
            DeclaredType declaredType = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) declaredType.asElement();
            lxModelInfo.setNetworkServicePackage(declaredType.toString());
            lxModelInfo.setNetworkServiceSimpleName(classTypeElement.getSimpleName().toString());
        }
        return lxModelInfo;
    }

}
