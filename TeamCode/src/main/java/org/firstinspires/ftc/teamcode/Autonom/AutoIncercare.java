package org.firstinspires.ftc.teamcode.Autonom;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Mecanum.RobotHardware;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;



@Autonomous(name="AutoIncercare", group="Autonom")

public class AutoIncercare extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        RobotHardware robot = new RobotHardware();
        robot.init(hardwareMap);

        waitForStart();
        while (opModeIsActive()){
            robot.driveSubsystem.moveForward(0.5, 1000);
            robot.driveSubsystem.moveBackward(0.5, 1000);
        }
        }
    }


