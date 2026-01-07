package org.firstinspires.ftc.teamcode.Mecanum;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.OuttakeSubsystem;

public class RobotHardware {
    public final DriveSubsystem driveSubsystem = null;
    public final IntakeSubsystem intakeSubsystem = null;
    public final OuttakeSubsystem outtakeSubsystem = null;


    public void init(HardwareMap hardwareMap) {
        driveSubsystem.init(hardwareMap);
        intakeSubsystem.init(hardwareMap);
        outtakeSubsystem.init(hardwareMap);
    }
}