package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.MotorPort;
import frc.robot.ShuffleboardDisplay;


public class SingleBrushedMotor extends SubsystemBase {
    private final WPI_TalonSRX m_motor = new WPI_TalonSRX(MotorPort.IntakeMotorTalon);

    // Set to zero to skip waiting for confirmation, set to nonzero to wait and
    // report to DS if action fails.
    private static final int kTimeoutMs = 30;

    private static final double kMaxOutput = 0.5;
    private static final boolean kMotorInvert = false;

    private double m_currentSpeed = 0;
    

    public SingleBrushedMotor() {
        initController();

        ShuffleboardDisplay.SingleLayout
            .addDouble("Voltage", m_motor::getMotorOutputVoltage);
        ShuffleboardDisplay.SingleLayout
            .addDouble("Speed", this::getCurrentSpeed);
    }

    public double getCurrentSpeed() {
        return m_currentSpeed;
    }

    public void move(double speed) {
        m_currentSpeed = speed;
        m_motor.set(speed);
    }

    public void stop() {
        m_motor.stopMotor();
        m_currentSpeed = 0;
    }

    public void initController() {
        // Factory Default all hardware to prevent unexpected behaviour
        m_motor.configFactoryDefault();

        // Set based on what direction you want forward/positive to be.
        // This does not affect sensor phase.
        m_motor.setInverted(kMotorInvert);

        // Config the peak and nominal outputs, 12V means full
        m_motor.configNominalOutputForward(0, kTimeoutMs);
        m_motor.configNominalOutputReverse(0, kTimeoutMs);
        m_motor.configPeakOutputForward(kMaxOutput, kTimeoutMs);
        m_motor.configPeakOutputReverse(-1 * kMaxOutput, kTimeoutMs);

        m_motor.setNeutralMode(NeutralMode.Brake);     
    }

}
