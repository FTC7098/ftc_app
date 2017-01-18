package sameer_s.processor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import sameer_s.processor.OpModeType;

import static java.lang.annotation.ElementType.TYPE;

@Target(value = TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ProcessedOpMode
{
    String name() default "";
    String group() default "";
    OpModeType type();
}
