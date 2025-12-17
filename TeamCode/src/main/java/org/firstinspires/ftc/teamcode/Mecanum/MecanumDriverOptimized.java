package org.firstinspires.ftc.teamcode.Mecanum;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="MecanumDriverOptimized", group="Linear Opmode")
public class MecanumDriverOptimized extends LinearOpMode {

    RobotHardware robot = new RobotHardware(this);
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        robot.init();

        telemetry.addData("Status", "Initialized");
        telemetry.addLine("Press Options/Start to reset field-centric heading");
        telemetry.update();

        waitForStart();
        runtime.reset();

        // Variables for Slew Rate Limiter
        double targetY = 0, targetX = 0, targetRx = 0;
        final double SLEW_RATE = 0.15; // Max change per loop (adjust for feel)

        // Variables for PID Heading Lock
        double targetHeading = 0;
        final double PID_KP = 1.0; // PID Proportional constant (adjust for tuning)

        while (opModeIsActive()) {
            // Speed control
            double power;
            if(gamepad1.left_trigger > 0.0) {
                power = 0.3; // Slow
            } else if (gamepad1.right_trigger > 0.0) {
                power = 1.0; // Turbo
            } else {
                power = 0.7; // Normal
            }

            // Reset Gyro if needed
            if (gamepad1.options || gamepad1.start) {
                robot.resetYaw();
                targetHeading = 0; // Reset target to new 0
            }

            // 1. INPUT PROCESSING
            // We use -y because gamepads y is negative when pushed forward
            // Cubing inputs for precision
            double rawY = -Math.pow(gamepad1.left_stick_y, 3);
            double rawX = Math.pow(gamepad1.left_stick_x, 3);
            double rawRx = Math.pow(gamepad1.right_stick_x, 3);

            // 2. SLEW RATE LIMITER (Smooth Acceleration)
            // Move target values towards raw inputs at fixed rate
            if (rawY > targetY) targetY = Math.min(rawY, targetY + SLEW_RATE);
            else if (rawY < targetY) targetY = Math.max(rawY, targetY - SLEW_RATE);

            if (rawX > targetX) targetX = Math.min(rawX, targetX + SLEW_RATE);
            else if (rawX < targetX) targetX = Math.max(rawX, targetX - SLEW_RATE);

            if (rawRx > targetRx) targetRx = Math.min(rawRx, targetRx + SLEW_RATE);
            else if (rawRx < targetRx) targetRx = Math.max(rawRx, targetRx - SLEW_RATE);


            // 3. PID HEADING LOCK (Drift Correction)
            double botHeading = robot.getHeading();
            double correction = 0;

            // If driver is turning, update target to current
            if (Math.abs(targetRx) > 0.05) {
                targetHeading = botHeading;
                correction = targetRx; // Use manual turn
            } else {
                // If driver is driving straight/strafing, use PID to hold heading
                double error = angleWrap(targetHeading - botHeading);
                correction = error * PID_KP;
            }

            // 4. DRIVE
            robot.driveRobot(targetY, targetX, correction, power);

            telemetry.addData("Run Time", runtime.toString());
            telemetry.addData("Heading", Math.toDegrees(botHeading));
            telemetry.addData("Target Heading", Math.toDegrees(targetHeading));
            telemetry.addData("Correction", correction);
            telemetry.update();
        }
    }

    // Helper to wrap angle error to -PI to PI
    public double angleWrap(double radians) {
        while (radians > Math.PI) radians -= 2 * Math.PI;
        while (radians < -Math.PI) radians += 2 * Math.PI;
        return radians;
    }
}
