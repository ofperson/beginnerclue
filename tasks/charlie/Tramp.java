package tasks.charlie;

import data.RestockJob;
import data.Utilities;
import data.Vars;
import data.clues.CharlieClue;
import framework.Task;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import tasks.AbstractClueTask;

public class Tramp extends AbstractClueTask implements Task {

    public static final String NPC = "Charlie the Tramp";
    public static final String[] ITEMS = {
            "Iron ore",
            "Iron dagger",
            "Raw herring",
            "Raw trout",
            "Trout",
            "Pike",
            "Leather body",
            "Leather chaps"
    };

    public static final Tile VW_BANK_TILE = new Tile(3185, 3436, 0);
    public static final Area VW_BANK = Area.generateArea(2, VW_BANK_TILE);

    public static final Tile TRAMP_TILE = new Tile(3209, 3392, 0);
    public static final Area TRAMP_AREA = Area.generateArea(6, TRAMP_TILE);

    public static final CharlieClue CLUE = CharlieClue.CLUES.get(0);

    public Tramp() {
        super(CLUE);
    }

    @Override
    public void execute() {
        if (!Utilities.inventoryContainsAll(ITEMS)) {
            if (!VW_BANK.contains(Players.getLocal())) {
                if (Utilities.walkWait(VW_BANK))
                    Sleep.sleep(600, 900);

                return;
            }
            // at bank
            if (!Bank.isOpen() && Sleep.sleepUntil(Bank::open, 5000))
                return;

            // withdraw items
            boolean missingItem = false;
            for (String item: ITEMS) {
                if (!Inventory.contains(item)) {
                    if (Bank.contains(item)) {
                        Bank.withdraw(item, 1);
                        Sleep.sleep(600, 900);
                    } else {
                        Logger.info("Missing item: " + item);
                        Sleep.sleep(600, 900);
                        if (Vars.get().isBuyEmoteItems()) {
                            missingItem = true;
                            Vars.get().addRestockJob(new RestockJob(item, Vars.get().getCharlieRestockAmount()));
                        } else {
                            Logger.warn("Not set to buy Charlie items! Ending...");
                            Vars.get().setRunning(false);
                            return;
                        }
                    }
                }
            }
            Sleep.sleepUntil(Bank::close, 5000);

            if (missingItem)
                return;

        } else {
            // walk to tramp
            if (!TRAMP_AREA.contains(Players.getLocal()) && Utilities.walkWait(TRAMP_AREA)) {
                Sleep.sleep(600, 900);
                return;
            }

            // handle charlie
            if (initialCharlie()) {
                Sleep.sleep(600, 900);
                Vars.get().setClue(null);
            }
        }
    }

    private boolean initialCharlie() {
        if (Utilities.waitNpc(NPC)
                && Sleep.sleepUntil(Dialogues::inDialogue, 5000)) {

            if (Dialogues.getNPCDialogue().contains("Spare some change guv?"))
                return true;

            // click to continue
            while (Dialogues.inDialogue()) {
                if (Dialogues.canContinue() && Sleep.sleepUntil(() -> Dialogues.clickContinue(), 5000))
                    Sleep.sleep(600, 900);
            }
            Sleep.sleep(600, 900);

            // give item
            if (Utilities.waitNpc(NPC)
                    && Sleep.sleepUntil(() -> Dialogues.inDialogue(), 5000)
                    && Sleep.sleepUntil(() -> Dialogues.canContinue(), 5000)
                    && Dialogues.clickContinue()) {
                Sleep.sleep(600, 900);
                Logger.info("gave charlie");
                return true;
            }
        }
        return false;
    }

}