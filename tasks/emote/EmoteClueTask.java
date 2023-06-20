package tasks.emote;

import data.RestockJob;
import data.Utilities;
import data.Vars;
import data.clues.ClueScroll;
import data.clues.EmoteClue;
import framework.Task;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import tasks.AbstractClueTask;

import java.util.Set;

public class EmoteClueTask extends AbstractClueTask implements Task {

    private Tile bankTile;
    private Area bankArea;

    public EmoteClueTask(ClueScroll clue) {
        super(clue);
        this.bankTile = null;
        this.bankArea = null;
    }

    public EmoteClueTask(ClueScroll clue, Tile bankTile) {
        super(clue);
        this.bankTile = bankTile;
        this.bankArea = Area.generateArea(2, bankTile);
    }

    @Override
    public void execute() {
        if (((EmoteClue) clue).getItems().length == 0) {
            noItemClue();
        } else {
            itemClue();
        }
    }

    private void noItemClue() {
        if (!clue.getArea().contains(Players.getLocal())) {
            if (Utilities.walkWait(clue.getArea())) {
                Sleep.sleep(600, 900);
            }
        } else {
            if (Utilities.handleUriEmote(((EmoteClue) clue).getEmote())) {
                Sleep.sleep(600, 900);
                Vars.get().setClue(null);
            }
        }
    }

    private void itemClue() {
        if (!Utilities.itemsInInvOrEquipped(((EmoteClue) clue).getItems())) {
            if (!bankArea.contains(Players.getLocal())) {
                if (Utilities.walkWait(bankArea))
                    Sleep.sleep(600, 900);

                return;
            }
            // at bank
            if (!Bank.isOpen() && Bank.open())
                return;

            // withdraw items
            boolean missingItem = false;
            for (String item: ((EmoteClue) clue).getItems()) {
                if (!Inventory.contains(item) && !Equipment.contains(item)) {
                    if (Bank.contains(item)) {
                        Bank.withdraw(item, 1);
                        Sleep.sleep(600, 900);
                    } else {
                        Logger.info("Missing item: " + item);
                        Sleep.sleep(600, 900);
                        if (Vars.get().isBuyEmoteItems()) {
                            missingItem = true;
                            Vars.get().addRestockJob(new RestockJob(item, 1)); // 1 for emote items
                        } else {
                            Logger.warn("Not set to buy emote items! Ending...");
                            Vars.get().setRunning(false);
                            return;
                        }
                    }
                }
            }
            Sleep.sleepUntil(Bank::close, 3000);

            // items missing, go to restock
            if (missingItem)
                return;

        } else {

            if (Utilities.equipmentContainsAll(((EmoteClue) clue).getItems())) {

                if (!clue.getArea().contains(Players.getLocal()) && Utilities.walkWait(clue.getArea())) {
                    Sleep.sleep(600, 900);
                    return;
                }

                // handle uri
                if (Utilities.handleUriEmote(((EmoteClue) clue).getEmote())) {
                    Sleep.sleep(600, 900);

                    Set<String> startingEquip = Vars.get().getEquipment();
                    if (startingEquip != null) {
                        for (String ei: startingEquip) {
                            if (!Equipment.contains(ei) && Inventory.contains(ei)) {
                                Inventory.interact(ei);
                                Sleep.sleep(600, 900);
                            }
                        }
                    }

                    Vars.get().setClue(null);
                }

            } else {
                // do equip
                for (String item: ((EmoteClue) clue).getItems()) {
                    if (!Equipment.contains(item)) {
                        Inventory.interact(item);
                        Sleep.sleep(600, 900);
                    }
                }
            }
        }
    }

}