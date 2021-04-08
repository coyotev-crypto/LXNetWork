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

public class LXModelInfo {
    private TypeElement typeElement;
    private LXModel lxModel;
    private String mNetworkEnginePackage;
    private String mNetworkServicePackage;
    private String mNetworkEngineSimpleName;
    private String mNetworkServiceSimpleName;
    private LXModelImplInfo mModelImplInfo;

    public LXModelInfo() {

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

    public LXModelImplInfo getModelImplInfo() {
        return mModelImplInfo;
    }

    public void setModelImplInfo(LXModelImplInfo mModelImplInfo) {
        this.mModelImplInfo = mModelImplInfo;
    }
}
