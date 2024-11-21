package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubSystem;

public class ResetEncoderCommand extends Command {
    
    private ArmSubSystem m_arm;
    private boolean m_isReset; 

    public ResetEncoderCommand(ArmSubSystem arm) {
        m_arm = arm;
        addRequirements(m_arm);
    }

    @Override
    public void initialize() {
        m_isReset = false;
    }

    @Override
    public void execute() {
        m_arm.resetEncoders();
        m_isReset = true;
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return m_isReset;
    }
}
