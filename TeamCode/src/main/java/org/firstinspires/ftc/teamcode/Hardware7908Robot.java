package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by ssuri on 10/19/16.
 *
 */
public class Hardware7908Robot
{
    private DcMotorController leftDrive, rightDrive, shooter, lift;
    private ColorSensor cs1, cs2;
    private TouchSensor shooterSwitch;

    public Hardware7908Robot(HardwareMap map)
    {
        leftDrive = map.dcMotorController.get("left_drive");
        rightDrive = map.dcMotorController.get("right_drive");
        shooter = map.dcMotorController.get("shooter");
        lift = map.dcMotorController.get("lift");
        cs1 = null;
        cs2 = null;
        shooterSwitch = map.touchSensor.get("shooter_switch");
    }

    public void logRobot(Telemetry telemetry)
    {
        telemetry.addData("Test Log", "Test Log");
    }

    public boolean shooterSwitch()
    {
        return shooterSwitch.isPressed();
    }

    public void stop()
    {
        for(int i = 0; i < 8; i++)
        {
            moveMotor(i, 0);
        }
    }


    public void moveMotor(int index, double pow)
    {
        DcMotorController controller = index < 2 ? leftDrive : (index < 4 ? rightDrive : (index < 6 ? shooter : lift));
        controller.setMotorPower(1 + (index % 2), pow);
    }

    public void drive(double pow)
    {
        drive(pow, pow);
    }

    public void drive(double left, double right)
    {
        leftDrive.setMotorPower(1, left);
        leftDrive.setMotorPower(2, left);
        rightDrive.setMotorPower(1, right);
        rightDrive.setMotorPower(2, right);
    }

    public void swingTurn(double pow, boolean left)
    {
        leftDrive.setMotorPower(1, pow * (left ? 1 : -1));
        leftDrive.setMotorPower(2, pow * (left ? 1 : -1));
        rightDrive.setMotorPower(1, pow * (left ? -1 : 1));
        rightDrive.setMotorPower(2, pow * (left ? -1 : 1));
    }

    public void pointTurn(double pow, boolean left)
    {
        leftDrive.setMotorPower(1, pow * (left ? 1 : 0));
        leftDrive.setMotorPower(2, pow * (left ? 1 : 0));
        rightDrive.setMotorPower(1, pow * (left ? 0 : 1));
        rightDrive.setMotorPower(2, pow * (left ? 0 : 1));
    }

    public void lineFollow(LineFollowData data)
    {
    }

    public static class LineFollowData
    {
        public int colorSensorNum;
        private int integral, differential;
        private float kP, kI, kD;

        public LineFollowData(int colorSensorNum, float kP, float kI, float kD)
        {
            this.colorSensorNum = colorSensorNum;
        }
    }
}
