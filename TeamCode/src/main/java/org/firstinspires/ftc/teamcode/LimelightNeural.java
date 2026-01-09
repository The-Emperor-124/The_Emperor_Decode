package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;

/*
 * This OpMode demonstrates Neural Network Object Detection (CNN) using Limelight 3A.
 * 
 * USE CASE:
 * Smarter detection of "Artifacts" (Purple/Green Balls) that distinguishes them 
 * from other objects better than simple color tuning.
 * 
 * SETUP:
 * 1. Train a model (e.g. YOLOv8n) to detect:
 *    - Class 0: "purple_ball"
 *    - Class 1: "green_ball"
 * 2. Upload the .tflite model to Limelight Web UI.
 * 3. Set this Pipeline to "Neural Detector" type.
 * 
 */
@TeleOp(name = "Limelight: Neural/CNN", group = "Limelight")
public class LimelightNeural extends LinearOpMode {

    private Limelight3A limelight;

    @Override
    public void runOpMode() throws InterruptedException {
        limelight = hardwareMap.get(Limelight3A.class, "limelight3A");

        // Assuming Pipeline 2 is your Neural Network Pipeline
        int neuralPipeline = 2;
        limelight.pipelineSwitch(neuralPipeline);
        limelight.start();

        telemetry.addLine("Limelight Neural Ready.");
        telemetry.addLine("Make sure Pipeline " + neuralPipeline + " is configured with your .tflite model.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            LLResult result = limelight.getLatestResult();

            if (result != null && result.isValid()) {
                // Get list of all detected objects
                List<LLResultTypes.DetectorResult> detections = result.getDetectorResults();

                telemetry.addData("Objects Detected", detections.size());

                for (LLResultTypes.DetectorResult detection : detections) {
                    // Extract info for each object
                    String className = detection.getClassName(); // Requires "labels.txt" on Limelight
                    double confidence = detection.getConfidence();
                    double tx = detection.getTargetXDegrees();
                    double ty = detection.getTargetYDegrees();

                    // Format output
                    telemetry.addLine(String.format("Obj: %s (%.1f%%)", className, confidence * 100));
                    telemetry.addLine(String.format("   Loc: x=%.1f, y=%.1f", tx, ty));
                    
                    // Example Usage: Only act on high confidence
                    if (confidence > 0.8) {
                        if (className.equals("purple_ball")) {
                            telemetry.addLine("   -> PURPLE PRIORITY!");
                        } else if (className.equals("green_ball")) {
                            telemetry.addLine("   -> GREEN PRIORITY!");
                        }
                    }
                }
            } else {
                telemetry.addData("Status", "Searching...");
            }

            telemetry.update();
        }
        limelight.stop();
    }
}
