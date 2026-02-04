# Limelight 3A Guide for FTC "DECODE" (2025-2026)

## Game Context: The DECODE Season
The 2025-2026 season revolves around **Artifacts**—colored polypropylene balls:
- **Purple Artifacts**
- **Green Artifacts**

Teams must manipulate these to unlock mysteries, interacting with field components like soft tiles and scoring zones. This guide provides the vision system solutions to detect these elements.

---

## Part 1: Configuration Guide

### 1. Simple Color Pipeline (The FAST way)
**Best for:** Simply finding a "blob" of color. Fast (60fps+) and easy to set up.

1.  **Connect**: Power your robot and connect to the Limelight WiFi (or via Ethernet).
2.  **Open UI**: Go to `http://172.58.0.1:5801` in your browser.
3.  **Pipeline 0 (Purple)**:
    - Set Type to **"Color / Retro"**.
    - Use the **Eyedropper** tool on a Purple Ball image to auto-tune HSV.
    - Tune "Area" filters to ignore small noise.
4.  **Pipeline 1 (Green)**:
    - Repeat the process for Green Balls.

### 2. Neural Network / CNN (The SMART way)
**Best for:** Distinguishing specific objects (e.g. "Ball" vs "Wall") and robust detection in varying light.

1.  **Gather Data**: Use "Snap" in Limelight UI to save ~50 images of balls.
2.  **Label**: Upload to [Roboflow](https://roboflow.com). Label `purple_ball` and `green_ball`.
3.  **Train**: Train a **YOLOv8 Nano** model.
4.  **Deploy**: 
    - Export as **TFLite**.
    - Upload `.tflite` and `labels.txt` to Limelight **Pipeline 2** (set to "Neural Detector").

---

## Part 2: Code Examples

### Example 1: `LimelightColor.java`
*Use this for basic tracking with Pipelines 0 and 1.*

```java
package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Limelight: Color Tracking", group = "Limelight")
public class LimelightColor extends LinearOpMode {

    private Limelight3A limelight;

    @Override
    public void runOpMode() throws InterruptedException {
        limelight = hardwareMap.get(Limelight3A.class, "limelight3A");

        int currentPipeline = 0; // 0 = Purple, 1 = Green
        limelight.pipelineSwitch(currentPipeline);
        limelight.start();

        telemetry.addLine("Ready. Press A (Purple) or B (Green).");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                limelight.pipelineSwitch(0);
                currentPipeline = 0;
            } else if (gamepad1.b) {
                limelight.pipelineSwitch(1);
                currentPipeline = 1;
            }

            LLResult result = limelight.getLatestResult();
            if (result != null && result.isValid()) {
                telemetry.addData("Pipeline", currentPipeline == 0 ? "Purple" : "Green");
                telemetry.addData("Tx (Offset)", result.getTx());
                telemetry.addData("Ty (Offset)", result.getTy());
                telemetry.addData("Area", result.getTa());
            } else {
                telemetry.addData("Status", "No Target");
            }
            telemetry.update();
        }
        limelight.stop();
    }
}
```

### Example 2: `LimelightNeural.java`
*Use this for AI detection with Pipeline 2.*

```java
package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;

@TeleOp(name = "Limelight: Neural/CNN", group = "Limelight")
public class LimelightNeural extends LinearOpMode {

    private Limelight3A limelight;

    @Override
    public void runOpMode() throws InterruptedException {
        limelight = hardwareMap.get(Limelight3A.class, "limelight3A");
        
        limelight.pipelineSwitch(2); // Neural Pipeline
        limelight.start();

        waitForStart();

        while (opModeIsActive()) {
            LLResult result = limelight.getLatestResult();
            
            if (result != null && result.isValid()) {
                List<LLResultTypes.DetectorResult> detections = result.getDetectorResults();
                
                for (LLResultTypes.DetectorResult detection : detections) {
                    telemetry.addLine("Obj: " + detection.getClassName());
                    telemetry.addData("Conf", "%.2f", detection.getConfidence());
                    telemetry.addData("Pos", "x=%.1f, y=%.1f", detection.getTargetXDegrees(), detection.getTargetYDegrees());
                }
            }
            telemetry.update();
        }
        limelight.stop();
    }
}
```

## Goal
Provide two distinct OpModes: one for basic color blob tracking and another for advanced Neural Network (CNN) object detection.

## Proposed Changes

### 1. LimelightColor.java
A TeleOp mode focused on specific Color Pipeline usage (e.g., Pipeline 0).

**Functionality:**
*   Initialize Limelight.
*   Set pipeline to 0 (user must configure this in UI for Yellow/Red/Blue).
*   Read `tx` (horizontal offset), `ty` (vertical offset), and `ta` (area).
*   Telemetry: "Tracking Color Blob".

### 2. LimelightNeural.java
A TeleOp mode focused on Neural Network results (e.g., Pipeline 1).

**Functionality:**
*   Initialize Limelight.
*   Set pipeline to 1 (user must upload a .tflite model for this).
*   Access `getDetectorResults()` list.
*   Loop through detections to show:
    *   Class Name (e.g., "Sample", "Specimen").
    *   Confidence %.
    *   Bounding Box coordinates.

### 3. Documentation: Limelight_Guide.md
A guide explaining:
*   **Color Setup:** Using the eyedropper tool in Limelight UI.
*   **Neural Setup:**
    *   **Easiest:** Using the "Classifier" built-in (Good for simple "Is this a block?" checks).
    *   **Smarter:** Training a YOLOv8 Nano model on Roboflow and uploading it to Limelight (Best for multiple objects).

## Verification Plan
*   **Compile:** Ensure both files compile correctly in the user's project structure.
*   **Check Imports:** Verify `com.qualcomm.hardware.limelightvision.LLResult` and `LLResultTypes` are used correctly.