package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class ShuffleboardDisplay {

    public static final ShuffleboardDisplay Instance = new ShuffleboardDisplay();

    public static ShuffleboardTab MainTab;

    public static ShuffleboardLayout SingleLayout;
    
    private ShuffleboardDisplay() {
        var kBasicLayoutWidth = 3;
        var kTotalHeight = 10;

        MainTab = Shuffleboard.getTab("Learning");

        var columnIndex = 0;
        // First Column
        SingleLayout = MainTab.getLayout("Single", BuiltInLayouts.kList)
                .withSize(kBasicLayoutWidth, kTotalHeight/2)
                .withPosition(columnIndex, 0);
    }
}
