package cc.turbosnail.compiler.bean;

import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

/**
 * @ProjectName: LrhNetHttp
 * @Package: cc.turbosnail.compiler.bean
 * @ClassName: MethodInfo
 * @Description: method info
 * @Author: lrh
 * @CreateDate: 2021/4/6 10:29
 * @UpdateDate: 2021/4/6 10:29
 * @Version: 1.0
 */
public class MethodInfo {
    private ExecutableElement executableElement;
    private String methodName;
    private String returnType;
    private List<? extends VariableElement> parameters;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public List<? extends VariableElement> getParameters() {
        return parameters;
    }

    public void setParameters(List<? extends VariableElement> parameters) {
        this.parameters = parameters;
    }

    public ExecutableElement getExecutableElement() {
        return executableElement;
    }

    public void setExecutableElement(ExecutableElement executableElement) {
        this.executableElement = executableElement;
    }
}
