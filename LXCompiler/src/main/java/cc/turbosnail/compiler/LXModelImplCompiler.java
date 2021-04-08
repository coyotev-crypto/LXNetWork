package cc.turbosnail.compiler;

import com.google.auto.service.AutoService;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/**
 * @ProjectName: LXNetHttp
 * @Package: cc.turbosnail.compiler
 * @ClassName: LXModelImplCompiler
 * @Description: Custom implementation
 * @Author: lrh
 * @CreateDate: 2021/4/8 10:02
 * @Version: 1.0
 */
@AutoService(Processor.class)
public class LXModelImplCompiler extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        return types;
    }
}
