package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class OuttakeSubsystem {
    public DcMotor outDr = null;
    public DcMotor outSt = null;
    //public Servo servoDisc = null;
    public Servo servoOut = null;

    // Servo Constants



    public void init(HardwareMap hardwareMap) {
        outDr = hardwareMap.get(DcMotor.class, "outDr");
        outSt = hardwareMap.get(DcMotor.class, "outSt");
        servoOut = hardwareMap.get(Servo.class, "servoOut");

        outSt.setDirection(DcMotorSimple.Direction.FORWARD);
        outDr.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void setOutakeMotorsPower(double power) {
        outDr.setPower(power);
        outSt.setPower(power);
    }

    public void setServoOutPosition(double position) {
        servoOut.setPosition(position);
    }

    
    public double getServoOutPosition() {
        return servoOut.getPosition();
    }
}
