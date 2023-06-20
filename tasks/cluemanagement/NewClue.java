package tasks.cluemanagement;

import data.Constants;
import data.Utilities;
import data.Vars;
import framework.Priority;
import framework.Task;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.event.impl.ExperienceEvent;
import org.dreambot.api.script.listener.ExperienceListener;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.core.Instance;

public class NewClue implements Task, ExperienceListener {

    public NewClue() {
        Instance.getInstance().addEventListener(this);
    }

    @Override
    public void onLevelUp(ExperienceEvent event) {
        if (Dialogues.canContinue()) {
            Dialogues.clickContinue();
            Sleep.sleep(600, 900);
        }
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return !Inventory.contains(Constants.CLUE) && Vars.get().getClue() == null;
    }

    @Override
    public void execute() {
        if (Vars.get().getMonster().getArea().contains(Players.getLocal())) {
            // clue drop check
            if (findClue()
                    && Utilities.pickUp(Constants.CLUE)
                    && Sleep.sleepUntil(() -> Inventory.contains(Constants.CLUE_ID), 5000)) {
                Sleep.sleep(600, 900);
                return;
            }

            if (!inCombat()) {
                NPC npc = findInteractingNPC();

                if (npc != null)
                    npc.interact("Attack");
                else
                    attackGoblin();

                Sleep.sleep(600, 900);
                if (Sleep.sleepUntil(() -> !Players.getLocal().isMoving() && Players.getLocal().isAnimating(), 8000))
                    Sleep.sleep(600, 900);

                return;
            }

            if (Walking.getRunEnergy() > Calculations.random(25, 45) && !Walking.isRunEnabled())
                Walking.toggleRun();

        } else {
            if (Utilities.walkWait(Vars.get().getMonster().getArea()))
                Sleep.sleep(600, 900);
        }
    }

    private boolean findClue() {
        GroundItem g = GroundItems.closest(Constants.CLUE_ID);
        return g != null && g.exists();
    }

    private NPC findInteractingNPC() {
        NPC npc = NPCs.closest(n -> n.getName().equals(Vars.get().getMonster().getName())
                && n.isInteracting(Players.getLocal())
                && n.getHealthPercent() > 0
                && n.exists());
        return npc != null ? npc : null;
    }

    private boolean inCombat() {
        NPC c1 = NPCs.closest(npc -> npc != null && npc.getName().equals(Vars.get().getMonster().getName())
                && npc.canReach()
                && npc.getHealthPercent() > 0
                && npc.isInteracting(Players.getLocal())
                && Vars.get().getMonster().getArea().contains(npc)
                && npc.exists());

        NPC c2 = NPCs.closest(npc -> npc != null && npc.getName().equals(Vars.get().getMonster().getName())
                && npc.isInteracting(Players.getLocal())
                && Players.getLocal().isInteracting(npc)
                && npc.getHealthPercent() > 0
                && npc.exists());

        return (c1 != null && Players.getLocal().isAnimating() || Players.getLocal().isMoving())
                || c2 != null;
    }

    private boolean attackGoblin() {
        NPC n = NPCs.closest(npc -> npc != null && npc.getName().equals(Vars.get().getMonster().getName())
                && Vars.get().getMonster().getArea().contains(npc)
                && npc.getHealthPercent() > 0
                && !npc.isInteractedWith()
                && npc.canReach()
                && npc.exists());

        return n != null && n.exists() ? n.interact("Attack") : false;
    }

    @Override
    public String toString() {
        return "Getting new clue from: " + Vars.get().getMonster().getName();
    }

}