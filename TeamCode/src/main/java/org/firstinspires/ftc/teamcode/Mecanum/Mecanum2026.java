package org.firstinspires.ftc.teamcode.Mecanum;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.subsystems.OuttakeSubsystem;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Mecanum2026", group="Linear Opmode")
public class Mecanum2026 extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    RobotHardware robot = new RobotHardware();

     Servo servoOut;
    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();



        waitForStart();
        runtime.reset();


        while (opModeIsActive()) {

            double power, powerIntake1, powerIntake2, powerIntake3;


            // turbo slow and normal modes
            if (gamepad1.left_bumper) {
                power = 0.2;
            } else if (gamepad1.right_bumper) {
                power = 1.0;
            } else {
                power = 0.6 ;
            }
            
            // DRIVE LOGIC
            robot.driveSubsystem.driveRobot(   gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, power);



            // INTAKE LOGIC
           /* if(gamepad2.a){       //intake in
                robot.intakeSubsystem.motorIntake1.setPower(-1.0);
            }
            else {
                robot.intakeSubsystem.motorIntake1.setPower(0.0);
            }
            if(gamepad2.dpad_down){       //intake1 out
                robot.intakeSubsystem.motorIntake1.setPower(1.0);
            }
            else {
                robot.intakeSubsystem.motorIntake1.setPower(0.0);
            }



           if(gamepad2.x){ //intake2 in
                robot.intakeSubsystem.motorIntake2.setPower(1.0 );
            }
            else {
                robot.intakeSubsystem.motorIntake2.setPower(0.0);
            }
            if(gamepad2.dpad_left){   //intake2 out
                robot.intakeSubsystem.motorIntake2.setPower(-1.0);
            }
            else {
                robot.intakeSubsystem.motorIntake2.setPower(0.0);
            } */

            //Systems Kevin (minte)
            // rasa ma-tii kevin ca nu mai ai buton pt fiecare in parte

            if(gamepad2.a){   //intake 1 IN
                robot.intakeSubsystem.motorIntake1.setPower(1.0);
            }
            else {
                robot.intakeSubsystem.motorIntake1.setPower(0.0);
            }

            if(gamepad2.x){  //intake 2 IN incet
                robot.intakeSubsystem.motorIntake2.setPower(1.0);
            }
            else {robot.intakeSubsystem.motorIntake2.setPower(0.0);}

            if(gamepad2.y){  //intake 2 out incet
                robot.intakeSubsystem.motorIntake2.setPower(-0.2);
            }
            else {
                  robot.intakeSubsystem.motorIntake2.setPower(0.0);}



            /*if(gamepad2.dpad_up){ //full systems (boss asta nu te ajuta)
                robot.intakeSubsystem.motorIntake1.setPower(1.0);
                robot.intakeSubsystem.motorIntake2.setPower(1.0);
                robot.outtakeSubsystem.motorOutake1.setPower(1.0);
                robot.outtakeSubsystem.motorOutake2.setPower(1.0);
            }
            else{
                robot.intakeSubsystem.motorIntake1.setPower(0.0);
                robot.intakeSubsystem.motorIntake2.setPower(0.0);
                robot.outtakeSubsystem.motorOutake1.setPower(0.0);
                robot.outtakeSubsystem.motorOutake2.setPower(0.0);
            }
*/
            if(gamepad2.dpad_right){ //intake 1 si 2
                robot.intakeSubsystem.motorIntake1.setPower(1.0);
                robot.intakeSubsystem.motorIntake2.setPower(0.7);
            }
            else{
                    robot.intakeSubsystem.motorIntake1.setPower(0.0);
                    robot.intakeSubsystem.motorIntake2.setPower(0.0);
                }

            if(gamepad2.dpad_down){ // intake 1+2 out
                robot.intakeSubsystem.motorIntake1.setPower(-0.6);
                robot.intakeSubsystem.motorIntake2.setPower(-0.6);
            }
            else{
                robot.intakeSubsystem.motorIntake1.setPower(0.0);
                robot.intakeSubsystem.motorIntake2.setPower(0.0);
                robot.outtakeSubsystem.stop();
            }



            if(gamepad2.b){  //outake
                robot.outtakeSubsystem.setOuttakeRPM(5100);
            }
            else {
                robot.outtakeSubsystem.stop();
            }

            if(gamepad2.b && gamepad2.right_bumper){
                robot.outtakeSubsystem.fullSpeed();

            }
            else {
                robot.outtakeSubsystem.stop();
            }



            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }

    }
}