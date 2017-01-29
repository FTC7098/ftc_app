package sameer_s.processor;

public interface OpModeStage
{
    boolean doAction() throws Throwable;

    interface LinearOpModeStage
    {
        void doAction() throws Throwable;
    }
}
