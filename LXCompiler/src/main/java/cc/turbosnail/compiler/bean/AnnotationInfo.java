package cc.turbosnail.compiler.bean;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import cc.turbosnail.lrhannotation.LXModel;

/**
 * @ProjectName: LrhNetHttp
 * @Package: cc.turbosnail.lxbindmodel
 * @ClassName: class info
 * @Description: class info
 * @Author: lrh
 * @CreateDate: 2021/4/6 10:29
 * @UpdateDate: 2021/4/6 10:29
 * @Version: 1.0
 */

public class AnnotationInfo {
    private TypeElement typeElement;
    private LXModel lxModel;
    private String mNetworkEnginePackage;
    private String mNetworkServicePackage;
    private String mNetworkEngineSimpleName;
    private String mNetworkServiceSimpleName;


    public AnnotationInfo(TypeElement typeElement) {
        this.typeElement = typeElement;
        this.lxModel = typeElement.getAnnotation(LXModel.class);
        parsingNetworkEngine();
        parsingNetworkService();
    }

    private void parsingNetworkEngine() {
        try {

            Class aClass = lxModel.networkEngine();
            mNetworkEngineSimpleName = aClass.getSimpleName();
            mNetworkEnginePackage = aClass.getCanonicalName();
        } catch (MirroredTypeException mte) {

            DeclaredType declaredType = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) declaredType.asElement();
            mNetworkEnginePackage = declaredType.toString();
            mNetworkEngineSimpleName = classTypeElement.getSimpleName().toString();
        }

    }

    private void parsingNetworkService() {
        try {

            Class aClass = lxModel.networkService();
            mNetworkServiceSimpleName = aClass.getSimpleName();
            mNetworkServicePackage = aClass.getCanonicalName();
        } catch (MirroredTypeException mte) {

            DeclaredType declaredType = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) declaredType.asElement();
            mNetworkServicePackage = declaredType.toString();
            mNetworkServiceSimpleName = classTypeElement.getSimpleName().toString();
        }
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }

    public void setTypeElement(TypeElement typeElement) {
        this.typeElement = typeElement;
    }

    public LXModel getLxModel() {
        return lxModel;
    }

    public void setLxModel(LXModel lxModel) {
        this.lxModel = lxModel;
    }

    public String getNetworkEnginePackage() {
        return mNetworkEnginePackage;
    }

    public void setNetworkEnginePackage(String mNetworkEnginePackage) {
        this.mNetworkEnginePackage = mNetworkEnginePackage;
    }

    public String getNetworkServicePackage() {
        return mNetworkServicePackage;
    }

    public void setNetworkServicePackage(String mNetworkServicePackage) {
        this.mNetworkServicePackage = mNetworkServicePackage;
    }

    public String getNetworkEngineSimpleName() {
        return mNetworkEngineSimpleName;
    }

    public void setNetworkEngineSimpleName(String mNetworkEngineSimpleName) {
        this.mNetworkEngineSimpleName = mNetworkEngineSimpleName;
    }

    public String getNetworkServiceSimpleName() {
        return mNetworkServiceSimpleName;
    }

    public void setNetworkServiceSimpleName(String mNetworkServiceSimpleName) {
        this.mNetworkServiceSimpleName = mNetworkServiceSimpleName;
    }
}
