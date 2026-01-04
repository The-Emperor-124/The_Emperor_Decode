package org.firstinspires.ftc.teamcode.Mecanum;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;



@TeleOp(name="Mecanum2026)", group="Linear Opmode")
public class Mecanum2026 extends LinearOpMode {
    private boolean intakePower=false;
    private boolean outakePower=false;
    private boolean lastButtonA_State=false;
    private boolean lastButtonY_State=false;

    private ElapsedTime runtime = new ElapsedTime();
    RobotHardware robot = new RobotHardware(this);




    @Override
    public void runOpMode() {
        robot.init(hardwareMap);


        telemetry.addData("Status", "Initialized");
        telemetry.update();


        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            boolean currentButtonA_State= gamepad2.a;
            boolean currentButtonY_State=gamepad2.y;
            double power,powerIntake,powerOutake;


            // turbo slow and normal modes
            if (gamepad1.left_trigger > 0.0) {
                power = 0.2;
            } else if (gamepad1.right_trigger > 0.0) {
                power = 1.0;
            } else {
                power = 0.7;
            }
            // Using the new modular hardware class to drive
            robot.driveRobot(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, power);



            if(gamepad2.a) {
                powerIntake=1.0;}
            else {powerIntake=0.0;}

            if(currentButtonA_State && !lastButtonA_State)
            {
                intakePower=!intakePower;
            }
            if(intakePower)
            {
                powerIntake=1.0;
            } else powerIntake=0.0;
            lastButtonA_State=currentButtonA_State;
            robot.invarteMotorIntake(powerIntake);


            if(gamepad2.y){
                powerOutake = 1.0;
            }
            else {
                powerOutake = 0.0;
            }
            if(currentButtonY_State && !lastButtonY_State){
                outakePower = !outakePower;
                double servoPos = robot.servoOut.getPosition();

            }
            if(gamepad2.x){
                robot.servoOut.setPosition(0.8);
            }
            if(gamepad2.b){
                robot.servoOut.setPosition(0.1);
            }
            if(outakePower){
                powerOutake=1.0;
            }
            else {
                powerOutake = 0.0;
            }
            lastButtonY_State = currentButtonY_State;
            robot.outake(powerOutake);

           /* if(gamepad2.yWasPressed()) {
                robot.outDr.setPower(1.0);
                robot.outSt.setPower(1.0);
                robot.servoOut.setPosition(0.4);
            } else if (gamepad2.yWasReleased()) {
                robot.outDr.setPower(0.0);
                robot.outSt.setPower(0.0);
                robot.servoOut.setPosition(0.2);
            } */
                   //pozziitii intake
            if(gamepad2.dpad_up){
                robot.servoDisc.setPosition(0.43); //0.35
            }
            if(gamepad2.dpad_right){
                robot.servoDisc.setPosition(0.52);
            }

            if(gamepad2.dpad_left){
                robot.servoDisc.setPosition(0.595);
            }

                    ///// pozitii outake
            if(gamepad2.dpad_down){
                robot.servoDisc.setPosition(0.545); //0.18
            }
            if(gamepad2.right_bumper){
                robot.servoDisc.setPosition(0.625);   //28
            }
            if(gamepad2.left_bumper){
                robot.servoDisc.setPosition(0.79); //12
            }

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }

    }
}