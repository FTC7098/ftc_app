package sameer_s.processor;

@FunctionalInterface
public interface OpModeStage
{
    boolean doAction() throws Throwable;

    static OpModeStage forTime(long millis, LinearOpModeStage action)
    {
        return new OpModeStage()
        {
            long startTime = -1;
            @Override
            public boolean doAction() throws Throwable
            {
                if(startTime == -1)
                {
                    startTime = System.currentTimeMillis();
                }

                long elapsed = System.currentTimeMillis() - startTime;
                if(elapsed < millis)
                {
                    action.doAction();
                    return false;
                }

                return true;
            }
        };
    }

    static OpModeStage sleep(long millis)
    {
        return forTime(millis, () -> {});
    }

    static OpModeStage linear(LinearOpModeStage action)
    {
        Thread t = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    action.doAction();
                }
                catch (Throwable t)
                {
                    throw new RuntimeException(t);
                }
            }
        };
        t.start();
        return () -> !t.isAlive();
    }

    static boolean execute(OpModeStage action)
    {
        try
        {
            return action.doAction();
        }
        catch (Throwable t)
        {
            throw new RuntimeException(t);
        }
    }

    interface LinearOpModeStage
    {
        void doAction() throws Throwable;
    }
}
