package frc.robot.Autonomous;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.RobotContainer;
import frc.robot.Constants.AutoConstants;
import frc.robot.commands.CargoTrack;
import frc.robot.commands.DriveXMeters;
import frc.robot.commands.HubTrack;
import frc.robot.commands.Shoot;
import frc.robot.commands.TurnXDegrees;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public class Auto {
    private static Arm arm = Arm.getInstance();
    private static Intake intake = Intake.getInstance();
    public static Command getIntakeCommand() { //Arm down and spin conveyor
        return new SequentialCommandGroup(
            /*new WaitCommand(0.3), */ // move balls in storage if needed
            new ParallelCommandGroup(
                new RunCommand( ()->arm.rotate(-0.4), arm),
                new RunCommand( ()->intake.intake(0.5), intake), 
                new RunCommand( ()->intake.setConveyor(0.5), intake)), 
            new WaitCommand(1.7), 
            new ParallelCommandGroup(
                new RunCommand( ()->arm.rotate(0.35), arm),
                new InstantCommand(intake::stopIntake, intake)));
    }

    public static Command getShootCommand() { //Drive up and shoot
        return new SequentialCommandGroup(
            new HubTrack(),
            new DriveXMeters(AutoConstants.hubXOffset, AutoConstants.DXMConstraints[0], AutoConstants.DXMConstraints[1]), 
            new Shoot(AutoConstants.shooterVelocity).withTimeout(4)
            );
    }

    public static Command getBackupCommand() { //Back up and find new ball
        return new SequentialCommandGroup(
            new DriveXMeters(-AutoConstants.backupDistance, AutoConstants.DXMConstraints[0], AutoConstants.DXMConstraints[1]),
            new TurnXDegrees(180, AutoConstants.TXDConstraints[0], AutoConstants.TXDConstraints[1]),
            new CargoTrack()
        );
    }
}