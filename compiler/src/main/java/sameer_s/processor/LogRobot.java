package sameer_s.processor;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface LogRobot
{
}
