package frc.robot.commands;

import java.util.Set;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;
import frc.robot.CustomUtil.Timeframe;
import frc.robot.subsystems.ExampleSubsystem;

public class ExampleCommand implements Command {
    private ExampleSubsystem subsystem = ExampleSubsystem.getInstance();
    private Timeframe<Double> exampleTimeframe;
    private double counter = 0;
    private Subsystem[] requirements = {subsystem};
    public ExampleCommand(/*Optional parameters here*/) {
        exampleTimeframe = new Timeframe<>(2.0, 1.0/Constants.dt); //Example construction
    }

    @Override
    public void initialize() { //Initialize runs once every time the Command is initially scheduled, reset any necessary components here
        exampleTimeframe.reset();
        counter = 0;
    }

    @Override
    public void execute() { //Runs every 20ms while the Command is being run by the Command Scheduler
        if(counter >= 100 * Constants.dt) counter = 0;
        exampleTimeframe.update((counter+=Constants.dt));
        subsystem.setOpenLoop(0.05); //Basic implementation

        /* 
        Advanced implementation: Use feedforward and feedback control for precise motion
        
        double feedforward = ExampleSubsystem.FEEDFORWARD.calculate(0.1); //calculate feedforward for 0.1 m/s
        double output = ExampleSubsystem.velController.calculate(subsystem.getEncoderMPS(), 0.1); //calculate the error correction if our goal is 0.1m/s
        subsystem.setOpenLoop((feedforward + output) / Constants.kMaxVoltage); //feedforward is always in units of voltage, and our PID would hypothetically be calibrated in terms of voltage as well, so to go from a voltage to a percent, take our total voltage and divide by max voltage (max V always 12)
        */
    }

    @Override
    public boolean isFinished() { //Runs every 20ms after execute, if this returns true the command will end itself
        return false;
    }

    /*
        @param interrupted whether the command ended due to an interruption (false if it ends on its own, such as when isFinished returns true)
    */
    @Override
    public void end(boolean interrupted) { //Runs once when the command is ended, whether by itself or an external factor
        subsystem.stop();
    }
    
    @Override
    public Set<Subsystem> getRequirements() { //Required method that tells the Command Scheduler which subsystems this command will use
        return Set.of(requirements);
    }
}
