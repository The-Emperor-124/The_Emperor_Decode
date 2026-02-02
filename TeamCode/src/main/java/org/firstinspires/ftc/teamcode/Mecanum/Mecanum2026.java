package org.firstinspires.ftc.teamcode.Mecanum;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.Subsystems.OuttakeSubsystem;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name="Mecanum2026", group="Linear Opmode")
public class Mecanum2026 extends LinearOpMode {
    private boolean intakePower = false;
    private boolean outakePower = false;
    private boolean lastButtonA_State = false;
    private boolean lastButtonY_State = false;

    private ElapsedTime runtime = new ElapsedTime();
    RobotHardware robot = new RobotHardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);


        telemetry.addData("Status", "Initialized");
        telemetry.update();


        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            boolean currentButtonA_State = gamepad2.a;
            boolean currentButtonY_State = gamepad2.y;

            double power, powerIntake, powerOutake;


            // turbo slow and normal modes
            if (gamepad1.left_bumper) {
                power = 0.2;
            } else if (gamepad1.right_bumper) {
                power = 1.0;
            } else {
                power = 0.7;
            }
            
            // DRIVE LOGIC
            robot.driveSubsystem.driveRobot(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, power);

            // INTAKE LOGIC
            if (gamepad2.a) {
                powerIntake = 1.0;
            } else {
                powerIntake = 0.0;
            }

            if (currentButtonA_State && !lastButtonA_State) {
                intakePower = !intakePower;
            }
            if (intakePower) {
                powerIntake = 1.0;
            } else powerIntake = 0.0;
            
            lastButtonA_State = currentButtonA_State;

            robot.intakeSubsystem.setPower(powerIntake);



            // OUTTAKE LOGIC

            if (gamepad2.y) {
                powerOutake = 1.0;
            } else {
                powerOutake = 0.0;
            }
            
            if (currentButtonY_State && !lastButtonY_State) {
                outakePower = !outakePower;
                // double servoPos = robot.outtakeSubsystem.getServoOutPosition(); // Nu cred ca l folositi pe asta decomentati daca da
            }

            if (gamepad2.x) {
                robot.outtakeSubsystem.setServoOutPosition(OuttakeSubsystem.SERVO_OUT_CLOSE); // 0.8
            }
            if (gamepad2.b) {
                robot.outtakeSubsystem.setServoOutPosition(OuttakeSubsystem.SERVO_OUT_OPEN); // 0.1
            }
            
            if (outakePower) {
                powerOutake = 1.0;
            } else {
                powerOutake = 0.0;
            }
            lastButtonY_State = currentButtonY_State;
            robot.outtakeSubsystem.setOutakeMotorsPower(powerOutake);


            // positions intake (ServoDisc)
            if (gamepad2.dpad_up) {
                robot.outtakeSubsystem.setServoDiscPosition(OuttakeSubsystem.DISC_UP); // 0.43
            }
            if (gamepad2.dpad_right) {
                robot.outtakeSubsystem.setServoDiscPosition(OuttakeSubsystem.DISC_RIGHT); // 0.52
            }

            if (gamepad2.dpad_left) {
                robot.outtakeSubsystem.setServoDiscPosition(OuttakeSubsystem.DISC_LEFT); // 0.595
            }

            ///// positions outtake
            if (gamepad2.dpad_down) {
                robot.outtakeSubsystem.setServoDiscPosition(OuttakeSubsystem.DISC_DOWN); // 0.545
            }
            if (gamepad2.right_bumper) {
                robot.outtakeSubsystem.setServoDiscPosition(OuttakeSubsystem.DISC_POS_1);   // 0.625
            }
            if (gamepad2.left_bumper) {
                robot.outtakeSubsystem.setServoDiscPosition(OuttakeSubsystem.DISC_POS_2); // 0.79
            }

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }

    }
}