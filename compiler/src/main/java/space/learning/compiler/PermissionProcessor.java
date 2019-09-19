package space.learning.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import space.learning.anno.CheckPermission;
import space.learning.anno.NeverAskAgain;
import space.learning.anno.PermissionDenied;
import space.learning.anno.ShowRationale;

@AutoService(Processor.class)
@SupportedAnnotationTypes({Constants.CHECK_PERMISSION_ANNO_TYPE, Constants.NEVER_ASK_AGAIN_ANNO_TYPE,
        Constants.PERMISSION_DENIED_ANNO_TYPE, Constants.SHOW_RATIONALE_ANNO_TYPE})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class PermissionProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Messager messager;
    private Filer filer;

    private HashMap<TypeElement, List<Element>> cacheMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        //初始化需要用到的类
        elementUtils = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();

        messager.printMessage(Diagnostic.Kind.NOTE, "start init");
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        if (set.isEmpty()) {
            messager.printMessage(Diagnostic.Kind.NOTE, "没有使用注解");
            return false;
        }

//        解析所有的annotation
        Set<? extends Element> rootElements = roundEnvironment.getElementsAnnotatedWith(CheckPermission.class);
        messager.printMessage(Diagnostic.Kind.NOTE, "rootElements size" + rootElements.size());

        for (Element element : rootElements) {

            String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();

            String className = element.getSimpleName().toString();

            String finalName = className + "$$Permission";

            messager.printMessage(Diagnostic.Kind.NOTE, "start write to file " + finalName);

            //生成文件
            try {
                JavaFileObject sourceFile = filer.createSourceFile(packageName + "." + finalName);

                Writer writer = sourceFile.openWriter();

                writer.write("package " + packageName + ";\n");

                writer.write("public class " + finalName + "{}\n");

                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return true;
    }
}
