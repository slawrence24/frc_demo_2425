package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

// import edu.wpi.first.networktables.GenericEntry;
// import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.ShuffleboardDisplay;

public class ArmSubSystem extends SubsystemBase {
    private final WPI_TalonSRX m_motorMaster = new WPI_TalonSRX(Constants.MotorPort.ArmMotorMaster);
    private final WPI_TalonSRX m_motorSlave = new WPI_TalonSRX(Constants.MotorPort.ArmMotorSlave);

    // private final DigitalInput m_bottomRightLimitSwitch = new
    // DigitalInput(Constants.DigitalInputPort.ArmBottomRightLimitSwitch);
    // private final DigitalInput m_bottomLeftLimitSwitch = new
    // DigitalInput(Constants.DigitalInputPort.ArmBottomLeftLimitSwitch);
    // private final DigitalInput m_topLimitSwitch = new
    // DigitalInput(Constants.DigitalInputPort.ArmTopLimitSwitch);

    public static final double kGearRatio = (50. / 24.);

    private double m_maxOutput = 0.4;

    private double m_angleDownSpeed = -0.4;
    private double m_angleUpSpeed = 0.4;

    // Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
    // now we just want the primary one.
    private static final int kPIDLoopIdx = 0;

    // Set to zero to skip waiting for confirmation, set to nonzero to wait and
    // report to DS if action fails.
    private static final int kTimeoutMs = 30;

    // Choose so that Talon does not report sensor out of phase
    private static final boolean kSensorPhase = true;

    // Choose based on what direction you want to be positive,
    // Convention should be position voltage moves arm up.
    private static final boolean kMotorInvert = true;

    // Number of encoder ticks per revolution.
    private static final double kMagEncoderConversionFactor = 4096;

    private double m_pidP = 0.4;
    private double m_pidI = 0.;
    private double m_pidD = 0.00001;
    private double m_pidIz = 0;
    
    public ArmSubSystem() {
        initMotorControllers();
        resetEncoders();

        ShuffleboardDisplay.SingleLayout.addDouble("Position", this::getPosition);
    }

    @Override
    public void periodic() {
    }

    // Resets encoders current position value to 0
    public void resetEncoders() {
        resetEncoders(0);
    }

    public void resetEncoders(double currentPosition) {
        currentPosition = currentPosition * kMagEncoderConversionFactor;
        m_motorMaster.setSelectedSensorPosition(currentPosition, kPIDLoopIdx, kTimeoutMs);
    }

    // Returns current encoder position value
    public double getPosition() {
        return m_motorMaster.getSelectedSensorPosition(kPIDLoopIdx) / kMagEncoderConversionFactor;
    }

    // Set the arm moving, value is voltage%
    public void move(double value) {
        // if (value > 0 && topLimitActivated()) { // up
        // m_motorMaster.set(0);
        // return;
        // } else if (value < 0 && bottomLimitActivated()) { // down
        // m_motorMaster.set(0);
        // resetEncoders(0);
        // return;
        // }

        m_motorMaster.set(value);
    }

    // Make arm move up using predefined up move speed.
    public void moveUp() {
        // if (topLimitActivated()) {
        // m_motorMaster.set(0);
        // } else {
        m_motorMaster.set(m_angleUpSpeed);
        // }
    }

    // Make arm move down using predefined down move speed.
    public void moveDown() {
        // if (bottomLimitActivated()) {
        // m_motorMaster.set(0);
        // resetEncoders(0);
        // } else {
        m_motorMaster.set(m_angleDownSpeed);
        // }
    }

    // Move arm to a specific encoder position.
    public void moveToPosition(double targetPosition) {
        var encoderPosition = targetPosition * kMagEncoderConversionFactor;
        m_motorMaster.set(ControlMode.Position, encoderPosition);
    }

    // Stop arm motor.
    public void stop() {
        m_motorMaster.stopMotor();
    }

    // // Returns status of the arm's bottom limit switch.
    // public boolean bottomLimitActivated() {
    // var value = (m_bottomRightLimitSwitch.get() ||
    // !m_bottomLeftLimitSwitch.get());
    // if (value) { resetEncoders(0); }
    // return value;
    // }

    // // Returns status of the arm's top limit switch.
    // public boolean topLimitActivated() {
    // return !m_topLimitSwitch.get();
    // }

    // Switches
    // Initializes the motor controllers
    private void initMotorControllers() {
        // Factory Default all hardware to prevent unexpected behaviour
        m_motorMaster.configFactoryDefault();
        m_motorSlave.configFactoryDefault();

        m_motorSlave.follow(m_motorMaster, FollowerType.PercentOutput);

        // Set based on what direction you want forward/positive to be.
        // This does not affect sensor phase.
        m_motorMaster.setInverted(kMotorInvert);
        m_motorSlave.setInverted(InvertType.FollowMaster);

        // Config the sensor used for Primary PID and sensor direction
        m_motorMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kPIDLoopIdx, kTimeoutMs);

        // Ensure sensor is positive when output is positive
        m_motorMaster.setSensorPhase(kSensorPhase);

        // Config the peak and nominal outputs, 12V means full
        m_motorMaster.configNominalOutputForward(0, kTimeoutMs);
        m_motorMaster.configNominalOutputReverse(0, kTimeoutMs);
        m_motorMaster.configPeakOutputForward(m_maxOutput, kTimeoutMs);
        m_motorMaster.configPeakOutputReverse(-1 * m_maxOutput, kTimeoutMs);

        // Config Position Closed Loop gains in slot0, typically kF stays zero.
        m_motorMaster.config_kF(kPIDLoopIdx, 0, kTimeoutMs);
        m_motorMaster.config_kP(kPIDLoopIdx, m_pidP, kTimeoutMs);
        m_motorMaster.config_kI(kPIDLoopIdx, m_pidI, kTimeoutMs);
        m_motorMaster.config_IntegralZone(kPIDLoopIdx, m_pidIz, kTimeoutMs);
        m_motorMaster.config_kD(kPIDLoopIdx, m_pidD, kTimeoutMs);
    }
}
