package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class OuttakeSubsystem {
    public DcMotorEx motorOutake1 = null;
    public DcMotorEx motorOutake2 = null;
    private static final double P=18;
    private static final double I=0;
    private static final double D=4;
    private static final double F=13.5;

    public void init(HardwareMap hardwareMap) {
         motorOutake1 = hardwareMap.get(DcMotorEx.class, "motorOutake1");
        motorOutake2 = hardwareMap.get(DcMotorEx.class, "motorOutake2");

        motorOutake1.setDirection(DcMotorSimple.Direction.REVERSE );
        motorOutake2.setDirection(DcMotorSimple.Direction.FORWARD );

        motorOutake1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorOutake2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorOutake1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorOutake2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        motorOutake1.setVelocityPIDFCoefficients(P, I, D, F);
        motorOutake2.setVelocityPIDFCoefficients(P, I, D, F);

    }
    // Set RPM directly
    public void setOuttakeRPM(double rpm) {
        double ticksPerSecond =
                (rpm / 60.0) * motorOutake1.getMotorType().getTicksPerRev();

        motorOutake1.setVelocity(ticksPerSecond);
        motorOutake2.setVelocity(ticksPerSecond);
    }

    // Full speed (6000 RPM)
    public void fullSpeed() {
        setOuttakeRPM(6000);
    }

    // Stop motors
    public void stop() {
        motorOutake1.setVelocity(0);
        motorOutake2.setVelocity(0);
    }
}


/*public void setOutakeMotorsPower(double power) {
         motorOutake1.setPower(power);
         motorOutake2.setPower(power);
         }

    }*/

