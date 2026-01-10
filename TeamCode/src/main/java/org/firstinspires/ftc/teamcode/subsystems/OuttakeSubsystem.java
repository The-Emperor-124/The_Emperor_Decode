package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class OuttakeSubsystem {
    public DcMotor outDr = null;
    public DcMotor outSt = null;
    //public Servo servoDisc = null;
    public Servo servoOut = null;

    // Servo Constants
    public static final double SERVO_OUT_CLOSE = 0.8;
    public static final double SERVO_OUT_OPEN = 0.1;

  //  public static final double DISC_UP = 0.43;
  //  public static final double DISC_RIGHT = 0.52;
   // public static final double DISC_LEFT = 0.595;
  //  public static final double DISC_DOWN = 0.545;
    
    // Additional positions from gamepad2 bumpers
  //  public static final double DISC_POS_1 = 0.625;
   // public static final double DISC_POS_2 = 0.79;
  //  public static final double DISC_INITIAL = 0.67;


    public void init(HardwareMap hardwareMap) {
        outDr = hardwareMap.get(DcMotor.class, "outDr");
        outSt = hardwareMap.get(DcMotor.class, "outSt");
      //  servoDisc = hardwareMap.get(Servo.class, "servoDisc");
        servoOut = hardwareMap.get(Servo.class, "servoOut");

        outSt.setDirection(DcMotorSimple.Direction.FORWARD);
        outDr.setDirection(DcMotorSimple.Direction.REVERSE);

       // servoDisc.setPosition(DISC_INITIAL);
        servoOut.setPosition(SERVO_OUT_CLOSE); 
    }

    public void setOutakeMotorsPower(double power) {
        outDr.setPower(power);
        outSt.setPower(power);
    }

    public void setServoOutPosition(double position) {
        servoOut.setPosition(position);
    }

   /* public void setServoDiscPosition(double position) {
        servoDisc.setPosition(position);
    }*/
    
    public double getServoOutPosition() {
        return servoOut.getPosition();
    }
}
