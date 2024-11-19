package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SingleBrushedMotor;

public class ActionCommand extends Command {
    
    private SingleBrushedMotor m_subSystem;
    private double m_time;
    private double m_forwardSpeed;

    public ActionCommand(double time, double forwardSpeed, SingleBrushedMotor base) {

        m_subSystem = base;
        m_time = time;
        m_forwardSpeed = forwardSpeed;
        addRequirements(m_subSystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
