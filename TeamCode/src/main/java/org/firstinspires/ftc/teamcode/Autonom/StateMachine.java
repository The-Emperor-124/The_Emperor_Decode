package org.firstinspires.ftc.teamcode.Autonom;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Mecanum.RobotHardware;

@Autonomous
public class StateMachine extends OpMode {

    RobotHardware robot  = new RobotHardware(this);
    enum state {
        WAIT_FOR_A,
        WAIT_FOR_B,
        WAIT_FOR_X,
        FINISHED
    }
    state state;
    {
        state = state.WAIT_FOR_A;
    }


    @Override
    public void init() {
        robot.init(hardwareMap);
        state = state.WAIT_FOR_A;
    }

    @Override
    public void loop() {
        telemetry.addData("Cur State" , state);
        switch (state){
            case WAIT_FOR_A:
                telemetry.addLine("To exit state, press A");
                if(gamepad1.a){
                    state = state.WAIT_FOR_B;
                }
                break;
            case WAIT_FOR_B:
                telemetry.addLine("To exit state, press B");
                if(gamepad1.b){
                    state = state.WAIT_FOR_X;
                }
                break;
            case WAIT_FOR_X:
                telemetry.addLine("To exit state, press X");
                if(gamepad1.x){
                    state = state.FINISHED;
                }
                break;
            default :
                telemetry.addLine("Auto state machine finished");
    }

}
}
