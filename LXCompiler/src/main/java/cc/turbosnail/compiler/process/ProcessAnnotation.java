package cc.turbosnail.compiler.process;


import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

public interface ProcessAnnotation {
    void process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment);
}
