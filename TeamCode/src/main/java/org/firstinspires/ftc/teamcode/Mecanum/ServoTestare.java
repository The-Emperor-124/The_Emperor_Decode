package org.firstinspires.ftc.teamcode.Mecanum;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ServoTestare extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Servo servo = hardwareMap.get(Servo.class, "servoOut");

        waitForStart();
        while (opModeIsActive()) {
            servo.setPosition(ServoTest.servoPos);
        }
    }
}
