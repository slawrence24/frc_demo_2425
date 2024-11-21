package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SingleBrushedMotor;

public class JoystickCommand  extends Command {
        
    private SingleBrushedMotor m_subSystem;
    private DoubleSupplier m_forward;

    public JoystickCommand(DoubleSupplier forward, SingleBrushedMotor base) {

        m_subSystem = base;
        m_forward = forward;
        addRequirements(m_subSystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        var forward = m_forward.getAsDouble();
        m_subSystem.move(forward);
    }

    @Override
    public void end(boolean interrupted) {
        m_subSystem.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
