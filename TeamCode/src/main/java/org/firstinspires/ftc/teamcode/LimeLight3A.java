package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp(name="LimeLight",group = "Testing")
public class LimeLight3A extends OpMode {
    private Limelight3A limeLight;
    private IMU imu;
    @Override
    public void init(){

        limeLight=hardwareMap.get(Limelight3A.class,"limelight3A");
        imu=hardwareMap.get(IMU.class,"imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));

        limeLight.pipelineSwitch(9);// pipe e configuratia pe care o foloseste ca sa detecteze un anumit Tag
        imu.initialize(parameters);


    }
    @Override
    public  void start()
    {
        limeLight.start();
    }

    @Override
    public void loop()
    {
        YawPitchRollAngles orientationYaw=imu.getRobotYawPitchRollAngles();
        limeLight.updateRobotOrientation(orientationYaw.getYaw());
        LLResult limelightResult=limeLight.getLatestResult();
        if(limelightResult!=null && limelightResult.isValid())
        {
            Pose3D robotPose=limelightResult.getBotpose_MT2();
            telemetry.addData("Target_x",limelightResult.getTx());
            telemetry.addData("Target_y",limelightResult.getTy());
            telemetry.addData("Target_area",limelightResult.getTa());
            telemetry.addData("robotPose",robotPose.toString());

        }
    }
}
