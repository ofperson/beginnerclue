package tasks.restock;

import data.RestockJob;
import data.Utilities;
import data.Vars;
import framework.Priority;
import framework.Task;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.grandexchange.GrandExchange;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Restocker implements Task {

    private static final Area GE = Area.generateArea(3,new Tile(3165, 3486, 0));

    @Override
    public Priority priority() {
        return Priority.RESTOCK;
    }

    @Override
    public boolean validate() {
        return !Vars.get().getRestockJobs().isEmpty(); // items to restock in list
    }

    @Override
    public void execute() {
        if (!GE.contains(Players.getLocal())) {
            if (Utilities.walkWait(GE))
                Sleep.sleep(600, 900);

            return;
        }

        // no coins in inventory
        if (!Inventory.contains(995) || coinCount() <= calcAvgCost()) {
            if (!Bank.isOpen() && Sleep.sleepUntil(Bank::open, 5000))
                return;

            if (Bank.contains(995)) {
                Bank.withdrawAll(995);
                Sleep.sleep(600, 900);
            } else {
                Logger.warn("Missing/not enough item: Coins!");
                Vars.get().setRunning(false);
                return;
            }
        }

        // close ge bank if open
        if (Bank.isOpen())
            Sleep.sleepUntil(Bank::close, 5000);

        // open ge
        if (!GrandExchange.isOpen())
            Sleep.sleepUntil(GrandExchange::open, 5000);

        List<RestockJob> jobs = new ArrayList<>(Vars.get().getRestockJobs());
        for (RestockJob job: jobs) {
            // make sure we have slots
            if (GrandExchange.getOpenSlots() == 0)
                Sleep.sleepUntil(GrandExchange::collectToBank, 4000);

            Logger.info("buy price: " + job.getCost());

            if (GrandExchange.buyItem(job.getItem(), job.getAmount(), (int)job.getCost())
                    && Sleep.sleepUntil(GrandExchange::isReadyToCollect, 20000)) {
                Sleep.sleep(600, 900);
                GrandExchange.collectToBank();
                Vars.get().getRestockJobs().remove(job);
            }
        }

        if (Vars.get().getRestockJobs().isEmpty())
            Sleep.sleepUntil(GrandExchange::close, 5000);
    }

    private int coinCount() {
        Item coins = Inventory.get(995);
        return coins != null ? coins.getAmount() : 0;
    }

    private int calcAvgCost() {
        int cost = 0;
        List<RestockJob> jobs = Vars.get().getRestockJobs();
        for (RestockJob job: jobs) {
            cost += job.getCost();
        }
        return cost;
    }

    @Override
    public String toString() {
        return "Restocking!";
    }
}
