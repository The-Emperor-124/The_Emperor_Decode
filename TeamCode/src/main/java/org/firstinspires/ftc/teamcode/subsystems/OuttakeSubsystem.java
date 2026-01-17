package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class OuttakeSubsystem {
    public DcMotor motorOutake = null;

    public void init(HardwareMap hardwareMap) {
       motorOutake = hardwareMap.get(DcMotor.class, "motorOutake");

       motorOutake.setDirection(DcMotorSimple.Direction.REVERSE );

    }

    public void setOutakeMotorsPower(double power) {
        motorOutake.setPower(power);
    }


}
