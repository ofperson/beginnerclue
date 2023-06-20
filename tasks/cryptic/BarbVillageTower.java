package tasks.cryptic;

import data.Utilities;
import data.Vars;
import data.clues.CrypticClue;
import framework.Task;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.pathfinding.impl.web.WebFinder;
import org.dreambot.api.methods.walking.web.node.AbstractWebNode;
import org.dreambot.api.methods.walking.web.node.impl.BasicWebNode;
import org.dreambot.api.methods.walking.web.node.impl.EntranceWebNode;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import tasks.AbstractClueTask;

public class BarbVillageTower extends AbstractClueTask implements Task {

    public static final CrypticClue CLUE = CrypticClue.CLUES.get(0);

    public BarbVillageTower() {
        super(CLUE);
        clue.setArea(Area.generateArea(3, new Tile(3097, 3432, 2)));

        Tile doorTile = new Tile(3098, 3426, 0);
        AbstractWebNode startClosestNode = WebFinder.getWebFinder().getNearest(doorTile, 25);

        EntranceWebNode entrance = new EntranceWebNode(doorTile.getX(), doorTile.getY(), doorTile.getZ(), "Door", "Open");
        entrance.addConnections(startClosestNode);
        startClosestNode.addConnections(entrance);

        EntranceWebNode ladderOneUp = new EntranceWebNode(new Tile(3096, 3433, 0), "Ladder", "Climb-up");
        ladderOneUp.addConnections(entrance);
        entrance.addConnections(ladderOneUp);

        EntranceWebNode ladderOneDown = new EntranceWebNode(new Tile(3096, 3433, 1), "Ladder", "Climb-down");
        ladderOneDown.addConnections(ladderOneUp);
        ladderOneUp.addConnections(ladderOneDown);

        EntranceWebNode ladderTwoUp = new EntranceWebNode(new Tile(3097, 3433, 1), "Ladder", "Climb-up");
        ladderTwoUp.addConnections(ladderOneDown);
        ladderOneDown.addConnections(ladderTwoUp);

        EntranceWebNode ladderTwoDown = new EntranceWebNode(new Tile(3097, 3433, 2), "Ladder", "Climb-down");
        ladderTwoDown.addConnections(ladderTwoUp);
        ladderTwoUp.addConnections(ladderTwoDown);
        ladderTwoDown.addConnections(new BasicWebNode(3097, 3432, 2));
    }

    @Override
    public void execute() {
        if (clue.getArea().contains(Players.getLocal())) {
            // open door if needed
            if (!doorIsOpen() && Utilities.waitObject(1535) && Sleep.sleepUntil(() -> !Players.getLocal().isMoving(), 4000))
                Sleep.sleep(600, 900);

            // talk to guy
            if (Utilities.waitNpc(((CrypticClue)clue).getNpc())
                    && Sleep.sleepUntil(Dialogues::canContinue, 3000)
                    && Dialogues.clickContinue()) {
                Sleep.sleep(600, 900);
                Vars.get().setClue(null);
            }

            return;
        }

        if (Utilities.walkWait(clue.getArea()))
            Sleep.sleep(600, 900);
    }

    private boolean doorIsOpen() {
        GameObject obj = GameObjects.closest(1536);
        return obj != null && obj.exists();
    }

}