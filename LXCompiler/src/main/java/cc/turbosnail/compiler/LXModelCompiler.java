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
import cc.turbosnail.compiler.bean.LXModelInfo;
import cc.turbosnail.compiler.bean.MethodInfo;
import cc.turbosnail.compiler.process.impl.ProcessLXModel;
import cc.turbosnail.compiler.process.impl.ProcessLXModelImpl;
import cc.turbosnail.compiler.utils.ParsingInfoUtils;
import cc.turbosnail.lrhannotation.Ignore;
import cc.turbosnail.lrhannotation.LXModel;
import cc.turbosnail.lrhannotation.LXModelImpl;

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
    private ProcessLXModel mProcessLXModel;

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
        types.add(LXModelImpl.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mProcessLXModel = new ProcessLXModel(mFiler,processingEnv);
        mProcessLXModel.process(set,roundEnvironment);
        return false;
    }



}
