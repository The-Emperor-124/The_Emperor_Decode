package org.firstinspires.ftc.teamcode.Autonom;

import com.acmerobotics.roadrunner.AccelConstraint;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;


import java.util.Arrays;

@Autonomous(name = "AutoAlbastru", group = "Autonomous")
public class AutoAlbastru extends LinearOpMode {
    @Override
    public void runOpMode() {
        // aici trebuie sa vedeti de pe ce pozitie din teren porniti si sa o puneti
        Pose2d initialPose = new Pose2d(65, -11, Math.toRadians(180));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);


        AccelConstraint accFast=new ProfileAccelConstraint(-105.0,105.0);
        AccelConstraint accSlow = new ProfileAccelConstraint(-45.0, 90.0);

        VelConstraint speedFast= new MinVelConstraint(Arrays.asList(
                new TranslationalVelConstraint(130.0),
                new AngularVelConstraint(Math.PI / 2)

        ));

        VelConstraint speedSlow= new MinVelConstraint(Arrays.asList(
                new TranslationalVelConstraint(80.0),
                new AngularVelConstraint(Math.PI / 2)

        ));

        // OPTIMIZATION: Enable Bulk Reads for faster loops
        for (com.qualcomm.hardware.lynx.LynxModule module : hardwareMap.getAll(com.qualcomm.hardware.lynx.LynxModule.class)) {
            module.setBulkCachingMode(com.qualcomm.hardware.lynx.LynxModule.BulkCachingMode.AUTO);
        }



        TrajectoryActionBuilder parcare = drive.actionBuilder(initialPose)
                .strafeToConstantHeading(new Vector2d(37.5, 33.5), speedSlow, accSlow);
        // Wait for Start
        waitForStart();

        if (isStopRequested()) return;

        Action actParcare = parcare.build();

        // Run Actions
        Actions.runBlocking(
                new SequentialAction(
                        actParcare
                )
        );
    }
}
