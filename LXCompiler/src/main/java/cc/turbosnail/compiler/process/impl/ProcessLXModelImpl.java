package cc.turbosnail.compiler.process.impl;


import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import cc.turbosnail.compiler.bean.LXModelImplInfo;
import cc.turbosnail.compiler.process.ProcessAnnotation;
import cc.turbosnail.compiler.utils.ParsingInfoUtils;
import cc.turbosnail.lrhannotation.LXModelImpl;

public class ProcessLXModelImpl implements ProcessAnnotation {
    private Filer mFiler;
    private ProcessingEnvironment processingEnv;
    private Map<String,JavaFileObject> mMapFileObject;
    public ProcessLXModelImpl(Filer mFiler, ProcessingEnvironment processingEnv, Map<String, JavaFileObject> mapFileObject) {
        this.mFiler = mFiler;
        this.processingEnv = processingEnv;
        this.mMapFileObject = mapFileObject;
    }

    @Override
    public void process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // mMapFileObject.put(packageName+"."+className,fileObject);
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(LXModelImpl.class);
        for (Element element : elementsAnnotatedWith) {
            LXModelImplInfo info = ParsingInfoUtils.parsingLXModelImplInfo((TypeElement) element);
//            try {
//                JavaFileObject test = mFiler.createSourceFile("test");
//                Writer writer = test.openWriter();
//                writer.write(info.getName());
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }
}
