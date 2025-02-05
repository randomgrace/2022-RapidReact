package com.spartronics4915.frc2022;

import com.spartronics4915.frc2022.commands.DriveCommands;
import com.spartronics4915.frc2022.commands.IntakeCommands;
import com.spartronics4915.frc2022.commands.ConveyorCommands;
import com.spartronics4915.frc2022.commands.LauncherCommands;
import com.spartronics4915.frc2022.commands.ClimberCommands;

import com.spartronics4915.frc2022.subsystems.Drive;
import com.spartronics4915.frc2022.subsystems.Intake;
import com.spartronics4915.frc2022.subsystems.Conveyor;
import com.spartronics4915.frc2022.subsystems.Launcher;
import com.spartronics4915.frc2022.subsystems.Climber;

import com.spartronics4915.frc2022.Constants.OIConstants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;


/**
 * This class is where the bulk of the robot should be declared.
 * Since command-based is a "declarative" paradigm, very little robot logic
 * should actually be handled in the {@link Robot} periodic methods
 * (other than the scheduler calls). Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer
{
    // The robot's subsystems and commands are defined here...
    
    // public final Drive mDrive;
    // public final DriveCommands mDriveCommands;

    public final Intake mIntake;
    public final IntakeCommands mIntakeCommands;

    public final Conveyor mConveyor;
    public final ConveyorCommands mConveyorCommands;

    public final Launcher mLauncher;
    public final LauncherCommands mLauncherCommands;
    
    // public final Climber mClimber;
    // public final ClimberCommands mClimberCommands;
  
    public static final Joystick mArcadeController = new Joystick(Constants.OIConstants.kArcadeStickPort);
    public static final Joystick mDriverController = new Joystick(Constants.OIConstants.kJoystickPort);

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer()
    {
        // ...and constructed here.
        //mAutoCommand = new ExampleCommand(mExampleSubsystem);

        // mDrive = new Drive();
        mIntake = new Intake();
        mConveyor = new Conveyor();
        mLauncher = new Launcher();
        // mClimber = new Climber();
        
        // mDriveCommands = new DriveCommands(mDrive, mDriverController);
        mIntakeCommands = new IntakeCommands(mIntake, mConveyor);
        mConveyorCommands = new ConveyorCommands(mConveyor, mIntake);
        mLauncherCommands = new LauncherCommands(mLauncher, mConveyor, mArcadeController);
        // mClimberCommands = new ClimberCommands(mClimber);

        configureButtonBindings();
    }

    /** Use this method to define your button ==> command mappings. */
    private void configureButtonBindings() {
        new JoystickButton(mDriverController, OIConstants.kSlowModeButton)
            .whenPressed(mDriveCommands.new ToggleSlowModeCommand())
            .whenReleased(mDriveCommands.new ToggleSlowModeCommand());

        new JoystickButton(mArcadeController, OIConstants.kIntakeToggleButton)
            .whenPressed(mIntakeCommands.new TryToggleIntake())
            .whenPressed(mConveyorCommands.new ToggleConveyor())
            .whenPressed(mLauncherCommands.new ToggleLauncher());

        new JoystickButton(mArcadeController, OIConstants.kConveyorReverseBothButton)
            .whileHeld(mConveyorCommands.new ReverseBoth());
        new JoystickButton(mArcadeController, OIConstants.kConveyorReverseBottomButton)
            .whileHeld(mConveyorCommands.new ReverseBottom());
            
        new JoystickButton(mArcadeController, OIConstants.kLauncherShootButton)
            .whenPressed(new SequentialCommandGroup(
                mIntakeCommands.new RetractIntake(),
                mConveyorCommands.new Shoot1() /* or ShootAll() once we're good enough */
            ));
        new JoystickButton(mArcadeController, OIConstants.kLauncherToggleButton)
            .whenPressed(mLauncherCommands.new ToggleLauncher());
        new JoystickButton(mArcadeController, OIConstants.kLauncherShootFarButton)
            .whileHeld(mLauncherCommands.new ShootFar());
            
        // new JoystickButton(mArcadeController, OIConstants.kClimberExtendButton)
        //     .whenPressed(mClimberCommands.new StartExtend())
        //     .whenReleased(mClimberCommands.new StopExtend());
        // new JoystickButton(mArcadeController, OIConstants.kClimberRetractButton)
        //     .whenPressed(mClimberCommands.new StartRetract())
        //     .whenReleased(mClimberCommands.new StopRetract());
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand()
    {
        return null; // -0
    }

    public Command getTeleopCommand()
    {
        return mLauncherCommands.new ToggleLauncher();
    }
}
