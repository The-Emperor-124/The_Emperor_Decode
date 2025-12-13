package org.firstinspires.ftc.teamcode.Mecanum;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class RobotHardware {
    private LinearOpMode myOpMode = null;

    public DcMotor leftRear = null;
    public DcMotor leftFront = null;
    public DcMotor rightRear = null;
    public DcMotor rightFront = null;
    public IMU imu = null;

    public RobotHardware(LinearOpMode opmode) {
        myOpMode = opmode;
    }

    public void init() {
        leftRear = myOpMode.hardwareMap.get(DcMotor.class, "leftRear");
        leftFront = myOpMode.hardwareMap.get(DcMotor.class, "leftFront");
        rightRear = myOpMode.hardwareMap.get(DcMotor.class, "rightRear");
        rightFront = myOpMode.hardwareMap.get(DcMotor.class, "rightFront");

        // Initialize IMU
        imu = myOpMode.hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

        leftRear.setDirection(DcMotor.Direction.FORWARD);
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightRear.setDirection(DcMotor.Direction.FORWARD);
    }

    public void driveRobot(double y, double x, double rx, double power) {
        leftRear.setPower((y + x / 0.85 - rx) * power);
        rightRear.setPower((-y + x / 0.85 - rx) * power);
        leftFront.setPower((y - x / 0.85 - rx) * power);
        rightFront.setPower((y + x / 0.85 + rx) * power);
    }

    public void driveFieldCentric(double y, double x, double rx, double power, double botHeading) {
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);

        leftFront.setPower(((rotY + rotX * 1.1 + rx) / denominator) * power);
        leftRear.setPower(((rotY - rotX * 1.1 + rx) / denominator) * power);
        rightFront.setPower(((rotY - rotX * 1.1 - rx) / denominator) * power);
        rightRear.setPower(((rotY + rotX * 1.1 - rx) / denominator) * power);
    }

    public void resetYaw() {
        imu.resetYaw();
    }

    public double getHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }
}
