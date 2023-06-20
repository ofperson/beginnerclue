package tasks.restock;

import data.Utilities;
import data.Vars;
import framework.Priority;
import framework.Task;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import tasks.hotcold.HotCold;

public class StrangeDevice implements Task {

    private Area reldoArea = new Area(
            new Tile(3207, 3498, 0),
            new Tile(3207, 3490, 0),
            new Tile(3215, 3490, 0),
            new Tile(3215, 3494, 0),
            new Tile(3218, 3494, 0),
            new Tile(3218, 3498, 0)
    );

    @Override
    public Priority priority() {
        return Priority.RESTOCK;
    }

    @Override
    public boolean validate() {
        return !Inventory.contains(HotCold.DEVICE) && Vars.get().isGetDevice();
    }

    @Override
    public void execute() {
        Logger.info("Getting new strange device");
        if (!reldoArea.contains(Players.getLocal()) && Utilities.walkWait(reldoArea)) {
            Sleep.sleep(600, 900);
            return;
        }

        if (Utilities.waitNpc("Reldo")
                && Sleep.sleepUntil(Dialogues::inDialogue, 5000)) {
            Sleep.sleep(600, 900);

            if (Dialogues.inDialogue()) {
                Sleep.sleepUntil(Dialogues::canContinue, 4000);
                while (Dialogues.canContinue()) {
                    Dialogues.clickContinue();
                    Sleep.sleep(600, 900);
                }

                if (Dialogues.areOptionsAvailable() && Dialogues.chooseFirstOption("Yes, I do.")) {
                    Sleep.sleepUntil(Dialogues::canContinue, 4000);
                    Sleep.sleep(600, 900);
                    while (Dialogues.canContinue()) {
                        Dialogues.clickContinue();
                        Sleep.sleep(600, 900);
                    }

                    if (Inventory.contains(HotCold.DEVICE)) {
                        Vars.get().setGetDevice(false);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Getting strange device from Reldo.";
    }
}
