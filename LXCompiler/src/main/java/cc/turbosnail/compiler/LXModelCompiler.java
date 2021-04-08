package cc.turbosnail.compiler;

import com.google.auto.service.AutoService;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;
import cc.turbosnail.compiler.bean.AnnotationInfo;
import cc.turbosnail.compiler.bean.MethodInfo;
import cc.turbosnail.compiler.utils.ParsingInfoUtils;
import cc.turbosnail.lrhannotation.Ignore;
import cc.turbosnail.lrhannotation.LXModel;

/**
 * @ProjectName: LrhNetHttp
 * @Package: cc.turbosnail.lxbindmodel
 * @ClassName: LXBindModel
 * @Description: create file
 * @Author: lrh
 * @CreateDate: 2021/4/6 10:29
 * @UpdateDate: 2021/4/6 10:29
 * @Version: 1.0
 */
@AutoService(Processor.class)
public class LXModelCompiler extends AbstractProcessor {

    private Filer mFiler;
    private List<MethodInfo> mMethodInfoList;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(LXModel.class.getCanonicalName());
        types.add(Ignore.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(LXModel.class);
        for (Element element : elementsAnnotatedWith) {
            TypeElement typeElement = (TypeElement) element;
            //Get current annotation
            LXModel annotation = typeElement.getAnnotation(LXModel.class);

            String value = annotation.value();
            if (value.isEmpty()) {
                throw new NullPointerException("Please add the name of the current Model to LXModel --------------");
            }
            String packageName = processingEnv.getElementUtils().getPackageOf(typeElement).toString(); //包名
            String className = annotation.value();
            mMethodInfoList = ParsingInfoUtils.parsingMethodInfo(typeElement);
            createFile(packageName, className, new AnnotationInfo(typeElement));
        }
        return false;
    }


    /**
     * File generation
     *
     * @param packageName
     * @param className
     * @param annotationUtils
     */
    private void createFile(String packageName, String className, AnnotationInfo annotationUtils) {

        /**
         * public class TestModel implements Model.TestModel{
         *
         *     @Override
         *     public void driverStart(int i, BaseObserver baseObserver) {
         *         LrhHttp.getService(baidu.class)
         *                 .driverStart()
         *                 .compose(LrhHttp.getInstance().applySchedulers(baseObserver));
         *     }
         *     @Override
         *     public Observable<ResponseBody> saveCaseItem(RequestBody requestBody) {
         *         return AppNetWorkApi.getService(PostApi.class)
         *                 .saveCaseItem(requestBody);
         * //        return ApiRetrofit.getInstance().postServer().saveCaseItem(requestBody);
         *     }
         * }
         */
        Writer writer = null;
        try {
            JavaFileObject fileObject = mFiler.createSourceFile(packageName + "." + className);
            writer = fileObject.openWriter();
            writer.write("package " + packageName + ";\n");
            writer.write("import cc.turbosnail.lrhlibrary.BaseObserver;\n");

            if (!annotationUtils.getNetworkEnginePackage().equals("cc.turbosnail.lrhannotation.NetworkEngine")) {
                writer.write("import " + annotationUtils.getNetworkEnginePackage() + ";\n");
            } else {
                writer.write("import cc.turbosnail.lrhlibrary.LrhHttp;\n");
            }
            if (annotationUtils.getNetworkServicePackage() != null) {
                writer.write("import " + annotationUtils.getNetworkServicePackage() + ";\n");
            } else {
                throw new NullPointerException("Please add network request interface service ");
            }

            writer.write("public class " + className + " implements " + annotationUtils.getTypeElement().getQualifiedName() + "{\n");
            for (MethodInfo methodInfo : mMethodInfoList) {
                writerMethod(writer, methodInfo, annotationUtils);
            }

            writer.write("}");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                writer = null;
            }
        }
    }

    private void writerMethod(Writer writer, MethodInfo methodInfo, AnnotationInfo annotationUtils) throws IOException {
        ExecutableElement executableElement = methodInfo.getExecutableElement();
        Ignore annotation = executableElement.getAnnotation(Ignore.class);

        writer.write("\t@Override\n");
        writer.write("\tpublic " + methodInfo.getReturnType() + " " + methodInfo.getMethodName() + "(");
        List<? extends VariableElement> variableElements = methodInfo.getParameters();

        for (int i = 0; i < variableElements.size(); i++) {
            VariableElement variableElement = variableElements.get(i);
            TypeMirror typeMirror = variableElement.asType();
            String parameterName = variableElement.getSimpleName().toString();
            writer.write(typeMirror + " " + parameterName);
            if (i < variableElements.size() - 1) {
                writer.write(",");
            }
        }
        writer.write("){\n");
        //Ignored method add empty implementation
        if (annotation != null) {
            if (!methodInfo.getReturnType().equals("void")) {
                writer.write("\t\treturn " + returnType(methodInfo.getReturnType()) + ";\n");
            }
        } else {
            //return AppNetWorkApi.getService(PostApi.class)
            //         *                 .saveCaseItem(requestBody);
            int length = variableElements.size();
            if (!methodInfo.getReturnType().equals("void")) {
                writer.write("return");
            }else {
                length = length - 1;
            }
            if (!annotationUtils.getNetworkEnginePackage().equals("cc.turbosnail.lrhannotation.NetworkEngine")) {
                writer.write("\t\t" + annotationUtils.getNetworkEnginePackage());
            } else {
                writer.write("\t\tLrhHttp");
            }
            writer.write(".getService(" + annotationUtils.getNetworkServiceSimpleName() + ".class)\n");
            writer.write("\t\t\t." + methodInfo.getMethodName() + "(");
            for (int i = 0; i < length; i++) {
                VariableElement variableElement = variableElements.get(i);
                String parameterName = variableElement.getSimpleName().toString();
                writer.write(parameterName);
                if (i != length-1){
                    writer.write(",");
                }
            }
            writer.write(")");
            if (!methodInfo.getReturnType().equals("void")) {
                writer.write(";\n");
            } else {
                writer.write("\n\t\t\t.compose(" + annotationUtils.getNetworkEngineSimpleName() + ".getInstance().applySchedulers(" + variableElements.get(variableElements.size() - 1) + "));\n");
            }
        }
        writer.write("\t}\n");
    }

    private String returnType(String returnType) {
        String returnValue = "null";
        switch (returnType) {
            case "int":
            case "float":
            case "double":
                returnValue = "0";
                break;
        }
        return returnValue;
    }
}
