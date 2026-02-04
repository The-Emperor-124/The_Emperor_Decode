package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
public class OuttakePIDSubsystem {
    
    // Hardware
    private DcMotorEx outDr = null;
    private DcMotorEx outSt = null;
    private Servo servoDisc = null;
    private Servo servoOut = null;

    // --- TUNING VARIABLES (Edit these in Dashboard) ---
    // Feedforward (F): Most important for speed! 
    // kV = 1 / MaxVelocity. Start with 0.0, increase until motor reaches target approx.
    public static double kV = 0.0;  
    public static double kS = 0.0; // Static friction (power to just barely move)

    // PID: Corrections
    public static double kP = 0.0; // Proportional
    public static double kI = 0.0; // Integral (Leave 0 usually)
    public static double kD = 0.0; // Derivative (Leave 0 usually)

    // Example Target
    public static double TARGET_RPM = 2000;
    // ------------------------------------------------

    // State variables
    private double currentTargetRPM = 0;
    // Integral sum for PID (if utilized)
    private double integralSum = 0;
    // Last loop time for consistent PID
    private double lastLoopTime = 0;
    
    private final ElapsedTime timer = new ElapsedTime();

    // Servo Constants (Taken from your original code)
    public static final double SERVO_OUT_CLOSE = 0.8;
    public static final double SERVO_OUT_OPEN = 0.1;
    public static final double DISC_INITIAL = 0.67;

    public void init(HardwareMap hardwareMap) {
        // cast to DcMotorEx for velocity features
        outDr = hardwareMap.get(DcMotorEx.class, "outDr");
        outSt = hardwareMap.get(DcMotorEx.class, "outSt");
        
        servoDisc = hardwareMap.get(Servo.class, "servoDisc");
        servoOut = hardwareMap.get(Servo.class, "servoOut");

        // Directions
        outSt.setDirection(DcMotorSimple.Direction.FORWARD);
        outDr.setDirection(DcMotorSimple.Direction.REVERSE);

        // IMPORTANT: Run without internal PID because we are making our own
        outDr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        outSt.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Reset timer
        timer.reset();
        lastLoopTime = timer.seconds();
    }

    /**
     * Call this in your TeleOp loop() repeatedly!
     * This calculates and applies the power.
     */
    public void update() {
        // 1. Get current velocity (Ticks per Second)
        // Taking average of both motors is safer
        double currentVelTicks = (outDr.getVelocity() + outSt.getVelocity()) / 2.0;

        // 2. Convert to RPM (GoBILDA 5202/5203 example formula, adjust TICKS_PER_REV)
        // yellow jacket 312RPM = 537.7 ticks
        // yellow jacket 1150RPM (likely for shooter) = 145.1 ticks
        // yellow jacket 1620RPM = 103.8 ticks 
        // CHECK YOUR MOTOR SPEC SHEET!
        double TICKS_PER_REV = 145.1; // Example for 1150 RPM motor
        double currentRPM = (currentVelTicks / TICKS_PER_REV) * 60.0;

        // 3. Calculate Error
        double error = currentTargetRPM - currentRPM;

        // 4. PID Calculations
        double currentTime = timer.seconds();
        double dt = currentTime - lastLoopTime;
        
        integralSum += error * dt;
        double derivative = 0; // (Calculate if needed, usually noise makes it bad for shooters)
        
        // 5. Feedforward Calculation
        // kV contribution: purely based on target velocity
        double feedforward = (kV * currentTargetRPM) + kS;

        // 6. Total Power
        double pid = (kP * error) + (kI * integralSum) + (kD * derivative);
        double power = feedforward + pid;

        // 7. Apply Power
        if (currentTargetRPM == 0) {
            power = 0;
            integralSum = 0; // Reset
        }
        
        outDr.setPower(power);
        outSt.setPower(power);

        lastLoopTime = currentTime;
    }

    public void setTargetRPM(double rpm) {
        this.currentTargetRPM = rpm;
    }

    public double getVelocityRPM() {
         // Assuming 145.1 ticks per rev, CHANGE THIS to your motor
         double TICKS_PER_REV = 145.1; 
         double velTicks = (outDr.getVelocity() + outSt.getVelocity()) / 2.0;
         return (velTicks / TICKS_PER_REV) * 60.0;
    }
}
