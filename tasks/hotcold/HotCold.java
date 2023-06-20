package tasks.hotcold;

import data.RestockJob;
import data.Utilities;
import data.Vars;
import data.clues.HotColdClue;
import framework.Task;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.listener.ChatListener;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.core.Instance;
import tasks.AbstractClueTask;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class HotCold extends AbstractClueTask implements Task, ChatListener {

    public static final int DEVICE = 23183;

    private Tile location = null;
    private HotColdSolver hotColdSolver = null;
    private HotColdTemperature temperature = null;

    public static HotColdClue CLUE = HotColdClue.CLUES.get(0);

    public HotCold() {
        super(CLUE);
        initHc();
        Instance.getInstance().addEventListener(this);
    }

    @Override
    public void onGameMessage(Message message) {
        Logger.info("MESSAGE: " + message.getMessage());
        if (message.getMessage().contains("The device is"))
            handleTemp(message.getMessage());

        if (message.getMessage().equals("You've obtained a casket!") || message.getMessage().equals("The strange device is inactive.")) {
            Vars.get().setClue(null);
            initHc();
        }
    }

    @Override
    public void execute() {
        if (!Inventory.contains(DEVICE)) { // separate banking/restock into own func
            if (Bank.open()) {
                if (Bank.contains(DEVICE)) {
                    Bank.withdraw(DEVICE, 1);
                    Sleep.sleep(600, 900);
                } else {
                    Logger.error("No strange device found in bank! Attempting to get another from Reldo...");
                    Bank.close();
                    Vars.get().setGetDevice(true);
                    return;
                }
                Bank.close();
            } else {
                Sleep.sleepUntil(Walking::shouldWalk, () -> Players.getLocal().isMoving(), 1000, 100);
                return;
            }
        }

        if (!Inventory.contains(952)) {
            // head to bank to look for spade
            if (Bank.open()) {
                if (Bank.contains(952)) {
                    Bank.withdraw(952, 1);
                    Sleep.sleep(600, 900);
                } else {
                    Logger.error("No spade found in bank! Attempting to buy from GE...");
                    Sleep.sleep(600, 900);
                    Bank.close();
                    Vars.get().addRestockJob(new RestockJob("Spade", 1));
                    return;
                }
                Bank.close();
            } else {
                Sleep.sleepUntil(Walking::shouldWalk, () -> Players.getLocal().isMoving(), 1000, 100);
                return;
            }
        }

        // update temperature
        if (location == null) {
            Vars.get().setStatus("Finding hot/cold location...");
            if (Utilities.clickInventory("Strange device")) {
                Sleep.sleep(600, 900);

                if (location != null) {
                    Logger.info("at location!");
                    return;
                }

                Logger.info("Current temp: " + temperature);
                Set<HotColdLocation> locs = hotColdSolver.getPossibleLocations();
                for (HotColdLocation loc: locs)
                    Logger.info("Possible locations: " + loc.getArea());

                Optional<HotColdLocation> hcl = locs.stream().findFirst();

                if (hcl.isPresent()) {
                    if (Utilities.walkWait(hcl.get().getTile())
                            && Sleep.sleepUntil(() -> !Players.getLocal().isMoving(), 10000)) {
                        Sleep.sleep(600, 900);
                    }
                } else {
                    // reset locations
                    initHc();
                }

            }

            return;
        }

        Vars.get().setStatus("Found location! Digging...");
        if (Utilities.clickInventory("Spade")
                && Sleep.sleepUntil(() -> !Players.getLocal().isAnimating(), 5000)) {
            Sleep.sleep(600, 900);

            // found chatscreen, clue/casket found
            if (Sleep.sleepUntil(Dialogues::inDialogue, 5000) && Dialogues.clickContinue()) {
                Vars.get().setClue(null);
                initHc();
                return;
            }

            // not found, back to search
            location = null;
        }
    }

    public void initHc() {
        Set<HotColdLocation> locations = Arrays.stream(HotColdLocation.values())
                .collect(Collectors.toSet());
        hotColdSolver = new HotColdSolver(locations);
        location = null;
        temperature = null;
    }

    private void handleTemp(String message) {
        HotColdTemperature temp = HotColdTemperature.getFromTemperatureSet(HotColdTemperature.BEGINNER_HOT_COLD_TEMPERATURES, message);

        if (temp == null) {
            Logger.info("Temperature not found!");
            return;
        }

        temperature = temp;
        if (temperature == HotColdTemperature.BEGINNER_VISIBLY_SHAKING) {
            location = Players.getLocal().getTile();
            Logger.info("Found location: " + Players.getLocal().getTile());
            return;
        }

        HotColdTemperatureChange temperatureChange = HotColdTemperatureChange.of(message);
        hotColdSolver.signal(Players.getLocal().getTile(), temperature, temperatureChange);
    }
}
