package tasks.cryptic;

import data.Utilities;
import data.Vars;
import data.clues.ClueScroll;
import data.clues.CrypticClue;
import framework.Task;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.utilities.Sleep;
import tasks.AbstractClueTask;

public class CrypticClueTask extends AbstractClueTask implements Task {

    public CrypticClueTask(ClueScroll clue) {
        super(clue);
    }

    @Override
    public void execute() {
        if (clue.getArea().contains(Players.getLocal())) {
            if (Utilities.waitNpc(((CrypticClue) clue).getNpc())
                    && Sleep.sleepUntil(Dialogues::canContinue, 5000)
                    && Dialogues.clickContinue()) {
                Sleep.sleep(600, 900);
                Vars.get().setClue(null);
            }

            return;
        }

        if (Utilities.walkWait(clue.getArea()))
            Sleep.sleep(600, 900);
    }
}
