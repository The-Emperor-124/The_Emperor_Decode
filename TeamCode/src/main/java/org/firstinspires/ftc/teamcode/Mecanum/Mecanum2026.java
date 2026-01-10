package org.firstinspires.ftc.teamcode.Mecanum;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.subsystems.OuttakeSubsystem;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
@TeleOp(name="Mecanum2026", group="Linear Opmode")
public class Mecanum2026 extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    RobotHardware robot = new RobotHardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            double power, powerIntake, powerOutake, powerIntake2;


            // turbo slow and normal modes
            if (gamepad1.left_bumper) {
                power = 0.2;
            } else if (gamepad1.right_bumper) {
                power = 1.0;
            } else {
                power = 0.7;
            }
            
            // DRIVE LOGIC
            robot.driveSubsystem.driveRobot(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, power);

            // INTAKE LOGIC
           if (gamepad2.a) {   //intake inauntru
                powerIntake = 1.0;
            } else {
                powerIntake = 0.0;
            }
            robot.intakeSubsystem.setPower(powerIntake);

            if (gamepad2.b) {   //intake afara
                powerIntake2 = -1.0;
            } else {
                powerIntake2 = 0.0;
            }
               robot.intakeSubsystem.setPower(powerIntake2);

            // OUTTAKE LOGIC
            if (gamepad2.y) {
                powerOutake = 1.0;
            } else {
                powerOutake = 0.0;
            }
            robot.outtakeSubsystem.setOutakeMotorsPower(powerOutake);

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }

    }
}