package sameer_s.processor;

import com.google.auto.service.AutoService;

import org.jboss.forge.roaster.ParserException;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
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

/**
 * Created by ssuri on 1/13/17.
 */

@AutoService(Processor.class)
public class OpModeProcessor extends AbstractProcessor
{
    @Override
    public Set<String> getSupportedAnnotationTypes()
    {
        return Collections.singleton(ProcessedOpMode.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion()
    {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv)
    {
        String sourceCode = null;
        String className = null;
        for (Element element : roundEnv
                .getElementsAnnotatedWith(ProcessedOpMode.class))
        {
            ProcessedOpMode annotation = element
                    .getAnnotation(ProcessedOpMode.class);

            if (element.getKind() == ElementKind.CLASS)
            {
                String classFolder = ((TypeElement) element).getQualifiedName()
                        .toString();
                File f = new File("TeamCode/src/main/java/"
                        + classFolder.replace(".", "/") + ".java");
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
                    return true;
                }

                className = element.getSimpleName() + "Processed";

                JavaClassSource javaClass = null;

                try
                {
                    javaClass = Roaster.parse(JavaClassSource.class, s);
                } catch (ParserException e)
                {
                    // do nothing, this is a bug
                } finally
                {
                    if (javaClass != null)
                    {
                        javaClass.setName(className);

                        javaClass.addAnnotation(annotation.type().path)
                                .setStringValue("name", annotation.name())
                                .setStringValue("group", annotation.group());

						/* Handle @LogRobot */
                        {
                            MethodSource<JavaClassSource> loop = getOrMake(
                                    javaClass, "loop");

                            for (FieldSource<JavaClassSource> field : javaClass
                                    .getFields())
                            {
                                String body = loop.getBody();
                                if (field.hasAnnotation(LogRobot.class))
                                {
                                    body += field.getName()
                                            + ".logRobot(telemetry);";
                                }

                                loop.setBody(body);
                            }
                        }
                        /* Handle OpModeStage[] */
                        {
                            FieldSource<JavaClassSource> array = null;
                            for (FieldSource<JavaClassSource> field : javaClass.getFields())
                            {
                                if (field.getType().isArray() && field.getType().isType(OpModeStage.class))
                                {
                                    array = field;
                                    break;
                                }
                            }

                            if (array != null)
                            {
                                String fieldName = "a";
                                while (javaClass.hasField(fieldName))
                                {
                                    fieldName += "a";
                                }

                                FieldSource<JavaClassSource> counter = javaClass.addField().setName(fieldName).setType(int.class).setPrivate().setLiteralInitializer("0");
                                MethodSource<JavaClassSource> loop = getOrMake(javaClass, "loop");

                                StringBuilder builder = new StringBuilder(loop.getBody());
                                builder.append("if(");
                                builder.append(counter.getName());
                                builder.append("<");
                                builder.append(array.getName());
                                builder.append(".length && sameer_s.processor.OpModeMethods.execute(");
                                builder.append(array.getName());
                                builder.append("[");
                                builder.append(counter.getName());
                                builder.append("]");
                                builder.append(")){");
                                builder.append(counter.getName());
                                builder.append("++;}");
                                loop.setBody(builder.toString());
                            }
                        }

                        javaClass.removeAnnotation(javaClass
                                .getAnnotation(ProcessedOpMode.class));

                        sourceCode = javaClass.toString();
                        System.out.println("Set src code");
                    } else
                    {
                        sourceCode = "An error occurred";
                    }
                }

                break;
            }
        }

        if (className != null)

        {
            try
            {
                JavaFileObject source = processingEnv.getFiler()
                        .createSourceFile("sameer_s.generated." + className);

                Writer writer = source.openWriter();
                writer.write(sourceCode);
                writer.flush();
                writer.close();
            } catch (IOException e)
            {
                // Do not call e.printStackTrace()
            }
        }

        return true;
    }

    private MethodSource<JavaClassSource> getOrMake(JavaClassSource javaClass,
                                                    String name)
    {
        if (javaClass.hasMethodSignature(name))
        {
            return javaClass.getMethod(name);
        } else
        {
            return javaClass.addMethod().setPublic().setReturnTypeVoid()
                    .setName(name);
        }
    }
}