package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SingleBrushedMotor;

public class ActionCommand extends Command {
    
    private SingleBrushedMotor m_subSystem;
    
    private double m_time;
    private double m_forwardSpeed;
    private boolean m_isFinished = false;
    private Timer m_timer;
    private boolean m_timerIsStarted = false;

    public ActionCommand(double time, double forwardSpeed, SingleBrushedMotor base) {
        m_subSystem = base;
        m_time = time;
        m_forwardSpeed = forwardSpeed;
        m_timer = new Timer();
        addRequirements(m_subSystem);
    }

    @Override
    public void initialize() {
        m_isFinished = false;
        m_timer.reset();
        m_timerIsStarted = false;
    }

    @Override
    public void execute() {
        StartTimer();
        m_subSystem.move(m_forwardSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        m_subSystem.stop();
        m_timer.stop();
        m_timerIsStarted = false;
    }

    @Override
    public boolean isFinished() {
        if (m_timer.get() < m_time) {
            return m_isFinished;
        } else {
            return true;
        }
    }
    
    private void StartTimer() {
        if (!m_timerIsStarted) {
            m_timer.reset();
            m_timer.start();
            m_timerIsStarted = true;
        }
    }
}
