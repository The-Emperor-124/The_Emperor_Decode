package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.Mecanum.Mecanum2026;

public class IntakeSubsystem {
    public DcMotor motorIntake1 = null;
    public DcMotor motorIntake2 = null;
    public DcMotor motorIntake3 = null;

    public void init(HardwareMap hardwareMap) {
        motorIntake1 = hardwareMap.get(DcMotor.class, "motorIntake1");
        motorIntake2 = hardwareMap.get(DcMotor.class, "motorIntake2");
        motorIntake3 = hardwareMap.get(DcMotor.class, "motorIntake3");

        motorIntake1.setDirection(DcMotorSimple.Direction.REVERSE);
        motorIntake2.setDirection(DcMotorSimple.Direction.REVERSE);
        motorIntake3.setDirection(DcMotorSimple.Direction.REVERSE );
    }

    public void setPower1(double power1) {
        motorIntake1.setPower(power1);
    }
    public void setPower2(double power2) {
        motorIntake2.setPower(power2);
    }
    public void setPower3(double power3) {
        motorIntake3.setPower(power3);
    }
}
