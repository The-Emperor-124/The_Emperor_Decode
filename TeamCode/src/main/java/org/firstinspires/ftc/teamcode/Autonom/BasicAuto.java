package org.firstinspires.ftc.teamcode.Autonom;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OuttakeSubsystem;

@Autonomous(name = "BasicAuto", group = "Autonomous")
public class BasicAuto extends LinearOpMode {
    @Override
    public void runOpMode() {
        // aici trebuie sa vedeti de pe ce pozitie din teren porniti si sa o puneti
        Pose2d initialPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        // OPTIMIZATION: Enable Bulk Reads for faster loops
        for (com.qualcomm.hardware.lynx.LynxModule module : hardwareMap.getAll(com.qualcomm.hardware.lynx.LynxModule.class)) {
            module.setBulkCachingMode(com.qualcomm.hardware.lynx.LynxModule.BulkCachingMode.AUTO);
        }

        // Initializam subsistemele folosite
        IntakeSubsystem intake = new IntakeSubsystem();
        OuttakeSubsystem outtake = new OuttakeSubsystem();
        
        intake.init(hardwareMap);
        outtake.init(hardwareMap);

        // aici ne definim traseele pe care le vom urma
        // un traseu = de ex de la locul de start la locul de pornit outake pt prima lansare
        // astea sunt doar ex random nu le luati ca atare e doar sa invatati voi
        Action trajectoryAction1 = drive.actionBuilder(initialPose)
                .lineToX(10)
                .turn(Math.toRadians(90))
                .lineToY(10)
                .build();

        Action trajectoryAction2 = drive.actionBuilder(new Pose2d(10, 10, Math.toRadians(90)))
                .lineToY(0)
                .turn(Math.toRadians(-90))
                .lineToX(0)
                .build();

        // Wait for Start
        waitForStart();

        if (isStopRequested()) return;

        // Run Actions
        Actions.runBlocking(
                new SequentialAction(
                        // asta inseamna ca ce e in blocul de paralel se executa simultan
                        // ca niste threaduri
                        new ParallelAction(
                                trajectoryAction1,
                                intake.spinIntakeAction(0.5)
                        ),
                        
                        // astea sunt secventiale deci sunt executate pe rand
                        intake.spinIntakeAction(0),
                        outtake.setLiftPowerAction(0.5),
                        
                        trajectoryAction2,
                        outtake.setLiftPowerAction(0)
                )
        );
    }
}
