package evenements;
import robot.*;


public class EvenementRendLaMain extends Evenement {
		
	private robot robot;

	public EvenementRendLaMain(long date, robot robot) {
		super(date);
	}

	public void execute() {
		robot.setBusy(false);
	}
}

