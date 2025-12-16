package org.firstinspires.ftc.teamcode.Mecanum;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Mecanum2026)", group="Linear Opmode")
public class Mecanum2026 extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    RobotHardware robot = new RobotHardware(this);

    @Override
    public void runOpMode() {
        robot.init();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            double power,powerIntake;
            // turbo slow and normal modes
            if (gamepad1.left_trigger > 0.0) {
                power = 0.2;
            } else if (gamepad1.right_trigger > 0.0) {
                power = 1.0;
            } else {
                power = 0.7;
            }
           if(gamepad2.a) {
             powerIntake=1.0;}
           else {powerIntake=0.0;}

            // Using the new modular hardware class to drive
            robot.driveFieldCentric(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, power, robot.getHeading());
            robot.invarteMotorIntake(powerIntake);

                telemetry.addData("Status", "Run Time: " + runtime.toString());
                telemetry.update();
        }

        }
    }
