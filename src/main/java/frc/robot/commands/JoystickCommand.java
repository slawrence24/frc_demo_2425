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
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
