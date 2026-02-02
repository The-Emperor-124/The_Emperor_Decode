package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

public class IntakeSubsystem {
    public DcMotor motorIntake = null;

    public void init(HardwareMap hardwareMap) {
        motorIntake = hardwareMap.get(DcMotor.class, "motorIntake");
        motorIntake.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    // TELE OP
    public void setPower(double power) {
        motorIntake.setPower(power);
    }

    // AUTO
    // Functie template nu se modifica, se apeleaza in functiile specifice
    public Action spinIntakeAction(double power) {
        return packet -> {
            motorIntake.setPower(power);
            return false;
        };
    }

    // aici va faceti voi functiile specifice cu valorile care va trb

    // ex aici, dar va faceti voi valorile
    public Action collectElementsAction(){
        return spinIntakeAction(0.5);
    }
}
