package sameer_s.processor;

public class OpModeMethods
{
    public static OpModeStage forTime(final long millis, final OpModeStage.LinearOpModeStage action)
    {
        return new OpModeStage()
        {
            long startTime = -1;

            @Override
            public boolean doAction() throws Throwable
            {
                if (startTime == -1)
                {
                    startTime = System.currentTimeMillis();
                }

                long elapsed = System.currentTimeMillis() - startTime;
                if (elapsed < millis)
                {
                    action.doAction();
                    return false;
                }

                return true;
            }
        };
    }

    public static OpModeStage sleep(long millis)
    {
        return forTime(millis, new OpModeStage.LinearOpModeStage()
        {
            @Override
            public void doAction() throws Throwable
            {

            }
        });
    }

    public static OpModeStage linear(final OpModeStage.LinearOpModeStage action)
    {
        final Thread t = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    action.doAction();
                } catch (Throwable t)
                {
                    throw new RuntimeException(t);
                }
            }
        };
        t.start();
        return new OpModeStage()
        {
            @Override
            public boolean doAction() throws Throwable
            {
                return !t.isAlive();
            }
        };
    }

    public static boolean execute(OpModeStage action)
    {
        try
        {
            return action.doAction();
        } catch (Throwable t)
        {
            throw new RuntimeException(t);
        }
    }
}
