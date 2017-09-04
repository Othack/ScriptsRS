package scripts.spin_logic.GEUtilities;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;

import scripts.api.Task;
import scripts.muler.utilities.Constants;
import scripts.webwalker_logic.WebWalker;

public class WalkToGE implements Task {

	@Override
	public String action() {
		return "Walking to GE...";
	}

	@Override
	public int priority() {
		return 1;
	}

	@Override
	public boolean validate() {
		return Constants.varrock.contains(Player.getPosition());
	}

	@Override
	public void execute() {
		WebWalker.walkTo(new RSTile(3165,3486,0));
		Mouse.leaveGame();
		General.sleep(2000, 7000);
	}

}
