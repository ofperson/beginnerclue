package tasks.map;

import data.Utilities;
import data.Vars;
import data.clues.MapClue;
import framework.Task;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import tasks.AbstractClueTask;

public class MapClueTask extends AbstractClueTask implements Task {

    public MapClueTask(MapClue clue) {
        super(clue);
    }

    @Override
    public void execute() {
        if (!Inventory.contains(952)) {
            Logger.error("No spade found!");
            Vars.get().setRunning(false);
            return;
        }

        if (Players.getLocal().getTile().distance(clue.getTile()) == 0) {
            if (Utilities.clickInventory("Spade")
                    && Sleep.sleepUntil(() -> !Players.getLocal().isAnimating(), 5000)) {
                Sleep.sleep(1600, 1900);
                Vars.get().setClue(null);
            }
            return;
        }

        if (Utilities.walkWait(clue.getArea())
                && Sleep.sleepUntil(() -> !Players.getLocal().isAnimating(), 15000)
                && Walking.walkExact(clue.getTile())
                && Sleep.sleepUntil(() -> !Players.getLocal().isAnimating(), 15000))
            Sleep.sleep(600, 900);
    }

}