package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class OuttakeSubsystem {
    public DcMotor motorOutake1 = null;
    public DcMotor motorOutake2 = null;

    public void init(HardwareMap hardwareMap) {
         motorOutake1 = hardwareMap.get(DcMotor.class, "motorOutake1");
        motorOutake2 = hardwareMap.get(DcMotor.class, "motorOutake2");

        motorOutake1.setDirection(DcMotorSimple.Direction.REVERSE );
        motorOutake2.setDirection(DcMotorSimple.Direction.FORWARD );
         }

          public void setOutakeMotorsPower(double power) {
         motorOutake1.setPower(power);
         motorOutake2.setPower(power);
         }

    }

