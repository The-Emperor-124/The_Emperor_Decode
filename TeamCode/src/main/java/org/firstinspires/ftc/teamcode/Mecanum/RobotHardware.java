package org.firstinspires.ftc.teamcode.Mecanum;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.OuttakeSubsystem;

public class RobotHardware {
    public final DriveSubsystem driveSubsystem;
    public final IntakeSubsystem intakeSubsystem;
    public final OuttakeSubsystem outtakeSubsystem;

    public RobotHardware() {
        driveSubsystem = new DriveSubsystem();
        intakeSubsystem = new IntakeSubsystem();
        outtakeSubsystem = new OuttakeSubsystem();
    }

    public void init(HardwareMap hardwareMap) {
        driveSubsystem.init(hardwareMap);
        intakeSubsystem.init(hardwareMap);
        outtakeSubsystem.init(hardwareMap);
    }
}