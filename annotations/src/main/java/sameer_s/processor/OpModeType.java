package sameer_s.processor;

/**
 * Created by Sameer on 2017-01-15.
 */
public enum OpModeType
{
    AUTONOMOUS("com.qualcomm.robotcore.eventloop.opmode.Autonomous"), TELEOP("com.qualcomm.robotcore.eventloop.opmode.TeleOp");

    public String path;

    OpModeType(String path)
    {
        this.path = path;
    }
}
