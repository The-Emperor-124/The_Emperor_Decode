package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/*
 * This OpMode demonstrates basic Color Tracking (Blob Detection) using Limelight 3A.
 * 
 * USE CASE:
 * Tracking the "Artifacts" (Purple and Green Balls) using simple color thresholds.
 * 
 * SETUP:
 * 1. Open Limelight Web UI (http://172.58.0.1:5801).
 * 2. Configure Pipeline 0 for PURPLE balls (adjust HSV to catch the purple color).
 * 3. Configure Pipeline 1 for GREEN balls (adjust HSV to catch the green color).
 * 
 * CONTROLS:
 * - Gamepad A: Switch to PURPLE pipeline (0).
 * - Gamepad B: Switch to GREEN pipeline (1).
 */
@TeleOp(name = "Limelight: Color Tracking", group = "Limelight")
public class LimelightColor extends LinearOpMode {

    private Limelight3A limelight;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the hardware
        limelight = hardwareMap.get(Limelight3A.class, "limelight3A");

        // Start with Pipeline 0 (Purple)
        int currentPipeline = 0;
        limelight.pipelineSwitch(currentPipeline);

        telemetry.setMsTransmissionInterval(11);
        telemetry.addLine("Limelight Color Tracker Ready.");
        telemetry.addLine("Press A for PURPLE (Pipe 0)");
        telemetry.addLine("Press B for GREEN (Pipe 1)");
        telemetry.update();

        // Start the camera polling
        limelight.start();

        waitForStart();

        while (opModeIsActive()) {
            
            // --- Input Handling ---
            if (gamepad1.a) {
                currentPipeline = 0;
                limelight.pipelineSwitch(currentPipeline);
            } else if (gamepad1.b) {
                currentPipeline = 1;
                limelight.pipelineSwitch(currentPipeline);
            }

            // --- Get Results ---
            LLResult result = limelight.getLatestResult();

            // --- Display Data ---
            telemetry.addData("Current Pipeline", currentPipeline == 0 ? "0 (Purple)" : "1 (Green)");

            if (result != null && result.isValid()) {
                // tx: Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
                double tx = result.getTx(); 
                // ty: Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
                double ty = result.getTy();
                // ta: Target Area (0% of image to 100% of image)
                double ta = result.getTa();

                telemetry.addData("Target Detected", "Yes");
                telemetry.addData("x (yaw)", "%.2f", tx);
                telemetry.addData("y (pitch)", "%.2f", ty);
                telemetry.addData("Area", "%.2f%%", ta);

                // Example Logic: Turn to face the ball
                if (tx > 1.0) {
                    telemetry.addLine("Turn Right >>>>");
                } else if (tx < -1.0) {
                    telemetry.addLine("<<<< Turn Left");
                } else {
                    telemetry.addLine("== CENTERED ==");
                }

            } else {
                telemetry.addData("Target Detected", "No");
            }

            telemetry.update();
        }
        
        limelight.stop();
    }
}
