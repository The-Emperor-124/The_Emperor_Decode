package org.firstinspires.ftc.teamcode.subsystems;



import static java.lang.Thread.sleep;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class DriveSubsystem {
    public DcMotor leftRear = null;
    public DcMotor leftFront = null;
    public DcMotor rightRear = null;
    public DcMotor rightFront = null;
    public IMU imu = null;

    private double strafeCorrection = 0.85;


    public void init(HardwareMap hardwareMap) {
        leftRear = hardwareMap.get(DcMotor.class, "leftRear");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightRear = hardwareMap.get(DcMotor.class, "rightRear");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        leftRear.setDirection(DcMotor.Direction.FORWARD);
        leftFront.setDirection(DcMotor.Direction.FORWARD );
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightRear.setDirection(DcMotor.Direction.FORWARD);

        // Initialize IMU
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.LEFT));
        imu.initialize(parameters);
    }

    public void driveRobot(double y, double x, double rx, double power) {
        leftRear.setPower((y + x / strafeCorrection - rx) * power);
        rightRear.setPower((-y + x / strafeCorrection - rx) * power);
        leftFront.setPower((y - x / strafeCorrection - rx) * power);
        rightFront.setPower((y + x / strafeCorrection + rx) * power);
    }

    public void driveRobotFieldCentric(double y, double x, double rx, double power) {
         double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

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

   public void driveMitza(double y, double x, double rx, double power) {
        double theta = Math.atan2(y, x);
        double r = Math.hypot(x, y);

        theta = AngleUnit.normalizeRadians(theta - imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

        double newForward = r * Math.sin(theta);
        double newStrafe = r * Math.cos(theta);

        driveRobot(newForward, newStrafe, rx, power);
    }

    public void moveForward(double power, int time) throws InterruptedException {
        leftRear.setPower(power);
        leftFront.setPower(power);
        rightRear.setPower(power);
        rightFront.setPower(power);

        sleep(time);
    }

    public void moveBackward(double power, int time) throws InterruptedException {
        leftRear.setPower(-power);
        leftFront.setPower(-power);
        rightRear.setPower(-power);
        rightFront.setPower(-power);

       sleep(time);
    }

    public void moveLeft(double power, int time) throws InterruptedException {
        /// astea nu sunt gata
        leftRear.setPower(power);
        leftFront.setPower(power);

        sleep(time);
    }

    public void moveRight(double power, int time) throws InterruptedException {
       /// nici astea nu sunt gata
        rightRear.setPower(power);
        rightFront.setPower(power);

        sleep(time);
    }


    public void resetYaw() {
        imu.resetYaw();
    }

    public double getHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }
}
