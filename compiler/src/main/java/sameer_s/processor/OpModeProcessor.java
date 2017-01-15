package sameer_s.processor;

import com.google.auto.service.AutoService;


import org.jboss.forge.roaster.ParserException;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.Parameter;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import static java.util.Collections.singleton;
import static javax.lang.model.SourceVersion.latestSupported;

/**
 * Created by ssuri on 1/13/17.
 */

@AutoService(Processor.class)
public class OpModeProcessor extends AbstractProcessor
{
    @Override
    public Set getSupportedAnnotationTypes()
    {
        return singleton(ProcessedOpMode.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion()
    {
        return latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
    {
        String sourceCode = null;
        String className = null;
        for (Element element : roundEnv.getElementsAnnotatedWith(ProcessedOpMode.class))
        {
            if (element.getKind() == ElementKind.CLASS)
            {
                String classFolder = ((TypeElement) element).getQualifiedName().toString();
                File f = new File("TeamCode/src/main/java/" + classFolder.replace(".", "/") + ".java");
                String s = "";
                try (Scanner sc = new Scanner(f))
                {
                    while (sc.hasNextLine())
                    {
                        s += sc.nextLine() + "\n";
                    }
                    sc.close();
                } catch (FileNotFoundException e)
                {
                }

                className = element.getSimpleName() + "Processed";


                JavaClassSource javaClass = null;

                try
                {
                    javaClass = Roaster.parse(JavaClassSource.class, s);
                } catch (ParserException e)
                {

                } finally
                {
                    if (javaClass != null)
                    {
                        javaClass.setName(className);
                        javaClass.addMethod()
                                .setPublic()
                                .setStatic(true)
                                .setName("main")
                                .setReturnTypeVoid()
                                .setBody("System.out.println(\"Hello World\");")
                                .addParameter("java.lang.String[]", "args");
                        javaClass.removeAnnotation(javaClass.getAnnotation(ProcessedOpMode.class));

                        sourceCode = javaClass.toString();
                        System.out.println("Set src code");
                    } else
                    {
                        sourceCode = "// An error occurred";
                    }
                }


                break;
            }
        }


        if (className != null)
        {
            try
            { // write the file
                JavaFileObject source = processingEnv.getFiler().createSourceFile("sameer_s.generated." + className);

                Writer writer = source.openWriter();
                writer.write(sourceCode);
                writer.flush();
                writer.close();
            } catch (IOException e)
            {
                // Note: calling e.printStackTrace() will print IO errors
                // that occur from the file already existing after its first run, this is normal
            }
        }

        return true;
    }

}